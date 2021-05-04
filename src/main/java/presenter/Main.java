package presenter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import org.json.simple.parser.ParseException;
import view.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ALL")
/**
 * The <code>Main</code> class is the class that will be executed when running the program.
 */
public class Main extends Application {

    Stage window;
    Scene achievementsMenu;

    public static AudioPlayer audioPlayer;
    public static AudioPlayer effectPlayer;


    static final int windowX = 0;
    static final int windowY = 0;
    static final int windowWidth = 1920;
    static final int windowHeight = 1080;

    static final int ORIGINAL_WIDTH = 1920;
    static final int ORIGINAL_HEIGHT = 1080;
    static float WR, HR;
    static byte RESOLUTION_ID;

    public static boolean fullscreen = false;

    /**
     * The main method that will be ran when starting the game.
     *
     * @param primaryStage The window that will contain almost all the content
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            window = primaryStage;

            prepareResolution(window);

            Pane mainMenuPanel = new Pane();

            CustomImage background = new CustomImage(windowX, windowY, WR, HR, "background.png");
            MainMenu mainMenu = new MainMenu(mainMenuPanel, windowWidth, windowHeight, WR, HR, window, background);

            // Set all buttons & overlays

            CustomButton backButtonGame = new CustomButton(0, 0, WR, HR, "back button.png");
            CustomButton backButtonAchievements = new CustomButton((int) ((windowWidth - 480 - 10)), (int) ((windowHeight - 96 - 10)), WR, HR, "back button.png");

            // --------------------


            //MUSIC --------------

            setAudioPlayers(); // TODO : Set the exceptions messages

            // --------------------


            // BUTTONS ACTIONS (SCENE SWITCHERS) ----

            mainMenu.getAchievementsButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(achievementsMenu);
                    window.setFullScreen(fullscreen);
                }
            });

            mainMenu.getQuitButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    closeProgram();
                }
            });

            backButtonAchievements.setOnClick(e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            background.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (mainMenu.getPlayInterface()) {
                    mainMenu.getCampaignButton().setVisible(false);
                    mainMenu.getTutorialButton().setVisible(false);
                    mainMenu.getFreePlayButton().setVisible(false);
                    mainMenu.getRandomButton().setVisible(false);
                    mainMenu.setPlayInterface(false);
                }
            });

            mainMenuPanel.getChildren().addAll(background,
                    mainMenu.getPlayButton(), mainMenu.getPlayButton().overlay,
                    mainMenu.getOptionsButton(), mainMenu.getOptionsButton().overlay,
                    mainMenu.getQuitButton(), mainMenu.getQuitButton().overlay,
                    mainMenu.getTutorialButton(), mainMenu.getTutorialButton().overlay,
                    mainMenu.getCreatorButton(), mainMenu.getCreatorButton().overlay,
                    mainMenu.getCampaignButton(), mainMenu.getCampaignButton().overlay,
                    mainMenu.getFreePlayButton(), mainMenu.getFreePlayButton().overlay,
                    mainMenu.getRandomButton(), mainMenu.getRandomButton().overlay,
                    mainMenu.getAchievementsButton(), mainMenu.getAchievementsButton().overlay);

            // EDITOR ----------
            Pane creatorPane = new Pane();

            CreatorMenu creatorMenu = new CreatorMenu(creatorPane, windowWidth, windowHeight, WR, HR);

            creatorPane.getChildren().addAll(
                    creatorMenu.getFinalPane()
            );

            creatorMenu.getMainMenuButton().setOnClick(e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            mainMenu.getCreatorButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(creatorMenu);
                    window.setFullScreen(fullscreen);
                }
            });
            // ---------------------


            /// OPTIONS ------------
            CustomImage optionsBackground = new CustomImage(windowX, windowY, WR, HR, "background empty.png");

            Pane optionsPane = new Pane();
            OptionsMenu optionsMenu = new OptionsMenu(optionsPane, windowWidth, windowHeight, WR, HR, optionsBackground, audioPlayer, effectPlayer);

            optionsMenu.getBackButtonOptions().setOnClick(e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            optionsPane.getChildren().addAll(optionsBackground, optionsMenu.getBackButtonOptions(),
                    optionsMenu.getBackButtonOptions().overlay,
                    optionsMenu.getResolution(), optionsMenu.getMusicVolume(), optionsMenu.getEffectVolume(),
                    optionsMenu.getUpControl(), optionsMenu.getDownControl(), optionsMenu.getRightControl(),
                    optionsMenu.getLeftControl(), optionsMenu.getRestartControl(), optionsMenu.getLoadControl(),
                    optionsMenu.getSaveControl(), optionsMenu.getOpenConsControl(), optionsMenu.getCloseConsControl());

            mainMenu.getOptionsButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(optionsMenu);
                    window.setFullScreen(fullscreen);
                }
            });

            // --------------------


            // ACHIEVEMENTS -------- TODO : Set the AchivementMenu
            /*
            GridPane achievementsPane = new GridPane();
            achievementsPane.setVgap(50);
            achievementsPane.setHgap(50);

            GridPane.setConstraints(backButtonAchievements, 10, 10);

            CustomImage achievement1Image = new CustomImage(0, 0, WR, HR, "achievement1.png");
            CustomImage achievement1Text = new CustomImage(0, 0, WR, HR, "achievement1 text.png");
            Achievement achievement1 = new Achievement(achievement1Image, achievement1Text, 2, 1, WR, HR, achievementsPane);

            CustomImage achievement2Image = new CustomImage(0, 0, WR, HR, "achievement2.png");
            CustomImage achievement2Text = new CustomImage(0, 0, WR, HR, "achievement2 text.png");
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
                    temporaryWriter.set("completed levels", String.valueOf(initValue + 1));
                    if (initValue == 0) {
                        achievement1.complete();
                    } else if (initValue == 4) {
                        achievement2.complete();
                    } else if (initValue == 9) {
                        achievement3.complete();
                    }
                } catch (IOException | ParseException ioException) {
                    AlertBox.display("Error", "An error occured while writing in the JSON file\n" +
                            ioException.getMessage());
                }
            });
            addLevel.setLayoutX(600);
            addLevel.setLayoutY(600);

            Pane achievementsBackgroundPane = new Pane();
            achievementsBackgroundPane.setPrefWidth(windowWidth * WR);
            achievementsBackgroundPane.setPrefHeight(windowHeight * WR);
            achievementsBackgroundPane.getChildren().addAll(achievementBackground, backButtonAchievements, backButtonAchievements.overlay, addLevel);

            achievementsPane.getChildren().addAll(
                    achievement1.image, achievement1.overlay, achievement1.text, achievement1.overlayDone,
                    achievement2.image, achievement2.overlay, achievement2.text, achievement2.overlayDone,
                    achievement3.image, achievement3.overlay, achievement3.text, achievement3.overlayDone,
                    addLevel);


             */
            Pane finalAchievementPane = new Pane();
            finalAchievementPane.setPrefWidth(windowWidth);
            finalAchievementPane.setPrefHeight(windowHeight);
            finalAchievementPane.getChildren().addAll();//achievementsBackgroundPane, achievementsPane);


