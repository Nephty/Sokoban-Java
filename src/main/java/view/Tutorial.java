package main.java.view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tutorial extends Menu {
    private CustomImage tutorial0,
            tutorial1, tutorial2, tutorial3,
            tutorial4, tutorial5, tutorial6,
            leftArrow, rightArrow;
    private ArrayList<CustomImage> images = new ArrayList<>();
    private byte currentImage = 0;

    public Tutorial(Parent parent_, double width_, double height_, float WR_, float HR_)
            throws FileNotFoundException {
        super(parent_, width_, height_, WR_, HR_);
        this.prepareImages();
        this.images.add(this.tutorial0);
        this.images.add(this.tutorial1);
        this.images.add(this.tutorial2);
        this.images.add(this.tutorial3);
        this.images.add(this.tutorial4);
        this.images.add(this.tutorial5);
        this.images.add(this.tutorial6);
        this.prepareArrowButtons();
    }

    public void prepareImages()
            throws FileNotFoundException {
        this.tutorial0 = new CustomImage(0, 0, this.WR, this.HR, "tutorial0.png");
        this.tutorial1 = new CustomImage(0, 0, this.WR, this.HR, "tutorial1.png");
        this.tutorial2 = new CustomImage(0, 0, this.WR, this.HR, "tutorial2.png");
        this.tutorial3 = new CustomImage(0, 0, this.WR, this.HR, "tutorial3.png");
        this.tutorial4 = new CustomImage(0, 0, this.WR, this.HR, "tutorial4.png");
        this.tutorial5 = new CustomImage(0, 0, this.WR, this.HR, "tutorial5.png");
        this.tutorial6 = new CustomImage(0, 0, this.WR, this.HR, "tutorial6.png");
        this.tutorial1.setVisible(false);
        this.tutorial2.setVisible(false);
        this.tutorial3.setVisible(false);
        this.tutorial4.setVisible(false);
        this.tutorial5.setVisible(false);
        this.tutorial6.setVisible(false);
        this.leftArrow = new CustomImage(100, 880, this.WR, this.HR, "temp arrow left.png");
        this.rightArrow = new CustomImage(1720, 880, this.WR, this.HR, "temp arrow right.png");
    }

    public void prepareArrowButtons() {
        this.leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.images.get(this.currentImage).setVisible(false);
                if (this.currentImage >= 1) {
                    this.currentImage--;
                }
                this.images.get(this.currentImage).setVisible(true);
            }
        });

        this.rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.images.get(this.currentImage).setVisible(false);
                if (this.currentImage <= 5) {
                    this.currentImage++;
                } else {  // If current image >= 6
                    this.currentImage = 0;
                }
                this.images.get(this.currentImage).setVisible(true);
            }
        });
    }

    public CustomImage getTutorial0() {
        return tutorial0;
    }

    public CustomImage getTutorial1() {
        return tutorial1;
    }

    public CustomImage getTutorial2() {
        return tutorial2;
    }

    public CustomImage getTutorial3() {
        return tutorial3;
    }

    public CustomImage getTutorial4() {
        return tutorial4;
    }

    public CustomImage getTutorial5() {
        return tutorial5;
    }

    public CustomImage getTutorial6() {
        return tutorial6;
    }

    public CustomImage getLeftArrow() {
        return leftArrow;
    }

    public CustomImage getRightArrow() {
        return rightArrow;
    }

    public byte getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(byte value) {
        this.images.get(this.currentImage-1).setVisible(false);
        this.currentImage = value;
        this.images.get(0).setVisible(true);
    }
}
