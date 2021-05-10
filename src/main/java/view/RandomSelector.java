package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import model.Difficulty;
import model.Gen;

public class RandomSelector extends LevelSelector{

    private Difficulty selectedDifficulty;
    public RandomSelector(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);
    }

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
    }



    private void applyDifficulty(CustomButton button, Difficulty difficulty){
        button.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            selectedDifficulty = difficulty;
            this.playButton.setVisible(true);
        });
    }

    @Override
    public Object getSelectedLevel(){
        /*NewGenerator.generate();
        NewGenerator.setContentBasedOnCurrentGeneration();
        return NewGenerator.getContent();*/
        Gen gene = new Gen();
        gene.launch(selectedDifficulty);
        return gene.getContent();
    }
}
