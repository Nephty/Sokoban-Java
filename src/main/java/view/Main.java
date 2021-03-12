package main.java.view;

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
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    final int windowX = 0;
    final int windowY = 0;
    final int windowWidth = 1920;
    final int windowHeight = 1080;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        Pane panel = new Pane();
        mainMenu = new Scene(panel, windowWidth, windowHeight);

        GridPane gamePanel = new GridPane();
        gamePanel.setPadding(new Insets(10, 10, 10, 10));
        gamePanel.setVgap(50);
        gamePanel.setHgap(50);

        CustomImage background = new CustomImage(windowX, windowY, 1920, 1080, "background.png");


        // Set all buttons & overlays
        CustomButton playButton = new CustomButton((1920/2)-(480/2), (1080/2)+25-96-25, 480, 96, "play button.png");
        CustomButton optionsButton = new CustomButton((1920/2)-(480/2), (1080/2)+25, 480, 96, "options button.png");
        CustomButton quitButton = new CustomButton((1920/2)-(480/2), (1080/2)+25+96+25+96+25, 480, 96, "quit button.png");
        CustomButton backButtonGame = new CustomButton(0, 0, 480, 96, "back button.png");
        CustomButton backButtonOptions = new CustomButton(1920-480-5, 1080-96-5, 480, 96, "back button.png");
        CustomButton backButtonAchievements = new CustomButton(1920-480-10, 1080-96-10, 480, 96, "back button.png");
        CustomButton achievementsButton = new CustomButton((1920/2)-(480/2), (1080/2)+25+96+25, 480, 96, "achievements button.png");
        CustomButton campaignButton = new CustomButton((1920/2)-(480/2)+480+15, (1080/2)-96-(96/2)-5, 480, 96, "campaign button.png");
        CustomButton tutorialButton = new CustomButton((1920/2)-(480/2)+480+15, (1080/2)-96+5+(96/2), 480, 96, "tutorial button.png");
        campaignButton.setVisible(false);
        tutorialButton.setVisible(false);
        // --------------------

        AtomicBoolean playInterface = new AtomicBoolean(false);

        // BUTTONS ACTIONS ----
        //playButton.setOnClick(e -> window.setScene(gameUI));
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
        });
        campaignButton.overlay.setOnMouseClicked(e -> window.setScene(gameUI));
        optionsButton.setOnClick(e -> window.setScene(optionsMenu));
        achievementsButton.setOnClick(e -> window.setScene(achievementsMenu));
        quitButton.setOnClick(e -> closeProgram());
        backButtonGame.setOnClick(e -> window.setScene(mainMenu));
        backButtonOptions.setOnClick(e -> window.setScene(mainMenu));
        backButtonAchievements.setOnClick(e -> window.setScene(mainMenu));
        // EXCEPTION FOR CUSTOMIMAGE
        background.setOnMouseClicked(e -> {
            if (playInterface.get()) {
                campaignButton.setVisible(false);
                tutorialButton.setVisible(false);
                playInterface.set(false);
            }
        });
        // --------------------
        // --------------------


        panel.getChildren().addAll(background, playButton, playButton.overlay, optionsButton, optionsButton.overlay, quitButton, quitButton.overlay, tutorialButton, tutorialButton.overlay, campaignButton, campaignButton.overlay, achievementsButton, achievementsButton.overlay);
        gamePanel.getChildren().addAll(backButtonGame, backButtonGame.overlay);


        // MUSIC --------------
        String musicFileName = "src\\resources\\sound\\beat.mp3";
        Media media = new Media(new File(musicFileName).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        //mediaPlayer.setRate(1);
        mediaPlayer.setVolume(0.8);
        // --------------------


        // OPTIONS ------------
        CustomImage optionsBackground = new CustomImage(windowX, windowY, 1920, 1080, "options menu.png");


        Pane optionsBackgroundPane = new Pane();
        optionsBackgroundPane.setPrefWidth(1920);
        optionsBackgroundPane.setPrefHeight(1080);
        optionsBackgroundPane.getChildren().addAll(optionsBackground);


        Pane optionsPane = new Pane();

        CustomImage soundScale = new CustomImage(10, 100, 206, 36, "scale.png");
        Rectangle soundScaleBar = new Rectangle(10+3-1, 100+3-1, (200+2)*0.8, 30+2);
        soundScaleBar.setFill(Color.rgb(90, 90, 255, 0.85));
        soundScale.setOnMouseClicked((MouseEvent event) -> {
            soundScaleBar.setWidth(event.getSceneX() - soundScaleBar.getX());
            mediaPlayer.setVolume(soundScaleBar.getWidth()/soundScale.getWidth_());
        });
        soundScaleBar.setOnMouseClicked((MouseEvent event) -> {
            soundScaleBar.setWidth(event.getSceneX() - soundScaleBar.getX());
            mediaPlayer.setVolume(soundScaleBar.getWidth()/soundScale.getWidth_());
        });
        AtomicBoolean dragging = new AtomicBoolean(true);

        optionsPane.getChildren().addAll(backButtonOptions, backButtonOptions.overlay, soundScale, soundScaleBar);



        Pane finalOptionsPane = new Pane();
        finalOptionsPane.setPrefWidth(1920);
        finalOptionsPane.setPrefHeight(1080);
        finalOptionsPane.getChildren().addAll(optionsBackgroundPane, optionsPane);


        optionsMenu = new Scene(finalOptionsPane, windowWidth, windowHeight);
        // --------------------


        // ACHIEVEMENTS --------
        GridPane achievementsPane = new GridPane();
        achievementsPane.setVgap(50);
        achievementsPane.setHgap(50);

        GridPane.setConstraints(backButtonAchievements, 10, 10);

        CustomImage achievement1Image = new CustomImage(0, 0, 100, 100, "achievement1.png");
        CustomImage achievement1Text = new CustomImage(0, 0, 200, 50, "achievement1 text.png");
        Achievement achievement1 = new Achievement(achievement1Image, achievement1Text, 2, 1, achievementsPane);

        CustomImage achievement2Image = new CustomImage(0, 0, 100, 100, "achievement2.png");
        CustomImage achievement2Text = new CustomImage(0, 0, 200, 50, "achievement2 text.png");
        Achievement achievement2 = new Achievement(achievement2Image, achievement2Text, 2, 2, achievementsPane);

        CustomImage achievement3Image = new CustomImage(0, 0, 100, 100, "achievement3.png");
        CustomImage achievement3Text = new CustomImage(0, 0, 200, 50, "achievement3 text.png");
        Achievement achievement3 = new Achievement(achievement3Image, achievement3Text, 2, 3, achievementsPane);


        CustomImage achievementBackground = new CustomImage(windowX, windowY, 1920, 1080, "achievements menu.png");
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
        achievementsBackgroundPane.setPrefWidth(1920);
        achievementsBackgroundPane.setPrefHeight(1080);
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
        leftMenu.setPrefWidth(350);
        leftMenu.setMaxWidth(350);
        leftMenu.setMinWidth(350);
        leftMenu.setPrefHeight(windowHeight);
        leftMenu.setMaxHeight(windowHeight);
        leftMenu.setMinHeight(windowHeight);
        leftMenu.setLayoutX(0);
        leftMenu.setLayoutY(0);
        Rectangle leftR = new Rectangle(0, 0, 350, 1080);
        leftR.setFill(Color.YELLOW);
        //leftMenu.getChildren().add(leftR);

        VBox rightMenu = new VBox();
        rightMenu.setPrefWidth(350);
        rightMenu.setMaxWidth(350);
        rightMenu.setMinWidth(350);
        rightMenu.setPrefHeight(windowHeight);
        rightMenu.setMaxHeight(windowHeight);
        rightMenu.setMinHeight(windowHeight);
        rightMenu.setLayoutX(350+1220);
        rightMenu.setLayoutY(0);
        Rectangle rightR = new Rectangle(0, 0, 350, 1080);
        rightR.setFill(Color.YELLOW);
        rightMenu.getChildren().add(rightR);

        Pane middleMenu = new Pane();
        middleMenu.setPrefWidth(1220);
        middleMenu.setMaxWidth(1220);
        middleMenu.setMinWidth(1220);
        middleMenu.setPrefHeight(windowHeight);
        middleMenu.setMaxHeight(windowHeight);
        middleMenu.setMinHeight(windowHeight);
        middleMenu.setLayoutX(350);
        middleMenu.setLayoutY(0);
        Rectangle midR = new Rectangle(0, 0, 1220, 1080);
        midR.setFill(Color.BLUE);
        middleMenu.getChildren().add(midR);

        Pane gamePane = new Pane();
        gamePane.setLayoutX(60);
        gamePane.setLayoutY(0);

        Board firstBoard = new Board();
        for (Block[] i : firstBoard.getBlockList()) {
            for (Block j : i) {
                System.out.println(j);
            }
        }

        Block[][] blockList = firstBoard.getBlockList();

        setMap(firstBoard, gamePane);

        Button moveUp = new Button("^");
        moveUp.setLayoutX(60);
        moveUp.setLayoutY(10);
        moveUp.setPrefWidth(50);
        moveUp.setPrefHeight(50);
        moveUp.setOnAction(e -> {
            firstBoard.move("UP");
            System.out.println(player.getX() + " " + player.getY());
            System.out.println(firstBoard.getBlockList()[player.getY()][player.getX()]);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            //GUIMoveUp();
        });
        leftMenu.getChildren().add(moveUp);

        Button moveLeft = new Button("<");
        moveLeft.setLayoutX(10);
        moveLeft.setLayoutY(60);
        moveLeft.setPrefWidth(50);
        moveLeft.setPrefHeight(50);
        moveLeft.setOnAction(e -> {
            firstBoard.move("LEFT");
            System.out.println(player.getX() + " " + player.getY());
            System.out.println(firstBoard.getBlockList()[player.getY()][player.getX()]);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        leftMenu.getChildren().add(moveLeft);

        Button moveDown = new Button("v");
        moveDown.setLayoutX(60);
        moveDown.setLayoutY(110);
        moveDown.setPrefWidth(50);
        moveDown.setPrefHeight(50);
        moveDown.setOnAction(e -> {
            firstBoard.move("DOWN");
            System.out.println(player.getX() + " " + player.getY());
            System.out.println(firstBoard.getBlockList()[player.getY()][player.getX()]);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        leftMenu.getChildren().add(moveDown);

        Button moveRight = new Button(">");
        moveRight.setLayoutX(110);
        moveRight.setLayoutY(60);
        moveRight.setPrefWidth(50);
        moveRight.setPrefHeight(50);
        moveRight.setOnAction(e -> {
            firstBoard.move("RIGHT");
            System.out.println(player.getX() + " " + player.getY());
            System.out.println(firstBoard.getBlockList()[player.getY()][player.getX()]);
            try {
                setMap(firstBoard, gamePane);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        leftMenu.getChildren().add(moveRight);


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


        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });



        // LOG IN MENU --------
        logInMenu = new LogInMenu(300, 400, window, mainMenu, "");
        // --------------------


        //JSONReader JSONDataReader = new JSONReader("data.json");
        //JSONWriter JSONDataWriter = new JSONWriter("data.json");

        player = new Player(12, 8, "");

        window.setTitle("GUI");
        window.show();
    }

    private void GUIMoveUp(Board board, GridPane gamePane) {
        int x = findPlayerX(board);
        int y = findPlayerX(board);
        //GridPane.setConstraints(gamePane.get);
    }

    private int findPlayerX(Board board) {
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

    private int findPlayerY(Board board) {
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

    public void setMap(Board board, Pane gamePane) throws FileNotFoundException {
        //gamePane.getChildren().remove(tempImage);
        blockList = board.getBlockList();

        final int spaceConstant = 25;

        gamePane.getChildren().removeAll(gamePane.getChildren());

        for (int y = 0; y < blockList.length; y++) {
            for (int x = 0; x < blockList[y].length; x++) {
                String fileName = "";

                try {
                    if (blockList[y][x] instanceof Wall) {
                        fileName = "wall.png";
                    } else if (blockList[y][x] instanceof Box) {
                        fileName = "box.png";
                    } else if (blockList[y][x] instanceof Player) {
                        fileName = "player.png";
                    } else if (blockList[y][x] instanceof BoxOnObj) {
                        fileName = "boxonobjective.png";
                    } else if (blockList[y][x] instanceof Objectiv) {
                        fileName = "objective.png";
                    } else if (blockList[y][x] == null) {
                        fileName = "air.png";
                    } else {
                        fileName = "air.png";
                    }
                    CustomImage tempImage = new CustomImage(x*spaceConstant, y*spaceConstant, 50, 50, fileName);
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
        if (board.isWin()) {
            AlertBox.display("Victory !", "You won !");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
