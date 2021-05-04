package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.JSONReader;
import model.JSONWriter;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A <code>Menu</code> is a user interface used to build different types of menus. The <code>Menu</code> is
 * not a visually implementable class : we can not create a new <code>Menu</code> object and have it working as
 * others menus, it is more of a template rather than a functional menu. For example, creating a new <code>Menu</code>
 * and expecting it to work as a <code>MainMenu</code> or a <code>PlayingMenu</code> is not intended nor recommended.
 */
public abstract class Menu
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

    /**
     * Create a new <code>Menu</code> object and prepare its attributes
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR_ The width ratio that will be used to resize the components
     * @param HR_ The height ratio that will be used to resize the components
     */
    public Menu(Parent parent_, double width_, double height_, float WR_, float HR_) {
        super(parent_, width_, height_);
        this.WR = WR_;
        this.HR = HR_;
        this.width = (int) width_;
        this.height = (int) height;
        this.font = new Font("Microsoft YaHei", 35*WR);
        this.color = Color.rgb(88, 38, 24);
    }

    /**
     * Create a new <code>Menu</code> object and prepare its attributes
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR_ The width ratio that will be used to resize the components
     * @param HR_ The height ratio that will be used to resize the components
     * @param background_ The background of the menu
     */
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

    /**
     * Read the data.json file and get the resolution ID written in the file.
     * @return The resolution ID of the selected resolution
     */
    public static byte getResolutionID() {
        JSONReader JSONDataReader = new JSONReader("data.json");
        return JSONDataReader.getByte("resolution");
    }

    /**
     * Use the Toolkit abstract class to get the resolution of the screen as a <code>Dimension</code> object.
     * @return The resolution of the screen. The first attribute is the width and the second attribute is the height
     */
    public static Dimension getScreenDimension() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Compute the width ratio according to the width of the selected resolution and the reference width.
     * @param targetWidth The desired width
     * @return The ratio between the desired width and the reference width
     */
    public static float getWidthRatio(int targetWidth) {
        return (float) targetWidth/ORIGINAL_WIDTH;
    }

    /**
     * Compute the height ratio according to the height of the selected resolution and the reference height.
     * @param targetHeight The desired height
     * @return The ratio between the desired height and the reference height
     */
    public static float getHeightRatio(int targetHeight) {
        return (float) targetHeight/ORIGINAL_HEIGHT;
    }

    /**
     * Switch returning a <code>Dimension</code> object according to the byte of the selected resolution.
     * @return The <code>Dimension</code> object corresponding to the byte
     */
    public static Dimension resolutionIDToDimension() {
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
                JSONWriter jsonWriter = new JSONWriter("data.json");
                jsonWriter.set("resolution", "0");
                return getScreenDimension();
        }
    }
}