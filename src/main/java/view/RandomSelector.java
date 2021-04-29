package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import model.Difficulty;
import model.NewGenerator;
import org.checkerframework.checker.units.qual.C;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class RandomSelector extends LevelSelector{

    private Difficulty selectedDifficulty;
    public RandomSelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
    }

    @Override
    public void setSelectors() throws IOException, ParseException{
        super.setSelectors();

        CustomButton easyButton = new CustomButton(600,100,WR, HR,"easy button.png");
        applyDifficulty(easyButton, Difficulty.EASY);

        CustomButton normalButton = new CustomButton(600,400,WR, HR,"normal button.png");
        applyDifficulty(normalButton, Difficulty.NORMAL);

        CustomButton hardButton = new CustomButton(600,700,WR, HR,"hard button.png");
        applyDifficulty(hardButton, Difficulty.HARD);

        middleMenu.getChildren().addAll(easyButton,easyButton.overlay, normalButton, normalButton.overlay,
                hardButton, hardButton.overlay);
        this.playButton.setVisible(true);
    }



    private void applyDifficulty(CustomButton button, Difficulty difficulty){
        button.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            selectedDifficulty = difficulty;
        });
    }

    @Override
    public Object getSelectedLevel(){
        System.out.println(selectedDifficulty);
        NewGenerator.generate();
        NewGenerator.setContentBasedOnCurrentGeneration();
        return NewGenerator.getContent();
    }
}
