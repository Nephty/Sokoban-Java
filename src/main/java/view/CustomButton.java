package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

/**
 * A <code>CustomButton</code> is a <code>CustomImage</code> filled with an image found in the
 *  <code>src\main\resources\img</code> directory. We can interact with it but no interface is implemented and
 *  no inital method is available for interaction. The difference with a <code>CustomImage</code> is that the
 *  <code>CustomButton</code> gets an overlay according to its size and the overlay is set up when creating a new
 *  <code>CustomButton</code>. Warning : if we want to add a method to click on the button, we must add a new
 *  <code>EventHandler</code> on the overlay because it will be displayed on top of the <code>CustomButton</code>.
 */
public class CustomButton extends CustomImage {

    public CustomImage overlay;

    /**
     * Create a new <code>CustomButton</code> that can be interacted with. The buttons has an image that can be of any size to
     * make it fully customizable.
     * @param x_ The X position of the button in its container
     * @param y_ The Y position of the button in its container
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @param fileName The name of the image file that will be used to display the button
     */
    public CustomButton(int x_, int y_, float WR, float HR, String fileName) {
        super(x_, y_, WR, HR, fileName);

        this.overlay = new CustomImage(x_, y_, WR, HR, "button overlay.png");
        this.overlay.setVisible(false);

        this.setOnMouseEntered(e -> {
            this.overlay.setVisible(true);
        });
        this.overlay.setOnMouseExited(e -> {
            this.overlay.setVisible(false);
        });
    }

    /**
     * Create a new CustomButton that can be interacted with. The buttons has an image that can be of any size to
     * make it fully customizable.
     * Inherits from <code>CustomImage</code>.
     * @param x_ The X position of the button in its container
     * @param y_ The Y position of the button in its container
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @param fileName The name of the image file that will be used to display the button
     * @param small A byte that can be 0 or 1. If it's 0, the constructor creates a small button.
     *              If it's 1, the constructor creates a slightly larger button, but not a regular one.
     */
    public CustomButton(int x_, int y_, float WR, float HR, String fileName, byte small) {
        super(x_, y_, WR, HR, fileName);

        String localFileName = "button overlay.png";

        if (small == 0) {
            localFileName = "small overlay.png";
        } else if (small == 1) {
            localFileName = "small larger overlay.png";
        }

        this.overlay = new CustomImage(x_, y_, WR, HR, localFileName);
        this.overlay.setVisible(false);

        this.setOnMouseEntered(e -> {
            this.overlay.setVisible(true);
        });
        this.overlay.setOnMouseExited(e -> {
            this.overlay.setVisible(false);
        });
    }

    /**
     * Set the <code>EventHandler</code> when the mouse clicks on the overlay of the button.
     * @param eventHandler The <code>EventHandler</code> that will be used upon clicking
     */
    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        this.overlay.setOnMouseClicked(eventHandler);
    }
}
