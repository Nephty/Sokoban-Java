package model;

import javafx.scene.layout.GridPane;
import view.CustomImage;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Achievement {
    public CustomImage image;
    public CustomImage overlay;
    public CustomImage overlayDone;
    public CustomImage text;
    AtomicBoolean locked;
    boolean done = false;

    /**
     * Creates an Achievement objects that represent any kind of achievement. The condition to unlock the achievement
     * must not be specified in the Achievement object but must be written in an if statement that can unlock
     * the said Achievement.
     * @param image_ The image of the icon of the Achievement
     * @param text_ The image of the text box to display
     * @param posX The X position on the screen
     * @param posY The Y position on the screen
     * @param WR The width ratio used to resize the icon according to the resolution
     * @param HR The height ratio used to resize the icon according to the resolution
     * @param grid The grid that will contain the components
     */
    public Achievement(CustomImage image_, CustomImage text_, int posX, int posY, float WR, float HR, GridPane grid) {
        this.image = image_;
        this.overlay = new CustomImage(0, 0, WR, HR, "achievement overlay.png");
        this.overlayDone = new CustomImage(0, 0, WR, HR, "achievement done overlay.png");
        this.text = text_;
        this.overlay.setVisible(false);
        this.overlayDone.setVisible(false);
        this.text.setVisible(false);

        this.locked = new AtomicBoolean(false);

        this.image.setOnMouseEntered(e -> {
            if (!this.locked.get()) {
                this.overlay.setVisible(true);
                this.text.setVisible(true);
            }
        });
        this.overlay.setOnMouseExited(e -> {
            if (!this.locked.get()) {
                this.overlay.setVisible(false);
                this.text.setVisible(false);
            }
        });
        this.overlayDone.setOnMouseEntered(e -> {
            if (!this.locked.get()) {
                this.overlay.setVisible(true);
                this.text.setVisible(true);
            }
        });
        this.overlayDone.setOnMouseExited(e -> {
            if (!this.locked.get()) {
                this.overlay.setVisible(false);
                this.text.setVisible(false);
            }
        });
        this.overlay.setOnMouseClicked(e -> {
            this.locked.set(!this.locked.get());
        });
        this.overlayDone.setOnMouseClicked(e -> {
            this.locked.set(!this.locked.get());
        });

        GridPane.setConstraints(this.image, posX, posY);
        GridPane.setConstraints(this.overlay, posX, posY);
        GridPane.setConstraints(this.overlayDone, posX, posY);
        GridPane.setConstraints(this.text, posX+1, posY);
    }

    /**
     * Mark the Achievement as completed.
     */
    public void complete() {
        this.overlayDone.setVisible(true);
    }

}
