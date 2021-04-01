package main.java.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class AchievementsMenu extends Menu {
    public AchievementsMenu(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);

    }

    public AchievementsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_) {
        super(parent_, width_, height_, WR, HR);
        this.background = background_;
    }
}
