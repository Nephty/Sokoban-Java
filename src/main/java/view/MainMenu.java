package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenu
        extends Menu {

    //---------//
    // Objects //
    //---------//

    private CustomButton playButton, optionsButton, quitButton, achievementsButton, campaignButton, tutorialButton,
                        freePlayButton;
    private CustomImage background;
    private static Stage window;

    //------//
    // Data //
    //------//

    static AtomicBoolean playInterface = new AtomicBoolean(false);


    public MainMenu(Parent parent_, double width_, double height_, float WR, float HR, Stage window_)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
        window = window_;
        setButtons();
        prepareButtonsActions();
    }

    public MainMenu(Parent parent_, double width_, double height_, float WR_, float HR_, Stage window_, CustomImage background_)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR_, HR_, background_);
        window = window_;
        this.background = background_;
        setButtons();
        prepareButtonsActions();
    }


    private void setButtons()
            throws IOException, ParseException {
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
        this.optionsButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25)),
                WR, HR, "options button.png");
        this.quitButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25+96+25+96+25)),
                WR, HR, "quit button.png");
        this.achievementsButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2))),
                (int) (((ORIGINAL_HEIGHT/2)+25+96+25)),
                WR, HR, "achievements button.png");
        this.campaignButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)-96-(96/2)-5)),
                WR, HR, "campaign button.png");
        this.tutorialButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)-96+5+(96/2))),
                WR, HR, "tutorial button.png");
        this.freePlayButton = new CustomButton(
                (int) (((ORIGINAL_WIDTH/2)-(480/2)+480+15)),
                (int) (((ORIGINAL_HEIGHT/2)+15+(96/2))),
                WR, HR, "achievements button.png");

        this.campaignButton.setVisible(false);
        this.tutorialButton.setVisible(false);
        this.freePlayButton.setVisible(false);
    }

    private void prepareButtonsActions() {
        this.playButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!playInterface.get()) {
                this.campaignButton.setVisible(true);
                this.tutorialButton.setVisible(true);
                this.freePlayButton.setVisible(true);
                playInterface.set(true);
            }
            else {
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
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
            }
        });

        this.tutorialButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.tutorialButton.overlay.setVisible(false);
            }
        });

        this.freePlayButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                window.setFullScreen(fullscreen);
                playInterface.set(false);
                this.campaignButton.setVisible(false);
                this.tutorialButton.setVisible(false);
                this.freePlayButton.setVisible(false);
                this.freePlayButton.overlay.setVisible(false);
            }
        });
    }

    public static boolean getPlayInterface() {
        return playInterface.get();
    }

    public static void setPlayInterface(boolean value) {
        playInterface.set(value);
    }

    public CustomButton getPlayButton() {
        return playButton;
    }

    public CustomButton getOptionsButton() {
        return optionsButton;
    }

    public CustomButton getQuitButton() {
        return quitButton;
    }

    public CustomButton getAchievementsButton() {
        return achievementsButton;
    }

    public CustomButton getCampaignButton() {
        return campaignButton;
    }

    public CustomButton getTutorialButton() {
        return tutorialButton;
    }

    public CustomButton getFreePlayButton() {
        return freePlayButton;
    }
}
