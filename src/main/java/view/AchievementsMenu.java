package view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class AchievementsMenu extends Menu {
    /**
     * Creates a new AchievementsMenu objects that will be used as a scene to display the content.
     * Inherits from Menu.
     * @param parent_ The main pane that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public AchievementsMenu(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);

    }

    /**
     * Creates a new AchievementsMenu objects that will be used as a scene to display the content.
     * Inherits from Menu.
     * @param parent_ The main pane that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @param background_ The image that will be displayed as the background of the menu
     */
    public AchievementsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_) {
        super(parent_, width_, height_, WR, HR);
        this.background = background_;
    }
}