            achievementsMenu = new Scene(finalAchievementPane, windowWidth, windowHeight);
            // --------------------


            // LEVEL SELECTOR ----

            Pane campaignSelectorPanel = new Pane();
            LevelSelector campaignSelector = new CampaignSelector(campaignSelectorPanel, windowWidth, windowHeight, WR, HR);

            campaignSelectorPanel.getChildren().addAll(campaignSelector.getFinalPane());

            campaignSelector.getBackButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            mainMenu.getCampaignButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    campaignSelector.setSelectors();
                    window.setScene(campaignSelector);
                    window.setFullScreen(fullscreen);
                }
            });
            // --------------------


            // FREE PLAY ------
            Pane freePlayPanel = new Pane();
            LevelSelector freePlaySelector = new FreePlaySelector(freePlayPanel, windowWidth, windowHeight, WR, HR);

            freePlayPanel.getChildren().addAll(freePlaySelector.getFinalPane());

            freePlaySelector.getBackButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            mainMenu.getFreePlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    freePlaySelector.setSelectors();
                    window.setScene(freePlaySelector);
                    window.setFullScreen(fullscreen);
                }
            });

            // RANDOM ---------------
            Pane randomPanel = new Pane();
            RandomSelector randomMenu = new RandomSelector(randomPanel, windowWidth, windowHeight, WR, HR);
            randomPanel.getChildren().addAll(randomMenu.getFinalPane());

            randomMenu.getBackButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            });

            mainMenu.getRandomButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    randomMenu.setSelectors();
                    window.setScene(randomMenu);
                    window.setFullScreen(fullscreen);
                }
            });

            // PLAY ---------------
            Pane playingMenuPanel = new Pane();
            PlayingMenu playingMenu = new PlayingMenu(playingMenuPanel, windowWidth, windowHeight, WR, HR, audioPlayer, effectPlayer);

            playingMenuPanel.getChildren().addAll(
                    playingMenu.getFinalPane()
            );

            playingMenu.getMainMenuButton().overlay.setOnMouseClicked(e -> {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
                playingMenu.getStopWatch().stop();
            });

            campaignSelector.getPlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    try {
                        byte currentLevel = (byte) campaignSelector.getSelectedLevel();
                        String levelFileName = "level";
                        if (currentLevel < 10) {
                            levelFileName += "0";
                        }
                        levelFileName += String.valueOf(currentLevel);
                        levelFileName += ".xsb";
                        ArrayList<String> level = Fichier.loadFile(levelFileName, "campaign");
                        playingMenu.setLevel(level, String.valueOf(currentLevel), "campaign");

                        campaignSelector.getPlayButton().setVisible(false);
                        window.setScene(playingMenu);
                        window.setFullScreen(fullscreen);
                        campaignSelector.getResumeButton().setVisible(true);
                        freePlaySelector.setHasSelected(true);
                    } catch (IOException exc) {
                        AlertBox.display("Error", "An error occured while trying to load the level\n" +
                                exc.getMessage());
                    }
                }
            });

            campaignSelector.getResumeButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                    playingMenu.getStopWatch().start();
                }
            });

            freePlaySelector.getPlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    try {
                        String levelName = (String) freePlaySelector.getSelectedLevel();
                        ArrayList<String> level = Fichier.loadFile(levelName, "freePlay");
                        String[] tmp = levelName.split(".xsb");
                        levelName = tmp[0];
                        if (levelName.length() > 7) {
                            String tmpName = "";
                            for (int j = 0; j <= 5; j++) {
                                tmpName += levelName.charAt(j);
                            }
                            tmpName += "...";
                            levelName = tmpName;
                        }
                        playingMenu.setLevel(level, levelName, "freePlay");

                        freePlaySelector.getPlayButton().setVisible(false);
                        window.setScene(playingMenu);
                        window.setFullScreen(fullscreen);
                        freePlaySelector.getResumeButton().setVisible(true);
                        freePlaySelector.setHasSelected(true);
                    } catch (IOException | IllegalArgumentException exc) {
                        AlertBox.display("Error", "An error occured while trying to load the level\n" +
                                exc.getMessage());
                    }
                }
            });

            freePlaySelector.getResumeButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                }
            });

            randomMenu.getPlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    try {
                        playingMenu.setLevel((ArrayList<String>) randomMenu.getSelectedLevel(), "Random", "random");
                        System.out.println(NewGenerator.getConfig());
                        window.setScene(playingMenu);
                        window.setFullScreen(fullscreen);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            });

            playingMenu.getRickRollImage().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(mainMenu);
                    window.setFullScreen(fullscreen);
                    playingMenu.getRickRollImage().setVisible(false);
                    audioPlayer.prepareMusic(audioPlayer.getFileName());
                    audioPlayer.play();
                }
            });
            // --------------------

            // TUTORIAl -----------
            Pane tutorialPane = new Pane();
            Tutorial tutorial = new Tutorial(tutorialPane, windowWidth, windowHeight, WR, HR);

            tutorialPane.getChildren().addAll(
                    tutorial.getTutorial0(), tutorial.getTutorial1(),
                    tutorial.getTutorial2(), tutorial.getTutorial3(),
                    tutorial.getTutorial4(), tutorial.getTutorial5(),
                    tutorial.getTutorial6(), tutorial.getLeftArrow(), tutorial.getRightArrow()
            );

            mainMenu.getTutorialButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(tutorial);
                    window.setFullScreen(fullscreen);
                }
            });

            tutorial.getRightArrow().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (tutorial.getCurrentImage() == 0) {
                        window.setScene(mainMenu);
                        window.setFullScreen(fullscreen);
                    }
                }
            });

            tutorial.getLeftArrow().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (tutorial.getCurrentImage() == 0) {
                        window.setScene(mainMenu);
                        window.setFullScreen(fullscreen);
                    }
                }
            });

            // --------------------

            Console.prepare();

            NewGenerator.prepare();

            window.setScene(mainMenu);
            window.show();
        } catch (Exception e2) {
            AlertBox.display("Fatal Error", "An error occurred while loading the game\n");
            e2.printStackTrace();
            if (e2.getMessage() != null) {
                System.out.println(e2.getMessage());
            }
            System.exit(-1);
        }
    }

    /**
     * Read the data.json file and get the resolution ID written in the file.
     *
     * @return The resolution ID of the selected resolution
     */
    public static byte getResolutionID() {
        JSONReader JSONDataReader = new JSONReader("data.json");
        return JSONDataReader.getByte("resolution");
    }

    /**
     * Use the Toolkit abstract class to get the resolution of the screen as a <code>Dimension</code> object.
     *
     * @return The resolution of the screen. The first attribute is the width and the second attribute is the height
     */
    public static Dimension getScreenDimension() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Compute the width ratio according to the width of the selected resolution and the reference width.
     *
     * @param targetWidth The desired width
     * @return The ratio between the desired width and the reference width
     */
    public static float getWidthRatio(int targetWidth) {
        return (float) targetWidth / ORIGINAL_WIDTH;
    }

    /**
     * Compute the height ratio according to the height of the selected resolution and the reference height.
     *
     * @param targetHeight The desired height
     * @return The ratio between the desired height and the reference height
     */
    public static float getHeightRatio(int targetHeight) {
        return (float) targetHeight / ORIGINAL_HEIGHT;
    }

    /**
     * Create a Dimension object with the selected resolution, sets the width and height ratio, the fullscreen
     * variable according to the selected resolution, the width, the height and the position of the window,
     * as well as its title and fullscreen mode.
     *
     * @param window The window that will be prepared
     */
    public static void prepareResolution(Stage window) {
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

        window.setWidth(windowWidth * WR);
        window.setHeight(windowHeight * HR);
        window.setX(windowX);
        window.setY(windowY);
        window.setResizable(false);
        window.setFullScreen(fullscreen);
        window.setTitle("GOOOOOEY");
        window.setFullScreenExitHint("");
    }


    /**
     * Safely closes the program by prompting a warning to the user and asking confirmation.
     */
    private void closeProgram() {
        boolean closeReply = ConfirmBox.display("Warning", "You're about to exit the program. Are you sure ?");
        if (closeReply) {
            window.close();
        }
    }

    private void setAudioPlayers() {
        audioPlayer = new AudioPlayer();
        effectPlayer = new AudioPlayer();
        JSONReader reader = new JSONReader("data.json");
        audioPlayer.setVolume(Double.valueOf(reader.getString("music")));
        effectPlayer.setVolume(Double.valueOf(reader.getString("effect")));
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * The very first method on the execution pile.
     *
     * @param args args
     */
    public static void main(String[] args) {
        if (args != null && args.length != 0) {
            if (args.length == 1) {
                if (args[0].equals("integrityCheck")) {
                    IntegrityChecker.checkFileIntegrity();
                    System.out.println("Enter something to leave... ");
                    Scanner input = new Scanner(System.in);
                    input.next();
                    System.exit(0);
                } else if (args.length == 3) {
                    ArrayList<String> stringMap = Fichier.loadFile(args[0], "freePlay");
                    ArrayList<Direction> moves = LevelSaver.getHistory(args[1], "");
                    Board map = new Board(stringMap);
                    map.applyMoves(moves);
                    stringMap = map.toArrayList();
                    Fichier.saveFile(args[2], "freePlay", stringMap);
                    System.exit(0);
                } else {
                    throw new IllegalArgumentException(" Only these configurations are allowed :\n" +
                            "- 3 arguments : (input.xsb map - .mov file - output.xsb file name )\n" +
                            "- 1 argument  : \"integrityCheck\"\n" +
                            "- 0 argument");
                }
            }
        }
        else {
            launch(args);
        }
    }
}
