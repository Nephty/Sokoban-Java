package main.java.view;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import main.java.model.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.java.model.Box;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ALL")
public class Main extends Application {

    Stage window;
    Scene mainMenu;
    Scene gameUI;
    Scene optionsMenu;
    Scene achievementsMenu;
    LogInMenu logInMenu;

    static MediaPlayer mediaPlayer;

    Player player;
    Block[][] blockList;
    CustomImage tempImage;

    static final int windowX = 0;
    static final int windowY = 0;
    static final int windowWidth = 1920;
    static final int windowHeight = 1080;

    static final int ORIGINAL_WIDTH = 1920;
    static final int ORIGINAL_HEIGHT = 1080;
    static float WR, HR;
    static byte RESOLUTION_ID;

    static boolean fullscreen = false;

    int limit = (int) (20*WR);
    int availableSpace = (int) (1000*WR);
    int imageLength;
    int firstPositionX;
    int remainingSpace;

    boolean currentLevelIsWon = false;
    int totalMoves = 0;
    byte totalMovesPow = 1;
    int totalPushes = 0;
    byte totalPushesPow = 1;

    Direction playerFacing = Direction.DOWN;

    long elapsedTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        prepareResolution(window);

        Pane panel = new Pane();
        mainMenu = new Scene(panel, windowWidth, windowHeight);

        GridPane gamePanel = new GridPane();
        gamePanel.setPadding(new Insets(10, 10, 10, 10));
        gamePanel.setVgap(50);
        gamePanel.setHgap(50);

        CustomImage background = new CustomImage(windowX, windowY, WR, HR, "background.png");


        // Set all buttons & overlays
        CustomButton playButton = new CustomButton((int) (((windowWidth/2)-(480/2))), (int) (((windowHeight/2)+25-96-25)), WR, HR, "play button.png");
        CustomButton optionsButton = new CustomButton((int) (((windowWidth/2)-(480/2))), (int)(((windowHeight/2)+25)), WR, HR, "options button.png");
        CustomButton quitButton = new CustomButton((int)(((windowWidth/2)-(480/2))), (int)(((windowHeight/2)+25+96+25+96+25)), WR, HR, "quit button.png");
        CustomButton backButtonGame = new CustomButton(0, 0, WR, HR, "back button.png");
        CustomButton backButtonOptions = new CustomButton((int)((windowWidth-480-5)), (int)((windowHeight-96-5)), WR, HR, "back button.png");
        CustomButton backButtonAchievements = new CustomButton((int)((windowWidth-480-10)), (int)((windowHeight-96-10)), WR, HR, "back button.png");
        CustomButton achievementsButton = new CustomButton((int)(((windowWidth/2)-(480/2))), (int)(((windowHeight/2)+25+96+25)), WR, HR, "achievements button.png");
        CustomButton campaignButton = new CustomButton((int)(((windowWidth/2)-(480/2)+480+15)), (int)(((windowHeight/2)-96-(96/2)-5)), WR, HR, "campaign button.png");
        CustomButton tutorialButton = new CustomButton((int)(((windowWidth/2)-(480/2)+480+15)), (int)(((windowHeight/2)-96+5+(96/2))), WR, HR, "tutorial button.png");
        campaignButton.setVisible(false);
        tutorialButton.setVisible(false);
        // --------------------

        AtomicBoolean playInterface = new AtomicBoolean(false);

