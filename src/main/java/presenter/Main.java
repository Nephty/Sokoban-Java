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
import view.*;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("ALL")
public class Main extends Application {

    Stage window;
    Scene achievementsMenu;

    static AudioPlayer audioPlayer;
    static AudioPlayer effectPlayer;

    static final int windowX = 0;
    static final int windowY = 0;
    static final int windowWidth = 1920;
    static final int windowHeight = 1080;

    static final int ORIGINAL_WIDTH = 1920;
    static final int ORIGINAL_HEIGHT = 1080;
    static float WR, HR;
    static byte RESOLUTION_ID;

    static boolean fullscreen = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        prepareResolution(window);

        Pane mainMenuPanel = new Pane();

        CustomImage background = new CustomImage(windowX, windowY, WR, HR, "background.png");
        MainMenu mainMenu = new MainMenu(mainMenuPanel, windowWidth, windowHeight, WR, HR, window, background);
        // TODO : move the resolution setup

        // Set all buttons & overlays


        CustomButton backButtonGame = new CustomButton(0, 0, WR, HR, "back button.png");
        CustomButton backButtonAchievements = new CustomButton((int)((windowWidth-480-10)), (int)((windowHeight-96-10)), WR, HR, "back button.png");

        // --------------------

        //MUSIC --------------

        setAudioPlayers();
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

