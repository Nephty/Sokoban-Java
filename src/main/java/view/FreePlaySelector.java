package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Fichier;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The <code>FreePlaySelector</code> is a user interface used to display all the different levels, their
 * corresponding information such as the level number, difficulty, average rating, best time and a small image
 * to give the user a sneak peak into the level. The user can go back to the main menu, selected a level or
 * deselect one, start a new level or resume the on-going game. If there are too many levels to display on the
 * screen, multiple pages will be created and can be accessed to find the levels which could not have been displayed
 * on the first page. Locked levels are not playable until the user finished the previous level. Locked levels also
 * have a small lock icon on them to make it easier to tell them apart from unlocked levels.
 * The <code>FreePlaySelector</code> is used for the levels defined in the resources\level\freePlay directory.
 */
public class FreePlaySelector extends LevelSelector{

    private  String selectedLevel;

    /**
     * Create a new <code>FreePlaySelector</code> object used as a level selector where the user can select
     * and start a level or resume one.
     * @param parent_ The <code>Parent</code> object that will contain the entire CampaignSelector
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public FreePlaySelector(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);
    }


    /**
     * Create the  buttons for the level selection.
     * Read the .xsb files in the freePlay folder and displays it
     */
    @Override
    public void setSelectors() {
        super.setSelectors();
        String[] files = Fichier.levelList("main\\resources\\level\\freePlay\\");
        int xScale = 0;
        int yScale = 0;
        int nbrFiles = files.length;
        for (int i=0; i< (nbrFiles - (page*36)); i++){
            if (i > 35){
                this.nextPageButton.setVisible(true);
                break;
            }
            String level = files[i+(page*36)];
            if (!level.endsWith(".xsb")){
                //If the file isn't an .xsb file, we skip this file.
                continue;
            }


            String levelName;
            if (level.equals(".xsb")){
                levelName = level;
            } else{
                String[] tmp = level.split(".xsb");
                levelName = tmp[0];
            }
            CustomButton tmpButton = new CustomButton(100 + xScale * 150, 250 + yScale * 150, WR, HR, "level box.png");
            this.middleMenu.getChildren().addAll(tmpButton);

            selectLevel(tmpButton, level);

            Text nbr;
            if (i < 9) {
                nbr = new Text(tmpButton.getX() + 35 * WR, tmpButton.getY() + 65 * WR, Integer.toString(i+1));
            } else {
                nbr = new Text(tmpButton.getX() + 25 * WR, tmpButton.getY() + 65 * WR, Integer.toString(i+1));
            }
            nbr.maxWidth(tmpButton.getWidth());
            nbr.maxHeight(tmpButton.getHeight());
            nbr.setFont(new Font("Microsoft YaHei", 40 * WR));
            nbr.setFill(Color.rgb(88, 38, 24));
            selectLevel(nbr,level);
            this.middleMenu.getChildren().add(nbr);

            if (levelName.length() > 5){
                String tmpName ="";
                for (int j=0;j<=5;j++){
                    tmpName += levelName.charAt(j);
                }
                tmpName += "...";
                levelName = tmpName;
            }
            int length = levelName.length();
            if (length == 1) {
                length++;
            }
            Text name = new Text(tmpButton.getX() -8 + (tmpButton.getWidth() /length), tmpButton.getY() -10*WR, levelName);
            name.maxWidth(tmpButton.getWidth());
            name.maxHeight(tmpButton.getHeight());
            name.setFont(new Font("Microsoft YaHei", 25 * WR));
            name.setFill(Color.rgb(88, 38, 24));
            selectLevel(name,level);
            this.middleMenu.getChildren().add(name);

            xScale++;
            if (xScale > 8) {
                yScale++;
                xScale = 0;
                }
        }
    }


    /**
     * Add the event handler to select a level with the node
     * @param node The button/text/image we want to set
     * @param level The level the node will select
     */
    protected void selectLevel(Node node, String level) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.selectedLevel = level;
            this.playButton.setVisible(true);
            this.resumeButton.setVisible(false);
        });
    }

    /**
     * String level accessor
     * @return the String level selected
     */
    @Override
    public Object getSelectedLevel(){
        return selectedLevel;
    }

}