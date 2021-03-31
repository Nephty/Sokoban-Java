package main.java.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import com.sun.javafx.scene.control.ControlHelper;

import java.io.FileNotFoundException;

public class CustomButton extends CustomImage {

    public CustomImage overlay;

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

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        this.overlay.setOnMouseClicked(eventHandler);
    }

    public void setOnEntering(EventHandler<? super MouseEvent> eventHandler) {
        this.setOnMouseEntered(eventHandler);
    }
}