        // EXCEPTION FOR CUSTOMIMAGE
        background.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (mainMenu.getPlayInterface()) {
                mainMenu.getCampaignButton().setVisible(false);
                mainMenu.getTutorialButton().setVisible(false);
                mainMenu.getFreePlayButton().setVisible(false);
                mainMenu.getRandomButton().setVisible(false);
                mainMenu.setPlayInterface(false);
            }
        });
        // --------------------
        // --------------------


        mainMenuPanel.getChildren().addAll(background,
                mainMenu.getPlayButton(), mainMenu.getPlayButton().overlay,
                mainMenu.getOptionsButton(), mainMenu.getOptionsButton().overlay,
                mainMenu.getQuitButton(), mainMenu.getQuitButton().overlay,
                mainMenu.getTutorialButton(), mainMenu.getTutorialButton().overlay,
                mainMenu.getCampaignButton(), mainMenu.getCampaignButton().overlay,
                mainMenu.getFreePlayButton(), mainMenu.getFreePlayButton().overlay,
                mainMenu.getRandomButton(), mainMenu.getRandomButton().overlay,
                mainMenu.getAchievementsButton(), mainMenu.getAchievementsButton().overlay);


        // OPTIONS ------------
        CustomImage optionsBackground = new CustomImage(windowX, windowY, WR, HR, "background empty.png");

        Pane optionsPane = new Pane();

        OptionsMenu optionsMenu = new OptionsMenu(optionsPane, windowWidth, windowHeight, WR, HR, optionsBackground);

        optionsMenu.getBackButtonOptions().setOnClick(e -> {
            String music = optionsMenu.getMusicField().getText();
            String effect = optionsMenu.getEffectField().getText();

            //If the user return to the main menu and leaves the field blank, we set the volume with
            //the volume he had when he openned the game.
            if (music.isEmpty()){
                music = optionsMenu.getStarterMusicVolume();
                optionsMenu.getMusicField().setText(music);
            }
            if (effect.isEmpty()){
                effect = optionsMenu.getStarterEffectVolume();
                optionsMenu.getEffectField().setText(effect);
            }
            try {
                JSONWriter writer = new JSONWriter("data.json");
                writer.set("music",music);
                writer.set("effect", effect);
                audioPlayer.changeVolume(Double.valueOf(music));
                effectPlayer.changeVolume(Double.valueOf(effect));
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
            } catch (IOException | ParseException exc) {
                exc.printStackTrace();
            }

        });
        optionsPane.getChildren().addAll(optionsBackground, optionsMenu.getBackButtonOptions(),
                optionsMenu.getBackButtonOptions().overlay,
                optionsMenu.getResolution(), optionsMenu.getMusicVolume(), optionsMenu.getEffectVolume());

        mainMenu.getOptionsButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(optionsMenu);
                window.setFullScreen(fullscreen);
            }
        });

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


        // LEVEL SELECTOR ----

        Pane campaignSelectorPanel = new Pane();
        LevelSelector campaignSelector = new CampaignSelector(campaignSelectorPanel,windowWidth, windowHeight, WR, HR);

        campaignSelectorPanel.getChildren().addAll(campaignSelector.getFinalPane());

        campaignSelector.getBackButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });

        mainMenu.getCampaignButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(campaignSelector);
                window.setFullScreen(fullscreen);
                campaignSelector.setSelectors();
            }
        });
        // --------------------


        //FREE PLAY ------
        Pane freePlayPanel = new Pane();
        LevelSelector freePlaySelector = new FreePlaySelector(freePlayPanel, windowWidth, windowHeight, WR, HR);

        freePlayPanel.getChildren().addAll(freePlaySelector.getFinalPane());

        freePlaySelector.getBackButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });

        mainMenu.getFreePlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(freePlaySelector);
                window.setFullScreen(fullscreen);
                freePlaySelector.setSelectors();
            }
        });

        // PLAY ---------------
        Pane playingMenuPanel = new Pane();
        PlayingMenu playingMenu = new PlayingMenu(playingMenuPanel, windowWidth, windowHeight, WR, HR, window, audioPlayer, effectPlayer);

        playingMenuPanel.getChildren().addAll(
            playingMenu.getFinalPane()
        );

        playingMenu.getMainMenuButton().overlay.setOnMouseClicked(e -> {
            window.setScene(mainMenu);
            window.setFullScreen(fullscreen);
        });

        campaignSelector.getPlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                try{
                    playingMenu.setLevel(campaignSelector.getSelectedLevel());
                    campaignSelector.getPlayButton().setVisible(false);
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                    campaignSelector.getResumeButton().setVisible(true);
                    campaignSelector.setHasSelected(true);
                }catch(Exception exc){
                    exc.printStackTrace();
                }
            }
        });

        campaignSelector.getResumeButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(playingMenu);
                window.setFullScreen(fullscreen);
            }
        });

        freePlaySelector.getPlayButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            if (e.getButton() == MouseButton.PRIMARY) {
                try{
                    playingMenu.setLevel(freePlaySelector.getStringLevel());
                    freePlaySelector.getPlayButton().setVisible(false);
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                    freePlaySelector.getResumeButton().setVisible(true);
                    freePlaySelector.setHasSelected(true);
                }catch(Exception exc){
                    exc.printStackTrace();
                }
            }
        });

        freePlaySelector.getResumeButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(playingMenu);
                window.setFullScreen(fullscreen);
            }
        });

        mainMenu.getRandomButton().overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                try{
                    Generate tmpGen = new Generate();
                    playingMenu.setLevel(tmpGen.content);
                    window.setScene(playingMenu);
                    window.setFullScreen(fullscreen);
                }catch(Exception exc){
                    exc.printStackTrace();
                }
            }
        });

        playingMenu.getRickRollImage().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setScene(mainMenu);
                window.setFullScreen(fullscreen);
                playingMenu.getRickRollImage().setVisible(false);
                audioPlayer.setMusic(audioPlayer.getBeat());
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

        // TODO : make a border image for full screen and one for not fullscreen

        window.setScene(mainMenu);

        window.show();
    }

    /**
    * Reads the ID of the selected resolution in the data.json file and returns it.
     * @return byte The selected ID written in the data.json file
     * @throws IOException Occurs if the provided file isn't found
     * @throws ParseException Occurs if the file is not readable
    */
    public static byte getResolutionID() throws IOException, ParseException {
        JSONReader JSONDataReader = new JSONReader("data.json");
        RESOLUTION_ID = JSONDataReader.getByte("resolution");
        return RESOLUTION_ID;
    }


    /**
     * Create a dimension object with the screen width and height as parameters and returns it.
     * @return Dimension Dimension object containing the width and the height of the screen
     */
    public static Dimension getScreenDimension() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Computes the width ratio to scale down the images according to the selected resolution.
     * @param targetWidth The width selected by the user
     * @return float The ratio between the reference width (1920) and the desired width
     */
    public static float getWidthRatio(int targetWidth) {
        return (float) targetWidth/ORIGINAL_WIDTH;
    }

    /**
     * Computes the height ratio to scale down the images according to the selected resolution.
     * @param targetHeight The height selected by the user
     * @return float The ratio between the reference height (1080) and the desired height
     */
    public static float getHeightRatio(int targetHeight) {
        return (float) targetHeight/ORIGINAL_HEIGHT;
    }

    /**
     * Create a Dimension object with the selected resolution, sets the width and height ratio, the fullscreen
     * variable according to the selected resolution, the width, the height and the position of the window,
     * as well as its title and fullscreen mode.
     * @param window The window that will be prepared
     * @throws IOException Occurs if the file read for the resolution ID isn't found
     * @throws ParseException Occurs if the file read for the resolution ID in not readable
     */
    public static void prepareResolution(Stage window) throws IOException, ParseException {
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


    /**
     * Safely closes the program by prompting a warning to the user and asking confirmation.
     */
    private void closeProgram() {
        boolean closeReply = ConfirmBox.display("Warning", "You're about to exit the program. Are you sure ?");
        if (closeReply) {
            window.close();
        }
    }

    private void setAudioPlayers() throws IOException, ParseException{
        audioPlayer = new AudioPlayer();
        effectPlayer = new AudioPlayer();
        JSONReader reader= new JSONReader("data.json");
        audioPlayer.changeVolume(Double.valueOf(reader.getString("music")));
        effectPlayer.changeVolume(Double.valueOf(reader.getString("effect")));
    }

    public static boolean isFullscreen(){
        return fullscreen;
    }

    public static void main(String[] args) {
        if (args != null && args.length != 0) {
            if (args.length != 3){
                throw new IllegalArgumentException("Only 3 arguments are allowed (input.xsb map - .mov file" +
                        " - output.xsb file name ");
            }else {
                ArrayList<String> stringMap = Fichier.loadFile(args[0], "freePlay");
                ArrayList<Direction> moves = LevelSaver.getHistory(args[1], "");
                Board map = new Board(stringMap);
                map.applyMoves(moves);
                stringMap = map.toArrayList();
                try {
                    Fichier.saveFile(args[2], "freePlay", stringMap);
                    System.exit(0);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            launch(args);
        }
    }
}