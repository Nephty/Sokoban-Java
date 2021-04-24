package view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A <code>CustomImage</code> is a <code>Rectangle</code> filled with an image found in the
 * <code>src\main\resources\img</code> directory. We can interact with it but no interface is implemented and
 * no inital method is available for interaction since this is not the purpose of the <code>CustomImage</code>.
 * Its main role is simply to display an image from the files.
 */
public class CustomImage
        extends Rectangle {

    private Image image;

    private int x_, y_;
    private double width_, height_;

    /**
     * Create a new <code>CustomImage</code> that can be interacted with. The buttons has an image that can be of any size to
     * make it fully customizable.
     * @param x_ The X position of the button in its container
     * @param y_ The Y position of the button in its container
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @param fileName The name of the image file that will be used to display the button
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    public CustomImage(int x_, int y_, float WR, float HR, String fileName)
            throws FileNotFoundException {
        super(x_*WR, y_*HR);

        try {
            FileInputStream inputStream = new FileInputStream("src\\main\\resources\\img\\" + fileName);
            this.image = new Image(inputStream);
            this.setFill(new ImagePattern(this.image));

            this.x_ = (int) (x_ * WR);
            this.y_ = (int) (y_ * HR);
            this.width_ = this.image.getWidth() * WR;
            this.height_ = this.image.getHeight() * HR;

            this.setWidth(this.width_);
            this.setHeight(this.height_);
            this.setX(this.x_);
            this.setY(this.y_);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Error : " + fileName + " isn't in src\\main\\resources\\img\\");
        }
    }
}
