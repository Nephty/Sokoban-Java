package view;


import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Fichier;
import model.JSONReader;
import org.json.simple.parser.ParseException;



public class CampaignSelector extends LevelSelector{

    public CampaignSelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException{
        super(parent_, width_, height_, WR, HR);
    }


    /**
     * Create the button for the level selection.
     * Read the completed levels in the data.json file and create the correct amount of buttons.
     */
    @Override
    public void setSelectors(){
        super.setSelectors();
        try{
            JSONReader reader = new JSONReader("data.json");
            this.completedLevels = reader.getInt("completed levels");
            int files = Fichier.howManyLevel("main\\resources\\level\\campaign\\");
            int xScale=0;
            int yScale=0;
            for (int i = 0; i<files;i++) {
                CustomButton tmpButton = new CustomButton(100 + xScale * 150, 250 + yScale * 150, WR, HR, "level box.png");
                byte level = (byte) (i + 1);

                Text name = new Text(tmpButton.getX_(), tmpButton.getY_() - 20 * HR, "Level " + level);
                name.maxWidth(tmpButton.getWidth_());
                name.maxHeight(tmpButton.getHeight_());
                name.setFont(new Font("Microsoft YaHei", 25 * WR));
                name.setFill(Color.rgb(88, 38, 24));

                this.middleMenu.getChildren().addAll(tmpButton);
                if (i > completedLevels) {
                    CustomImage lock = new CustomImage(100 + xScale * 150, 250 + yScale * 150, WR, HR, "lock overlay.png");
                    lock.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                        try {
                            selectedLevelViewer.setVisible(false);
                            levelViewer.getChildren().removeAll(levelViewer.getChildren());
                            CustomImage lvlImage = new CustomImage(0, 0, WR, HR, "maps\\level" + level + ".png");
                            levelViewer.getChildren().add(lvlImage);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    });

                    lock.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        levelViewer.getChildren().removeAll(levelViewer.getChildren());
                        selectedLevelViewer.setVisible(true);
                    });

                    this.middleMenu.getChildren().add(lock);
                } else {
                    tmpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        this.selectedLevel = level;
                        this.playButton.setVisible(true);
                        this.resumeButton.setVisible(false);
                        try {
                            selectedLevelViewer.getChildren().removeAll(selectedLevelViewer.getChildren());
                            CustomImage lvlImage = new CustomImage(0, 0, WR, HR, "maps\\level" + level + ".png");
                            selectedLevelViewer.getChildren().add(lvlImage);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    });


                    Text nbr;
                    if (i < 9) {
                        nbr = new Text(tmpButton.getX_() + 35 * WR, tmpButton.getY_() + 65 * WR, Byte.toString(level));
                    } else {
                        nbr = new Text(tmpButton.getX_() + 25 * WR, tmpButton.getY_() + 65 * WR, Byte.toString(level));
                    }
                    nbr.maxWidth(tmpButton.getWidth_());
                    nbr.maxHeight(tmpButton.getHeight_());
                    nbr.setFont(new Font("Microsoft YaHei", 40 * WR));
                    nbr.setFill(Color.rgb(88, 38, 24));
                    this.middleMenu.getChildren().add(nbr);

                    tmpButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                        try {
                            selectedLevelViewer.setVisible(false);
                            levelViewer.getChildren().removeAll(levelViewer.getChildren());
                            CustomImage lvlImage = new CustomImage(0, 0, WR, HR, "maps\\level" + level + ".png");
                            levelViewer.getChildren().add(lvlImage);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    });

                    tmpButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        levelViewer.getChildren().removeAll(levelViewer.getChildren());
                        selectedLevelViewer.setVisible(true);
                    });
                }


                xScale++;
                if (xScale > 8) {
                    yScale++;
                    xScale = 0;
                }
            }
        } catch (IOException | ParseException exc){
            exc.printStackTrace();
        }
    }
}