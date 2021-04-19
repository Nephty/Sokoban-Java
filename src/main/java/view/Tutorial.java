package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The <code>Tutorial</code> is a user interface that displays a slideshow of images explaining how to play the game.
 * Basics of the Sokoban game are explained in this <code>Menu</code> and the user can navigate through the
 * different images, going back and forth. The user can come back later and continue the Tutorial and do it
 * multiple times.
 */
public class Tutorial extends Menu {
    private CustomImage tutorial0,
            tutorial1, tutorial2, tutorial3,
            tutorial4, tutorial5, tutorial6,
            leftArrow, rightArrow;
    private ArrayList<CustomImage> images = new ArrayList<>();
    private byte currentImage = 0;

    /**
     * Create a new <code>Tutorial</code> and prepare the slideshow and the buttons used to navigate.
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR_ The width ratio that will be used to resize the components
     * @param HR_ The height ratio that will be used to resize the components
     */
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

    /**
     * Create all the necessary images and only set the first image visible.
     * @throws FileNotFoundException Exception thrown when a specified file could not be found
     */
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
    }

    /**
     * Create the images for the buttons used to navigate through the slideshow and set their respective
     * <code>EventHandler</code>.
     * @throws FileNotFoundException Exception thrown when a specified file could not be found
     */
    public void prepareArrowButtons() throws FileNotFoundException {
        this.leftArrow = new CustomImage(100, 880, this.WR, this.HR, "left arrow.png");
        this.rightArrow = new CustomImage(1720, 880, this.WR, this.HR, "right arrow.png");
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

    /**
     * Return the first image of the slideshow.
     * @return The first image of the slideshow
     */
    public CustomImage getTutorial0() {
        return tutorial0;
    }

    /**
     * Return the second image of the slideshow.
     * @return The second image of the slideshow
     */
    public CustomImage getTutorial1() {
        return tutorial1;
    }

    /**
     * Return the third image of the slideshow.
     * @return The third image of the slideshow
     */
    public CustomImage getTutorial2() {
        return tutorial2;
    }

    /**
     * Return the fourth image of the slideshow.
     * @return The fourth image of the slideshow
     */
    public CustomImage getTutorial3() {
        return tutorial3;
    }

    /**
     * Return the fifth image of the slideshow.
     * @return The fifth image of the slideshow
     */
    public CustomImage getTutorial4() {
        return tutorial4;
    }

    /**
     * Return the sixth image of the slideshow.
     * @return The sixth image of the slideshow
     */
    public CustomImage getTutorial5() {
        return tutorial5;
    }

    /**
     * Return the last image of the slideshow.
     * @return The last image of the slideshow
     */
    public CustomImage getTutorial6() {
        return tutorial6;
    }

    /**
     * Return the right arrow image used to go one image forward.
     * @return The right arrow image
     */
    public CustomImage getLeftArrow() {
        return leftArrow;
    }

    /**
     * Return the left arrow image used to go one image backwards.
     * @return The left arrow image
     */
    public CustomImage getRightArrow() {
        return rightArrow;
    }

    /**
     * Return the currently displayed image of the slideshow
     * @return The currently displayed image of the slideshow
     */
    public byte getCurrentImage() {
        return currentImage;
    }
}
