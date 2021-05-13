package presenter;

import javafx.application.Application;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("ALL")
/**
 * The <code>Main</code> class is the class that will be executed when running the program.
 */
public class Main extends Application {

    Stage window;

    public static AudioPlayer audioPlayer;
    public static AudioPlayer effectPlayer;
    public static final String SEPARATOR = getFileDistination();


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

            // --------------------


            //MUSIC --------------

            setAudioPlayers();

            // --------------------


            // BUTTONS ACTIONS (SCENE SWITCHERS) ----


            mainMenu.getQuitButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    closeProgram();
                }
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
                    mainMenu.getRandomButton(), mainMenu.getRandomButton().overlay);

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
                    optionsMenu.getSaveControl(), optionsMenu.getOpenConsControl(), optionsMenu.getCloseConsControl()
                    optionsMenu.getAutoPromptHBox());

            mainMenu.getOptionsButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(optionsMenu);
                    window.setFullScreen(fullscreen);
                }
            });

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
                    byte currentLevel = (byte) campaignSelector.getSelectedLevel();
                    String levelFileName = "level";
                    if (currentLevel < 10) {
                        levelFileName += "0";
                    }
                    levelFileName += String.valueOf(currentLevel);
                    levelFileName += ".xsb";
                    ArrayList<String> level = FileGetter.loadFile(levelFileName, "campaign");
                    playingMenu.setLevel(level, String.valueOf(currentLevel), "campaign");

                    campaignSelector.getPlayButton().setVisible(false);
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                    campaignSelector.getResumeButton().setVisible(true);
                    campaignSelector.setHasSelected(true);
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
                        ArrayList<String> level = FileGetter.loadFile(levelName, "freePlay");
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
                    } catch (IllegalArgumentException exc) {
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
                        playingMenu.setLevel((ArrayList<String>) randomMenu.getSelectedLevel(), "Random", "random");
                        window.setScene(playingMenu);
                        window.setFullScreen(fullscreen);
                        randomMenu.getPlayButton().setVisible(false);
                        randomMenu.getResumeButton().setVisible(true);
                        randomMenu.setHasSelected(true);
                }
            });

            randomMenu.getResumeButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                window.setScene(randomMenu);
                window.setFullScreen(fullscreen);
            });

            playingMenu.getRickRollImage().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    window.setScene(mainMenu);
                    window.setFullScreen(fullscreen);
                    playingMenu.getRickRollImage().setVisible(false);
                    audioPlayer.prepareMusic(AudioPlayer.beatFile);
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


            window.setScene(mainMenu);
            window.show();
        } catch (Exception e2) {
            AlertBox.display("Fatal Error", "An error occurred while loading the game\n");
            e2.printStackTrace();
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
                fullscreen = true;
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
     * Get the os of the user and return "/" or "\".
     * @return "/" if it's Linux/MacOS and "\" if it's Windows.
     */
    public static String getFileDistination(){
        String OS = System.getProperty("os.name");
        String res;
        if (OS.startsWith("Windows")){
            res = "\\";
        } else {
            res = "/";
        }
        return res;
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
                    ArrayList<String> stringMap = FileGetter.loadFile(args[0], "freePlay");
                    ArrayList<Direction> moves = LevelSaver.getHistory(args[1], "");
                    Board map = new Board(stringMap);
                    map.applyMoves(moves);
                    stringMap = map.toArrayList();
                    FileGetter.saveFile(args[2], "freePlay", stringMap);
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
