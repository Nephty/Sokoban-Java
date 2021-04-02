package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomImage extends Rectangle {

    private Image image;
    private Pane imgContainer;

    private int x_, y_;
    private double width_, height_;
    private String fileName_;

    public CustomImage(int x_, int y_, float WR, float HR, String fileName) throws FileNotFoundException {
        super(x_*WR, y_*HR);

        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\img\\" + fileName);
        this.image = new Image(inputStream);
        this.setFill(new ImagePattern(this.image));

        this.x_ = (int) (x_*WR);
        this.y_ = (int) (y_*HR);
        this.width_ = this.image.getWidth()*WR;
        this.height_ = this.image.getHeight()*HR;
        this.fileName_ = fileName;

        this.setWidth(this.width_);
        this.setHeight(this.height_);
        this.setX(this.x_);
        this.setY(this.y_);
    }

    public CustomImage(int x_, int y_, int width_, int height_, String fileName) throws FileNotFoundException {
        super(x_, y_, width_, height_);

        FileInputStream inputStream = new FileInputStream("src\\resources\\img\\" + fileName);
        this.image = new Image(inputStream);
        this.setFill(new ImagePattern(this.image));

        this.x_ = x_;
        this.y_ = y_;
        this.width_ = width_;
        this.height_ = height_;
        this.fileName_ = fileName;
    }
    /*
    public void setPosX(double finalX) {
        this.relocate(finalX, this.getLayoutBounds().getMinY());
    }

    public void setPosY(double finalY) {
        this.relocate(this.getLayoutBounds().getMinX(), finalY);
    }

    public void setPos(double finalX, double finalY) {
        this.relocate(finalX, finalY);
    }

    public void moveX(double distX) {
        // this.relocate(this.getLayoutBounds().getMinX() + distX, this.getLayoutBounds().getMinY());
        this.setLayoutX(distX);
    }

    public void moveY(double distY) {
        // this.relocate(this.getLayoutBounds().getMinX(), this.getLayoutBounds().getMinY() + distY);
        this.setLayoutY(distY);
    }
    */

    public int getX_() {
        return this.x_;
    }

    public int getY_() {
        return this.y_;
    }

    public double getWidth_() {
        return this.width_;
    }

    public double getHeight_() {
        return this.height_;
    }

    public String getFileName_() {
        return this.fileName_;
    }


    public Pane getPane() {
        return this.imgContainer;
    }

    public void setContainerWidth(double width) {
        this.imgContainer.setPrefWidth(width);
    }

    public void setContainerHeight(double height) {
        this.imgContainer.setPrefHeight(height);
    }

    /* BAD FUNCTION
    public void setDimension(int newWidth, int newHeight) {
        // Get initial position values
        double initialX = this.getX();
        double initialY = this.getY();

        // Get the ratio and rescale
        double xRatio = newWidth/this.getWidth();
        double yRatio = newHeight/this.getHeight();
        this.setScaleX(xRatio);
        this.setScaleY(yRatio);

        // Calculate the new visual position values
        double newX = this.getWidth() * xRatio;
        double newY = this.getHeight() * yRatio;

        // Move the image to its original position
        double diffX = Math.abs(initialX - newX);
        double diffY = Math.abs(initialY - newY);

        this.setLayoutX(-diffX);
        this.setLayoutY(-diffY);
    }
     */

    /*
    public void setDimension(int newWidth, int newHeight) {
        // Get initial position values
        double initialX = this.getLayoutX();
        double initialY = this.getLayoutY();

        // Get the ratio and rescale
        double targetRatioX = newWidth/this.getWidth();
        double targetRatioY = newHeight/this.getHeight();
        this.setScaleX(targetRatioX);
        this.setScaleY(targetRatioY);

        // Calculate the new visual position values
        double newX = newWidth * targetRatioX;
        double newY = newHeight * targetRatioY;

        // Move the image to its original position
        double diffX = Math.abs(initialX - newX);
        double diffY = Math.abs(initialY - newY);

        this.setLayoutX(-diffX);
        this.setLayoutY(-diffY);
    }
     */

    public boolean isMouseInside() {
        PointerInfo pointer = MouseInfo.getPointerInfo();
        int pointerX = pointer.getLocation().x;
        int pointerY = pointer.getLocation().y;

        if (this.getX() <= pointerX && pointerX <= this.getX() + this.getWidth()) {
            if (this.getY() <= pointerY && pointerY <= this.getY() + this.getHeight()) {
                return true;
            }
        }
        return false;
    }
}
