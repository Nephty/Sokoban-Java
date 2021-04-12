package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.JSONReader;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Menu
        extends Scene {

    //---------//
    // Objects //
    //---------//

    protected CustomImage background;
    protected ArrayList<CustomImage> images = new ArrayList<>();
    protected ArrayList<CustomButton> buttons = new ArrayList<>();

    //------//
    // Data //
    //------//

    protected int width;
    protected int height;
    protected float WR;
    protected float HR;
    protected static final int ORIGINAL_WIDTH = 1920;
    protected static final int ORIGINAL_HEIGHT = 1080;
    protected static boolean fullscreen = false;
    protected Font font;
    protected Color color;


    public Menu(Parent parent_, double width_, double height_, float WR_, float HR_) {
        super(parent_, width_, height_);
        this.WR = WR_;
        this.HR = HR_;
        this.width = (int) width_;
        this.height = (int) height;
        this.font = new Font("Microsoft YaHei", 35*WR);
        this.color = Color.rgb(88, 38, 24);
    }

    public Menu(Parent parent_, double width_, double height_, float WR_, float HR_, CustomImage background_) {
        super(parent_, width_, height_);
        this.background = background_;
        this.WR = WR_;
        this.HR = HR_;
        this.width = (int) width_;
        this.height = (int) height;
        final Font font = new Font("Microsoft YaHei", 35*WR);
        final Color color = Color.rgb(88, 38, 24);
    }
    
    //-------------------//
    // GETTERS & SETTERS //
    //-------------------//

    public CustomImage getBackground() {
        return background;
    }

    public void setBackground(CustomImage background) {
        this.background = background;
    }

    public ArrayList<CustomImage> getImages() {
        return this.images;
    }

    public void setImages(ArrayList<CustomImage> images) {
        this.images = images;
    }

    public ArrayList<CustomButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<CustomButton> buttons) {
        this.buttons = buttons;
    }




    public static byte getResolutionID() throws IOException, ParseException {
        JSONReader JSONDataReader = new JSONReader("data.json");
        byte RESOLUTION_ID = JSONDataReader.getByte("resolution");
        return RESOLUTION_ID;
    }

    public static Dimension getScreenDimension() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static float getWidthRatio(int targetWidth) {
        return (float) targetWidth/ORIGINAL_WIDTH;
    }

    public static float getHeightRatio(int targetHeight) {
        return (float) targetHeight/ORIGINAL_HEIGHT;
    }

    public static Dimension resolutionIDToDimension() throws IOException, ParseException {
        byte resolutionID = getResolutionID();
        switch (resolutionID) {
            case 0:
                return getScreenDimension();
            case 1:
                return new Dimension(1280, 720);
            case 2:
                return new Dimension(1600, 900);
            case 3:
                return new Dimension(1920, 1080);
            case 4:
                return new Dimension(2560, 1440);
            case 5:
                return new Dimension(3840, 2160);
            case 6:
                return new Dimension(640, 360);
            default:
                throw new IllegalStateException("Unexpected value: " + resolutionID);
        }
    }
}