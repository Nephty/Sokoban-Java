package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A <code>Main</code> is a user interface and is the first UI displayed when the game is launched.
 * It contains a "Play" button that shows a "Campaign", "Tutorial" and "Freeplay" button. The first button
 * allows the user to play on the campaign levels, the second button shows the tutorial to the user and the third
 * button allows the user to play on the freeplay levels. It also contains the "Options" button that opens the options
 * menu, a "Quests" button that opens the achievements menu and a "Quit" button that closes the game.
 */
public class MainMenu
        extends Menu {

    //---------//
    // Objects //
    //---------//

    private CustomButton playButton, createButton, optionsButton, quitButton, achievementsButton, campaignButton, tutorialButton,
                        freePlayButton, randomButton;
    private CustomImage background;
    private static Stage window;

    //------//
    // Data //
    //------//

    static AtomicBoolean playInterface = new AtomicBoolean(false);

    /**
     * Create a new <code>MainMenu</code> object and prepare its attributes
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR_ The width ratio that will be used to resize the components
     * @param HR_ The height ratio that will be used to resize the components
     * @param window_ The window containing everything
     * @param background_ The background that will be displayed for the menu
     */
    public MainMenu(Parent parent_, double width_, double height_, float WR_, float HR_, Stage window_, CustomImage background_) {
        super(parent_, width_, height_, WR_, HR_, background_);
        window = window_;
        this.background = background_;
        setButtons();
        prepareButtonsActions();
    }

    /**
     * Prepare the "Play", "Options", "Quests", "Quit", "Campaign", "Tutorial" and "Freeplay" buttons.
     * The <code>EventHandlers</code> are not set here.
     */
    private void setButtons() {
        Dimension dimension = resolutionIDToDimension();
        float WR = getWidthRatio(dimension.width);
        float HR = getHeightRatio(dimension.height);

        if (dimension.width == getScreenDimension().width) {
            fullscreen = true;
        }

        this.playButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25-96-25)),
                WR, HR, "play button.png");
        this.createButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25)),
                WR, HR, "create button.png");
        this.optionsButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25+96+25)),
                WR, HR, "options button.png");
        this.achievementsButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25+96+25+96+25)),
                WR, HR, "achievements button.png");
        this.quitButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25+96+25+96+25+96+25)),
                WR, HR, "quit button.png");

        this.campaignButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)-96-(96/2)-5)),
                WR, HR, "campaign button.png");
        this.tutorialButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)+15+(96/2))),
                WR, HR, "tutorial button.png");
        this.freePlayButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)-96+5+(96/2))),
                WR, HR, "freeplay button.png");
        this.randomButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)+96+25+(96/2))),
                WR, HR, "random button.png");

        this.campaignButton.setVisible(false);
        this.tutorialButton.setVisible(false);
        this.freePlayButton.setVisible(false);
        this.randomButton.setVisible(false);
    }


    /**
     * Prepare the <code>EventHandlers</code> for the buttons and their overlays according to their respective function.
     */
    private void prepareButtonsActions() {
        this.playButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!playInterface.get()) {
                this.campaignButton.setVisible(true);
                this.tutorialButton.setVisible(true);
                this.freePlayButton.setVisible(true);
                this.randomButton.setVisible(true);
                playInterface.set(true);
            }
            else {
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.randomButton.setVisible(false);
                playInterface.set(false);
            }
        });

        this.campaignButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.campaignButton.overlay.setVisible(false);
                this.randomButton.setVisible(false);
            }
        });

        this.tutorialButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.randomButton.setVisible(false);
            }
        });

        this.freePlayButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.randomButton.setVisible(false);
            }
        });

        this.randomButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.randomButton.setVisible(false);
            }
        });
    }

    /**
     * Return the playInterface boolean that will be <code>true</code> if the three buttons "Campaign", "Tutorial" and
     * "Freeplay" are currently displayed on the screen, and <code>false</code> otherwise.
     * @return The display state of the three "Campaign", "Tutorial" and "Freeplay" buttons
     */
    public static boolean getPlayInterface() {
        return playInterface.get();
    }

    /**
     * Set a new value of the playInterface boolean. Must be set to <code>true</code> if the three buttons "Campaign",
     * "Tutorial" and "Freeplay" buttons are currently visible, and <code>false</code> otherwise.
     * @param value The new value for the playInterface boolean
     */
    public static void setPlayInterface(boolean value) {
        playInterface.set(value);
    }

    /**
     * Return the "Play" button used to switch to the <code>PlayingMenu</code>.
     * @return The "Play" button currently used
     */
    public CustomButton getPlayButton() {
        return playButton;
    }

    /**
     * Return the "Options" button used to switch to the <code>OptionsMenu</code>.
     * @return The "Options" button currently used
     */
    public CustomButton getOptionsButton() {
        return optionsButton;
    }

    /**
     * Return the "Create" button used to switch to the <code>CreatorMenu</code>
     * @return The "Create" button currently used.
     */
    public CustomButton getCreatorButton(){
        return createButton;
    }

    /**
     * Return the "Quit" button used to quit the game
     * @return The "Quit" button currently used
     */
    public CustomButton getQuitButton() {
        return quitButton;
    }

    /**
     * Return the "Achievements" button used to switch to the <code>AchievementsMenu</code>.
     * @return The "Achievements" button currently used
     */
    public CustomButton getAchievementsButton() {
        return achievementsButton;
    }

    /**
     * Return the "Campaign" button used to switch to the <code>CampaignSelector</code>.
     * @return The "Campaign" button currently used
     */
    public CustomButton getCampaignButton() {
        return campaignButton;
    }

    /**
     * Return the "Tutorial" button used to switch to the <code>Tutorial</code>.
     * @return The "Tutorial" button currently used
     */
    public CustomButton getTutorialButton() {
        return tutorialButton;
    }

    /**
     * Return the "Freeplay" button used to switch to the <code>FreePlaySelector</code>.
     * @return The "Freeplay" button currently used
     */
    public CustomButton getFreePlayButton() {
        return freePlayButton;
    }

    /**
     * Return the "Random" button used to switch to the <code>RandomSelector</code>
     * @return The generation button
     */
    public CustomButton getRandomButton(){
        return randomButton;
    }
}