        // BUTTONS ACTIONS ----
        playButton.overlay.setOnMouseClicked(e -> {
            if (!playInterface.get()) {
                campaignButton.setVisible(true);
                tutorialButton.setVisible(true);
                playInterface.set(true);
            }
            else {
                campaignButton.setVisible(false);
                tutorialButton.setVisible(false);
                playInterface.set(false);
            }
            window.setFullScreen(fullscreen);
        });
        campaignButton.overlay.setOnMouseClicked(e -> {
            window.setScene(gameUI);
            window.setFullScreen(fullscreen);
            playInterface.set(false);
            campaignButton.setVisible(false);
            tutorialButton.setVisible(false);
        });
        tutorialButton.overlay.setOnMouseClicked(e -> {
            playInterface.set(false);
            campaignButton.setVisible(false);
            tutorialButton.setVisible(false);
        });
        optionsButton.setOnClick(e -> {
            window.setScene(optionsMenu);
            window.setFullScreen(fullscreen);
        });
        achievementsButton.setOnClick(e -> {
            window.setScene(achievementsMenu);
            window.setFullScreen(fullscreen);
        });
        quitButton.setOnClick(e -> closeProgram());
        backButtonGame.setOnClick(e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });
        backButtonOptions.setOnClick(e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });
        backButtonAchievements.setOnClick(e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });
        // EXCEPTION FOR CUSTOMIMAGE
        background.setOnMouseClicked(e -> {
            if (playInterface.get()) {
                campaignButton.setVisible(false);
                tutorialButton.setVisible(false);
                playInterface.set(false);
            }
            window.setFullScreen(fullscreen);
        });
        // --------------------
        // --------------------


        panel.getChildren().addAll(background, playButton, playButton.overlay, optionsButton, optionsButton.overlay, quitButton, quitButton.overlay, tutorialButton, tutorialButton.overlay, campaignButton, campaignButton.overlay, achievementsButton, achievementsButton.overlay);
        gamePanel.getChildren().addAll(backButtonGame, backButtonGame.overlay);


        // OPTIONS ------------
        CustomImage optionsBackground = new CustomImage(windowX, windowY, WR, HR, "background empty.png");


        Pane optionsBackgroundPane = new Pane();
        optionsBackgroundPane.setPrefWidth(windowWidth*WR);
        optionsBackgroundPane.setPrefHeight(windowHeight*HR);
        optionsBackgroundPane.getChildren().addAll(optionsBackground);


        Pane optionsPane = new Pane();

        CustomImage soundScale = new CustomImage((int)(10*WR), (int)(100*HR), WR, HR, "scale.png");
        Rectangle soundScaleBar = new Rectangle((int)((10+3-1)*WR), (int)((100+3-1*HR)), (int)((200+2)*WR), (int)((30+2)*HR));
        soundScaleBar.setFill(Color.rgb(90, 90, 255, 0.85));
        soundScaleBar.setWidth((int)((100+1)*WR));
        soundScale.setOnMouseClicked((MouseEvent event) -> soundScaleBar.setWidth(event.getSceneX() - soundScaleBar.getX()));
        soundScaleBar.setOnMouseClicked((MouseEvent event) -> soundScaleBar.setWidth(event.getSceneX() - soundScaleBar.getX()));
        AtomicBoolean dragging = new AtomicBoolean(true);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Native resolution", "1280x720", "1600x900", "1920x1080", "2560x1440", "3840x2160");
        comboBox.getSelectionModel().select(RESOLUTION_ID);
        comboBox.setLayoutX(500*WR);
        comboBox.setLayoutY(150*HR);

        comboBox.setOnAction(e -> {
            System.out.println("User selected : " + comboBox.getValue());
            try {
                JSONWriter resolutionModifier = new JSONWriter("data.json");
                RESOLUTION_ID = (byte) (comboBox.getSelectionModel().getSelectedIndex());
                resolutionModifier.set("resolution", String.valueOf(RESOLUTION_ID));
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });

        optionsPane.getChildren().addAll(backButtonOptions, backButtonOptions.overlay, soundScale, soundScaleBar, comboBox);

        Pane finalOptionsPane = new Pane();
        finalOptionsPane.setPrefWidth(windowWidth*WR);
        finalOptionsPane.setPrefHeight(windowHeight*HR);
        finalOptionsPane.getChildren().addAll(optionsBackgroundPane, optionsPane);


        optionsMenu = new Scene(finalOptionsPane, windowWidth, windowHeight);
        // --------------------


        // ACHIEVEMENTS --------
        GridPane achievementsPane = new GridPane();
        achievementsPane.setVgap(50);
        achievementsPane.setHgap(50);

        GridPane.setConstraints(backButtonAchievements, 10, 10);

        CustomImage achievement1Image = new CustomImage(0, 0,WR, HR, "achievement1.png");
        CustomImage achievement1Text = new CustomImage(0, 0,WR, HR, "achievement1 text.png");
        Achievement achievement1 = new Achievement(achievement1Image, achievement1Text, 2, 1, WR, HR, achievementsPane);

        CustomImage achievement2Image = new CustomImage(0, 0, WR, HR, "achievement2.png");
        CustomImage achievement2Text = new CustomImage(0, 0,WR, HR, "achievement2 text.png");
        Achievement achievement2 = new Achievement(achievement2Image, achievement2Text, 2, 2, WR, HR, achievementsPane);

        CustomImage achievement3Image = new CustomImage(0, 0, WR, HR, "achievement3.png");
        CustomImage achievement3Text = new CustomImage(0, 0, WR, HR, "achievement3 text.png");
        Achievement achievement3 = new Achievement(achievement3Image, achievement3Text, 2, 3, WR, HR, achievementsPane);


        CustomImage achievementBackground = new CustomImage(windowX, windowY, WR, HR, "background empty.png");
        GridPane.setConstraints(achievementBackground, 0, 0);

        Button addLevel = new Button("Complete one level");
        addLevel.setOnAction(e -> {
            try {
                JSONWriter temporaryWriter = new JSONWriter("data.json");
                JSONReader temporaryReader = new JSONReader("data.json");
                int initValue = temporaryReader.getInt("completed levels");
                temporaryWriter.set("completed levels", String.valueOf(initValue +1));
                if (initValue == 0) {
                    achievement1.complete();
                } else if (initValue == 4) {
                    achievement2.complete();
                } else if (initValue == 9) {
                    achievement3.complete();
                }
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });
        addLevel.setLayoutX(600);
        addLevel.setLayoutY(600);

        Pane achievementsBackgroundPane = new Pane();
        achievementsBackgroundPane.setPrefWidth(windowWidth*WR);
        achievementsBackgroundPane.setPrefHeight(windowHeight*WR);
        achievementsBackgroundPane.getChildren().addAll(achievementBackground, backButtonAchievements, backButtonAchievements.overlay, addLevel);

        achievementsPane.getChildren().addAll(
                achievement1.image, achievement1.overlay, achievement1.text, achievement1.overlayDone,
                achievement2.image, achievement2.overlay, achievement2.text, achievement2.overlayDone,
                achievement3.image, achievement3.overlay, achievement3.text, achievement3.overlayDone,
                addLevel);

        Pane finalAchievementPane = new Pane();
        finalAchievementPane.setPrefWidth(windowWidth);
        finalAchievementPane.setPrefHeight(windowHeight);
        finalAchievementPane.getChildren().addAll(achievementsBackgroundPane, achievementsPane);


        achievementsMenu = new Scene(finalAchievementPane, windowWidth, windowHeight);
        // --------------------


        // PLAY MENU ----------
        Pane leftMenu = new Pane();
        leftMenu.setPrefWidth(350*WR);
        leftMenu.setMaxWidth(350*WR);
        leftMenu.setMinWidth(350*WR);
        leftMenu.setPrefHeight(windowHeight);
        leftMenu.setMaxHeight(windowHeight);
        leftMenu.setMinHeight(windowHeight);
        leftMenu.setLayoutX(0);
        leftMenu.setLayoutY(0);
        CustomImage leftMenuImage = new CustomImage(0, 0, WR, HR, "left side menu.png");
        // Adding everything to the leftmenu on line #529 (as of 30-03-21 22:41)


        Pane rightMenu = new Pane();
        rightMenu.setPrefWidth(350*WR);
        rightMenu.setMaxWidth(350*WR);
        rightMenu.setMinWidth(350*WR);
        rightMenu.setPrefHeight(windowHeight);
        rightMenu.setMaxHeight(windowHeight);
        rightMenu.setMinHeight(windowHeight);
        rightMenu.setLayoutX((350+1220)*WR);
        rightMenu.setLayoutY(0);
        CustomImage rightMenuImage = new CustomImage(0, 0, WR, HR, "right side menu.png");

        Pane middleMenu = new Pane();
        middleMenu.setPrefWidth(1220*WR);
        middleMenu.setMaxWidth(1220*WR);
        middleMenu.setMinWidth(1220*WR);
        middleMenu.setPrefHeight(windowHeight);
        middleMenu.setMaxHeight(windowHeight);
        middleMenu.setMinHeight(windowHeight);
        middleMenu.setLayoutX(350*WR);
        middleMenu.setLayoutY(0);
        Rectangle midR = new Rectangle(0, 0, 1220*WR, windowHeight*HR);
        midR.setFill(Color.BLACK);
        middleMenu.getChildren().add(midR);

        Pane gamePane = new Pane();
        gamePane.setLayoutX(60*WR);
        gamePane.setLayoutY(60*WR);

        String levelFileName = "level";
        int currentLevelPosX = 148;
        byte currentLevel = 2;
        if (currentLevel < 10) {
            levelFileName += "0";
            currentLevelPosX += 10;

        }
        levelFileName += String.valueOf(currentLevel) + ".xsb";
        ArrayList<String> level = Fichier.loadFile(levelFileName, "");
        Board firstBoard = new Board(level);

        Block[][] blockList = firstBoard.getBlockList();

        prepareMapSize(firstBoard);

        setMap(firstBoard, gamePane);


        // LEFT MENU TEXTS
        final Font font = new Font("Microsoft YaHei", 35*WR);
        final Color color = Color.rgb(88, 38, 24);

        CustomImage moves = new CustomImage(65, 100, WR, HR, "moves.png");
        CustomImage movesContainer = new CustomImage(85, 150, WR, HR, "text container.png");
        Text totalMovesText = new Text(165*WR, 185*HR, String.valueOf(totalMoves));
        totalMovesText.setFont(font);
        totalMovesText.setFill(color);

        CustomImage pushes = new CustomImage(65, 210, WR, HR, "pushes.png");
        CustomImage pushesContainer = new CustomImage(85, 260, WR, HR, "text container.png");
        Text totalPushesText = new Text(165*WR, 295*WR, String.valueOf(totalPushes));
        totalPushesText.setFont(font);
        totalPushesText.setFill(color);

        CustomImage objectives = new CustomImage(65, 320, WR, HR, "objectives.png");
        CustomImage objectivesContainer = new CustomImage(85, 370, WR, HR, "text container.png");
        String objectivesStr = firstBoard.getCurrBoxOnObj() + " / " + firstBoard.getBoxes().size();
        Text objectivesText = new Text(135*WR, 405*WR, objectivesStr);
        objectivesText.setFont(font);
        objectivesText.setFill(color);

        CustomImage time = new CustomImage(65, 430, WR, HR, "time.png");
        CustomImage timeContainer = new CustomImage(85, 480, WR, HR, "text container.png");
        String timeStr = "00:00:00";
        Text timeText = new Text(105*WR, 515*WR, timeStr);
        timeText.setFont(font);
        timeText.setFill(color);

        CustomButton undoButton = new CustomButton(65, 800, WR, HR, "undo.png", (byte) 0);

        CustomButton restartButton = new CustomButton(65, 900, WR, HR, "restart.png", (byte) 0);
        restartButton.overlay.setOnMouseClicked(e -> {
            firstBoard.restart();
            playerFacing = Direction.DOWN;
            totalMoves = 0;
            totalPushes = 0;
            totalMovesText.setText(String.valueOf(totalMoves));
            totalPushesText.setText(String.valueOf(totalPushes));
            objectivesText.setText(firstBoard.getCurrBoxOnObj() + " / " + firstBoard.getBoxes().size());
            while (totalMovesPow > 1) {
                totalMovesPow -= 1;
                totalMovesText.setX(totalMovesText.getX()+10);
            }
            while (totalPushesPow > 1) {
                totalPushesPow -= 1;
                totalPushesText.setX(totalPushesText.getX()+10);
            }
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        // Adding everything to the leftmenu on line #529 (as of 30-03-21 22:41)


        CustomImage currentLevelImg = new CustomImage(58, 100, WR, HR, "level.png");
        CustomImage currentLevelContainer = new CustomImage(78, 150, WR, HR, "text container.png");
        Text currentLevelText = new Text(currentLevelPosX*WR, 185*WR, String.valueOf(currentLevel));
        currentLevelText.setFont(font);
        currentLevelText.setFill(color);


        Difficulty difficulty = Difficulty.NORMAL;
        int difficultyPosX = 0;

        switch (difficulty) {
            case EASY:
                difficultyPosX = 123;
                break;
            case NORMAL :
                difficultyPosX = 91;
                break;
            case HARD :
                difficultyPosX = 118;
                break;
            case EXTREME :
                difficultyPosX = 89;
                break;
        }

        CustomImage difficultyImg = new CustomImage(58, 245, WR, HR, "difficulty.png");
        CustomImage difficultyContainer = new CustomImage(78, 295, WR, HR, "text container.png");
        Text difficultyText = new Text(difficultyPosX*WR, 330*WR, String.valueOf(difficulty));
        difficultyText.setFont(font);
        difficultyText.setFill(color);

        float averageRating = (float) 0.69;
        int averageRatingPosX = 140;
        if ((int) (averageRating) == 1) {
            averageRatingPosX -= 10;
        } else if (averageRating <= 0.1) {
            averageRatingPosX += 10;
        }
        CustomImage averageRatingImg = new CustomImage(50, 390, WR, HR, "average rating.png");
        CustomImage averageRatingContainer = new CustomImage(80, 440, WR, HR, "text container.png");
        String averageRatingStr = String.valueOf((int)(averageRating*100)) + "%";
        Text averageRatingText = new Text(averageRatingPosX*WR, 475*WR, averageRatingStr);
        averageRatingText.setFont(font);
        averageRatingText.setFill(color);

        CustomButton mainMenuButton = new CustomButton(50, 850, WR, HR, "main menu.png", (byte) 1);
        mainMenuButton.overlay.setOnMouseClicked(e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });



        // TODO : when moves or pushes go from 9 to 10, move it a bit to the side, same for 99 -> 100 and center it intially
        // -----


        EventHandler key = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Direction direction;
                System.out.println("boop");
                switch(event.getCode()) {
                    case Z:
                    case UP:
                        direction = Direction.UP;
                        playerFacing = Direction.UP;
                        break;
                    case S:
                    case DOWN:
                        direction = Direction.DOWN;
                        playerFacing = Direction.DOWN;
                        break;
                    case Q:
                    case LEFT:
                        direction = Direction.LEFT;
                        playerFacing = Direction.LEFT;
                        break;
                    case D:
                    case RIGHT:
                        direction = Direction.RIGHT;
                        playerFacing = Direction.RIGHT;
                        break;
                    case R:
                        firstBoard.restart();
                        playerFacing = Direction.DOWN;
                        direction = Direction.RESTART;
                    default:
                        direction = Direction.NULL;
                }
                BooleanCouple moveResult = firstBoard.move(direction);
                if (direction != Direction.NULL && moveResult.isA()) {
                    totalMoves++;
                    totalMovesText.setText(String.valueOf(totalMoves));
                    if (moveResult.isB()) {
                        totalPushes++;
                        System.out.println(firstBoard.getGoals().size());
                        objectivesText.setText(firstBoard.getCurrBoxOnObj() + " / " + firstBoard.getBoxes().size());
                        totalPushesText.setText(String.valueOf(totalPushes));
                    }

                    // offset to keep the text centered
                    if ((totalMoves) % Math.pow(10, totalMovesPow) == 0) {
                        totalMovesPow++;
                        totalMovesText.setX(totalMovesText.getX()-10);
                    }

                    // offset to keep the text centered
                    System.out.println(totalPushes);
                    if ((totalPushes) % Math.pow(10, totalPushesPow) == 0 && totalPushes != 0) {
                        totalPushesPow++;
                        totalPushesText.setX(totalPushesText.getX()-10);
                    }

                    try {
                        setMap(firstBoard, gamePane);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        };

        Button moveUp = new Button("^");
        moveUp.setLayoutX(60*WR);
        moveUp.setLayoutY(10*HR);
        moveUp.setPrefWidth(50*WR);
        moveUp.setPrefHeight(50*HR);
        moveUp.setOnAction(e -> {
            firstBoard.move(Direction.UP);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            //GUIMoveUp();
        });
        moveUp.setOnKeyPressed(key);

        Button moveLeft = new Button("<");
        moveLeft.setLayoutX(10*WR);
        moveLeft.setLayoutY(60*HR);
        moveLeft.setPrefWidth(50*WR);
        moveLeft.setPrefHeight(50*HR);
        moveLeft.setOnAction(e -> {
            firstBoard.move(Direction.LEFT);
            totalMoves++;
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        moveLeft.setOnKeyPressed(key);

        Button moveDown = new Button("v");
        moveDown.setLayoutX(60*WR);
        moveDown.setLayoutY(110*HR);
        moveDown.setPrefWidth(50*WR);
        moveDown.setPrefHeight(50*HR);
        moveDown.setOnAction(e -> {
            firstBoard.move(Direction.DOWN);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        moveDown.setOnKeyPressed(key);

        Button moveRight = new Button(">");
        moveRight.setLayoutX(110*WR);
        moveRight.setLayoutY(60*HR);
        moveRight.setPrefWidth(50*WR);
        moveRight.setPrefHeight(50*HR);
        moveRight.setOnAction(e -> {
            firstBoard.move(Direction.RIGHT);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        moveRight.setOnKeyPressed(key);

        leftMenu.getChildren().addAll(moveDown, moveUp, moveRight, moveLeft);

        leftMenu.getChildren().add(leftMenuImage);

        leftMenu.getChildren().addAll(
                moves, movesContainer, totalMovesText,
                pushes, pushesContainer, totalPushesText,
                objectives, objectivesContainer, objectivesText,
                time, timeContainer, timeText,
                undoButton, undoButton.overlay,
                restartButton, restartButton.overlay
        );

        rightMenu.getChildren().add(rightMenuImage);

        rightMenu.getChildren().addAll(
                currentLevelImg, currentLevelContainer, currentLevelText,
                difficultyImg, difficultyContainer, difficultyText,
                averageRatingImg, averageRatingContainer, averageRatingText,
                mainMenuButton, mainMenuButton.overlay
        );


        middleMenu.getChildren().add(gamePane);

        Pane finalGamePane = new Pane();
        finalGamePane.setLayoutX(0);
        finalGamePane.setLayoutY(0);
        finalGamePane.setPrefWidth(windowWidth);
        finalGamePane.setMinWidth(windowWidth);
        finalGamePane.setMaxWidth(windowWidth);
        finalAchievementPane.setPrefHeight(windowHeight);
        finalGamePane.setMinHeight(windowHeight);
        finalGamePane.setMaxHeight(windowHeight);
        finalGamePane.getChildren().addAll(leftMenu, rightMenu, middleMenu);
        gameUI = new Scene(finalGamePane, windowWidth, windowHeight);
        // --------------------


        // LOG IN MENU --------
        //logInMenu = new LogInMenu(300, 400, window, mainMenu, "");
        // --------------------


        // MUSIC --------------
        String musicFileName = "src\\resources\\sound\\beat.mp3";
        Media media = new Media(new File(musicFileName).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        //mediaPlayer.setRate(1);
        mediaPlayer.setVolume(1);
        // --------------------

        window.setScene(mainMenu);
        System.out.println(WR + " " + HR);

        window.show();
    }

    public static byte getResolutionID() throws IOException, ParseException {
        JSONReader JSONDataReader = new JSONReader("data.json");
        RESOLUTION_ID = JSONDataReader.getByte("resolution");
        return RESOLUTION_ID;
    }

    public static Dimension getScreenDimension() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static float getWidthRatio(@NotNull int targetWidth) {
        return (float) targetWidth/ORIGINAL_WIDTH;
    }

    public static float getHeightRatio(@NotNull int targetHeight) {
        return (float) targetHeight/ORIGINAL_HEIGHT;
    }

    public static void prepareResolution(@NotNull Stage window) throws IOException, ParseException {
        // Available resolutions :
        // 0 : native resolution
        // 1 : 1280x720     HD
        // 2 : 1600x900
        // 3 : 1920x1080    FHD
        // 4 : 2560x1440    WQHD
        // 5 : 3840x2160    UHD-1 (presque 4K car 4K = 4096x2160)

        Dimension dimension;

        byte resolutionID = getResolutionID();
        switch (resolutionID) {
            case 0:
                dimension = getScreenDimension();
                break;
            case 1:
                dimension = new Dimension(1280, 720);
                break;
            case 2:
                dimension = new Dimension(1600, 900);
                break;
            case 3:
                dimension = new Dimension(1920, 1080);
                break;
            case 4:
                dimension = new Dimension(2560, 1440);
                break;
            case 5:
                dimension = new Dimension(3840, 2160);
                break;
            case 6:
                dimension = new Dimension(640, 360);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resolutionID);
        }

        if (dimension.width == getScreenDimension().width) {
            fullscreen = true;
        }

        WR = getWidthRatio(dimension.width);
        HR = getHeightRatio(dimension.height);

        window.setWidth(windowWidth*WR);
        window.setHeight(windowHeight*HR);
        window.setX(windowX);
        window.setY(windowY);
        window.setResizable(false);
        window.setFullScreen(fullscreen);
        window.setTitle("GOOOOOEY");
        window.setFullScreenExitHint("");
    }

    public void prepareMapSize(@NotNull Board board) {
        int maxWidth = board.getLevelWidth();
        limit = (int) (20*WR);
        availableSpace = (int) (1000*WR);
        // case maxWidth <= 0 doesn't need to be checked because there will always be at least one block on every line
        if (maxWidth <= limit) {
            imageLength = (int)(50*WR);
        }
        // no need to do limit < maxWidth <= 40 because maxWidth will always be > limit if we reach this point
        else if (maxWidth <= (int)((1000*WR)/25)) {
            imageLength = (int) (availableSpace/maxWidth);
        }

        remainingSpace = (int) (220*WR);
        firstPositionX = (int) (460*WR);  // 0.5*remainingSpace + leftMenuWidth*WR = 0.5*220*WR + 350*WR = 460*WR

        System.out.println("image length       :   " + imageLength + " px");
        System.out.println("limit              :   " + limit + " img");
        System.out.println("available space    : " + availableSpace + " px");
        System.out.println("remaining space    :  " + remainingSpace + " px");
        System.out.println("pos x of first img :  " + firstPositionX + " px");
        // TODO : use computed values to show a correct sized map
    }

    private int findPlayerX(@NotNull Board board) {
        int x = 0;
        blockList = board.getBlockList();
        for (int indexY = 0; indexY < 20; indexY++) {
            for (int indexX = 0; indexX < 20; indexX++) {
                if (blockList[indexY][x] instanceof Player) {
                    x = indexX;
                    break;
                }
            }
        }
        return x;
    }

    private int findPlayerY(@NotNull Board board) {
        int y = 0;
        blockList = board.getBlockList();
        for (int indexY = 0; indexY < 20; indexY++) {
            for (int indexX = 0; indexX < 20; indexX++) {
                if (blockList[indexY][indexX] instanceof Player) {
                    y = indexY;
                    break;
                }
            }
        }
        return y;
    }

    private void closeProgram() {
        boolean closeReply = ConfirmBox.display("Warning", "You're about to exit the program. Are you sure ?");
        if (closeReply) {
            window.close();
        }
    }

    public void setMap(@NotNull Board board, @NotNull Pane gamePane) throws FileNotFoundException {
        //gamePane.getChildren().remove(tempImage);
        blockList = board.getBlockList();

        final int spaceConstant = (int) ((imageLength/2)/WR);

        gamePane.getChildren().removeAll(gamePane.getChildren());

        for (int y = 0; y < board.getLevelHeight(); y++) {
            for (int x = 0; x < blockList[y].length; x++) {
                String fileName = "";

                try {
                    if (blockList[y][x] instanceof Wall) {
                        fileName = "wall.png";
                    } else if ((blockList[y][x] instanceof Box) && !(blockList[y][x] instanceof  BoxOnObj)) {
                        fileName = "box.png";
                    } else if (blockList[y][x] instanceof Player) {
                        if (playerFacing == Direction.DOWN) {
                            fileName = "player down.png";
                        }
                        else if (playerFacing == Direction.UP) {
                            fileName = "player up.png";
                        }
                        else if (playerFacing == Direction.LEFT) {
                            fileName = "player left.png";
                        }
                        else if (playerFacing == Direction.RIGHT) {
                            fileName = "player right.png";
                        }
                    } else if (blockList[y][x] instanceof BoxOnObj) {
                        fileName = "boxonobjective.png";
                    } else if (blockList[y][x] instanceof Goal) {
                        fileName = "objective.png";
                    } else if (blockList[y][x] == null) {
                        fileName = "air.png";
                    } else {
                        fileName = "air.png";
                    }
                    CustomImage tempImage = new CustomImage((int)(x*spaceConstant), (int)(y*spaceConstant), WR, HR, fileName);
                    tempImage.setLayoutX(tempImage.getX_());
                    tempImage.setLayoutY(tempImage.getY_());
                    tempImage.setWidth(tempImage.getWidth_());
                    tempImage.setHeight(tempImage.getHeight_());
                    gamePane.getChildren().add(tempImage);
                }
                catch (ArrayIndexOutOfBoundsException error) {
                }

            }
        }
        if (board.isWin() && !currentLevelIsWon) {
            AlertBox.display("Victory !", "You won !");
            currentLevelIsWon = true;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
