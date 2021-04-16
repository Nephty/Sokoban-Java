package view;

import javafx.scene.Parent;

/**
 * The <code>AchievementsMenu</code> is a user interface used to display <code>Achievement</code> objects and
 * their respective information. Achievements are unlockable and can be clicked onto to display the said information.
 * Each Achievement has its name written in the <code>src\main\java\resources\data.json</code> file and the
 * corresponding value is a boolean with the <code>true</code> value if the Achievement is unlocked and
 * the <code>false</code> value otherwise.
 */
public class AchievementsMenu
        extends Menu {

    /**
     * Create a new <code>AchievementsMenu</code> objects that will be used as a scene to display the content.
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
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
     * Create a new <code>AchievementsMenu</code> objects that will be used as a scene to display the content.
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
