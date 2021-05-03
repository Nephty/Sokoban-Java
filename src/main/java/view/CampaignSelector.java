package view;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Fichier;
import model.JSONReader;
import org.json.simple.parser.ParseException;


/**
 * The <code>CampaignSelector</code> is a user interface used to display all the different levels, their
 * corresponding information such as the level number, difficulty, average rating, best time and a small image
 * to give the user a sneak peak into the level. The user can go back to the main menu, selected a level or
 * deselect one, start a new level or resume the on-going game. If there are too many levels to display on the
 * screen, multiple pages will be created and can be accessed to find the levels which could not have been displayed
 * on the first page. Locked levels are not playable until the user finished the previous level. Locked levels also
 * have a small lock icon on them to make it easier to tell them apart from unlocked levels.
 * The <code>CampaignSelector</code> is used for the levels defined in the resources\level\campaign directory.
 */
public class CampaignSelector extends LevelSelector{

    private byte selectedLevel;

    /**
     * Create a new <code>CampaignSelector</code> object used as a level selector where the user can select
     * and start a level or resume one.
     * @param parent_ The <code>Parent</code> object that will contain the entire CampaignSelector
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @throws IOException Exception thrown when a provided file name doesn't match any file
     * @throws ParseException Exception thrown when the .json file could not be parsed
     */
    public CampaignSelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException{
        super(parent_, width_, height_, WR, HR);
    }


    /**
     * Create the button for the level selection.
     * Read the completed levels in the data.json file and create the correct amount of buttons.
     */
    @Override
    public void setSelectors()
            throws IOException, ParseException {
        super.setSelectors();
        JSONReader reader = new JSONReader("data.json");
        this.completedLevels = reader.getInt("completed levels");
        int files = Fichier.howManyLevel("main\\resources\\level\\campaign\\")-1;
        int xScale=0;
        int yScale=0;
          
        for (int i = 0; (i<files - (page*36));i++) {
            if (i > 35){
                this.nextPageButton.setVisible(true);
                break;
            }
            byte level;
            level = (byte) (i + (page*36)+1);
            CustomButton tmpButton = new CustomButton(100 + xScale * 150, 250 + yScale * 150, WR, HR, "level box.png");
             this.middleMenu.getChildren().addAll(tmpButton);
             if (i > completedLevels) {
                 CustomImage lock = new CustomImage(100 + xScale * 150, 250 + yScale * 150, WR, HR, "lock overlay.png");
                 showLevel(lock, level);
                 this.middleMenu.getChildren().add(lock);
             } else {
                 selectLevel(tmpButton, level);
                 showLevel(tmpButton, level);

                 Text nbr;
                 if (i < 9) {
                     nbr = new Text(tmpButton.getX() + 35 * WR, tmpButton.getY() + 65 * WR, Byte.toString(level));
                 } else {
                     nbr = new Text(tmpButton.getX() + 25 * WR, tmpButton.getY() + 65 * WR, Byte.toString(level));
                 }
                 nbr.maxWidth(tmpButton.getWidth());
                 nbr.maxHeight(tmpButton.getHeight());
                 nbr.setFont(new Font("Microsoft YaHei", 40 * WR));
                 nbr.setFill(Color.rgb(88, 38, 24));

                 showLevel(nbr, level);
                 selectLevel(nbr,level);
                 this.middleMenu.getChildren().add(nbr);
             }
             xScale++;
             if (xScale > 8) {
                 yScale++;
                 xScale = 0;
             }
        }
    }


    /**
     * Add the MouseOver EventHandler to the node
     * @param node The button/text/image we want to set
     * @param level The number of the level we want the image
     */
    private void showLevel(Node node, byte level){
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            try {
                selectedLevelViewer.setVisible(false);
                levelViewer.getChildren().removeAll(levelViewer.getChildren());
                CustomImage lvlImage = new CustomImage(0, 0, WR, HR, "maps\\level" + level + ".png");
                levelViewer.setLayoutY((int) ((ORIGINAL_HEIGHT*HR/2) - (lvlImage.getHeight()/2)));
                levelViewer.getChildren().add(lvlImage);
            } catch (IOException exc) {
                AlertBox.display("Error", "Error : "+exc.getMessage());
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            levelViewer.getChildren().removeAll(levelViewer.getChildren());
            selectedLevelViewer.setVisible(true);
        });
    }

    /**
     * Add the event handler to select a level with the node
     * @param node The button/text/image we want to set
     * @param level The number of the level the node will select
     */
    private void selectLevel(Node node, byte level) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.selectedLevel = level;
            this.playButton.setVisible(true);
            this.resumeButton.setVisible(false);
            try {
                selectedLevelViewer.getChildren().removeAll(selectedLevelViewer.getChildren());
                CustomImage lvlImage = new CustomImage(0, 0, WR, HR, "maps\\level" + level + ".png");
                selectedLevelViewer.getChildren().add(lvlImage);
                selectedLevelViewer.setLayoutY((int) ((ORIGINAL_HEIGHT*HR/2) - (lvlImage.getHeight()/2)));
            } catch (IOException exc) {
                AlertBox.display("Error", "Error : "+exc.getMessage());
            }
        });
    }

    /**
     * Return the currently selected level.
     * @return The currently selected level
     */
    @Override
    public Object getSelectedLevel(){
        return this.selectedLevel;
    }
}