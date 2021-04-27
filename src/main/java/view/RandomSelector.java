package view;

import javafx.scene.Parent;
import model.NewGenerator;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class RandomSelector extends LevelSelector{

    private ArrayList<String> selectedLevel;
    public RandomSelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
    }

    @Override
    public Object getSelectedLevel(){
        NewGenerator.generate();
        NewGenerator.setContentBasedOnCurrentGeneration();
        selectedLevel = NewGenerator.getContent();
        return selectedLevel;
    }
}
