package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import com.sun.javafx.scene.control.ControlHelper;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static view.MainMenu.playInterface;
import static view.Menu.fullscreen;

public class CustomButton extends CustomImage {

    public CustomImage overlay;
    private EventHandler<? super MouseEvent> function;

    /**
     * Creates a new CustomButton that can be interacted with. The buttons has an image that can be of any size to
     * make it fully customizable.
     * @param x_ The X position of the button in its container
     * @param y_ The Y position of the button in its container
     * @param WR The width ratio used to resize the button according to the resolution
     * @param HR The height ratio used to resize the button according to the resolution
     * @param fileName The name of the image file that will be used to display the button
     * @throws FileNotFoundException Occurs if the given file is not found
     */
    public CustomButton(int x_, int y_, float WR, float HR, String fileName) throws FileNotFoundException {
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

    public CustomButton(int x_, int y_, float WR, float HR, String fileName, byte small) throws FileNotFoundException {
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

    public CustomButton(int x_, int y_, int width_, int height_, String fileName) throws FileNotFoundException {
        super(x_, y_, width_, height_, fileName);

        this.overlay = new CustomImage(x_, y_, width_, height_, "button overlay.png");
        this.overlay.setVisible(false);

        this.setOnMouseEntered(e -> {
            this.overlay.setVisible(true);
        });
        this.overlay.setOnMouseExited(e -> {
            this.overlay.setVisible(false);
        });
    }

    public EventHandler<? super MouseEvent> getFunction() {
        return this.function;
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        this.overlay.setOnMouseClicked(eventHandler);
    }

    public void setOnEntering(EventHandler<? super MouseEvent> eventHandler) {
        this.setOnMouseEntered(eventHandler);
    }
}
