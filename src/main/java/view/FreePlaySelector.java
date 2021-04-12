package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Fichier;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class FreePlaySelector extends LevelSelector{

    private  String selectedLevel;

    public FreePlaySelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
    }


    /**
     * Create the button for the level selection.
     * Read the completed levels in the data.json file and create the correct amount of buttons.
     */
    @Override
    public void setSelectors() {
        super.setSelectors();
        try {
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
                String[] tmp = level.split(".xsb");
                String levelName = tmp[0];
                CustomButton tmpButton = new CustomButton(100 + xScale * 150, 250 + yScale * 150, WR, HR, "level_box.png");
                this.middleMenu.getChildren().addAll(tmpButton);

                selectLevel(tmpButton, level);

                Text nbr;
                if (i < 9) {
                    nbr = new Text(tmpButton.getX_() + 35 * WR, tmpButton.getY_() + 65 * WR, Integer.toString(i+1));
                } else {
                    nbr = new Text(tmpButton.getX_() + 25 * WR, tmpButton.getY_() + 65 * WR, Integer.toString(i+1));
                }
                nbr.maxWidth(tmpButton.getWidth_());
                nbr.maxHeight(tmpButton.getHeight_());
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
                Text name = new Text(tmpButton.getX_() -8 + (tmpButton.getWidth_() /length), tmpButton.getY_() -10*WR, levelName);
                name.maxWidth(tmpButton.getWidth_());
                name.maxHeight(tmpButton.getHeight_());
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
        } catch (IOException exc){
            exc.printStackTrace();
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
    public String getStringLevel(){
        super.getStringLevel();
        return selectedLevel;
    }

}
