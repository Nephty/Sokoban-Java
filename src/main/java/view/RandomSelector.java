package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Difficulty;
import model.Gen;

import java.util.ArrayList;

/**
 * <code>RandomSelector</code> is a user interface used to selected the difficulty of a generated level.
 * THe user can also decide a custom size of the level by choosing the width or the number of boxes.
 */
public class RandomSelector extends LevelSelector{

    private Difficulty selectedDifficulty;
    private TextField boxesField;
    private TextField sizeField;
    private  int gameSize;
    private int gameBoxes;

    /**
     * The main <code>RandomSelector</code> constructor
     * @param parent_ The <code>Parent</code> object that will contain the entire CampaignSelector
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public RandomSelector(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);
    }

    /**
     * Create the difficulty buttons and the <code>TextField</code> for the generation.
     */
    @Override
    public void setSelectors() {
        super.setSelectors();

        CustomButton easyButton = new CustomButton(600,100,WR, HR,"easy button.png");
        applyDifficulty(easyButton, Difficulty.EASY);

        CustomButton normalButton = new CustomButton(600,400,WR, HR,"normal button.png");
        applyDifficulty(normalButton, Difficulty.NORMAL);

        CustomButton hardButton = new CustomButton(600,700,WR, HR,"hard button.png");
        applyDifficulty(hardButton, Difficulty.HARD);

        middleMenu.getChildren().addAll(easyButton,easyButton.overlay, normalButton, normalButton.overlay,
                hardButton, hardButton.overlay);

        VBox customGameVBox = new VBox();
        Label sizeLabel = new Label("Size of the map");
        sizeLabel.setTextFill(Color.rgb(88, 38, 24));
        sizeLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        this.sizeField = new TextField("10");
        Label boxesLabel = new Label("number of boxes");
        boxesLabel.setTextFill(Color.rgb(88, 38, 24));
        boxesLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        this.boxesField = new TextField("2");

        customGameVBox.setLayoutX(50*WR);
        customGameVBox.setLayoutY(300*HR);
        CustomButton selectButton = new CustomButton(50,450,WR,HR,"confirm.png",(byte) 1);
        prepareSelectButtonAction(selectButton);
        customGameVBox.getChildren().addAll(sizeLabel,sizeField,boxesLabel,boxesField);
        rightMenu.getChildren().addAll(customGameVBox,selectButton, selectButton.overlay);


    }


    /**
     * Apply the EventHandler to select the difficulty
     * @param button The button on which we want to apply the EventHandle
     * @param difficulty The difficulty of the button.
     */
    private void applyDifficulty(CustomButton button, Difficulty difficulty){
        button.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            selectedDifficulty = difficulty;
            this.playButton.setVisible(true);
        });
    }

    /**
     * Prepare the EventHandler to apply the custom size of the generation
     * @param selectButton The button on which we want to add the EventHandler.
     */
    private void prepareSelectButtonAction(CustomButton selectButton){
        selectButton.setOnClick(e->{
            try{
                int size = Integer.parseInt(sizeField.getText());
                int nbrBoxes = Integer.parseInt(boxesField.getText());
                if (size > 6 && size <= 25*WR){
                    if (size < 26) {
                        int maxBoxes = 4*(size-6);
                        if (nbrBoxes <= maxBoxes){
                            selectedDifficulty = null;
                            gameSize = size;
                            gameBoxes = nbrBoxes;
                            this.playButton.setVisible(true);
                        } else{
                            AlertBox.display("Minor error", "Number of boxes must be lower than "+maxBoxes
                            + " with a width of " + size);
                        }
                    }else{
                       AlertBox.display("Minor error", "Size must be lower than 26");
                    }
                }else {
                    AlertBox.display("Minor error","Size must be between 7 and " + (int) Math.floor(25*WR));
                }
            } catch (NumberFormatException exc){
                AlertBox.display("Minor error", "Size or the number of boxes have to be integers !");
            }
        });
    }

    /**
     * Generate the new level with the given parameters
     * @return The ArrayList of String of the generated level.
     */
    @Override
    public ArrayList<String> getSelectedLevel(){
        Gen gene = new Gen();
        if (selectedDifficulty != null) {
            gene.launch(selectedDifficulty);
        }else {
            gene.launch(gameSize, gameBoxes);
        }
        return gene.getContent();
    }
}
