package view;

import model.JSONReader;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LevelBox extends CustomImage {

    private boolean isUnlocked = false;
    private byte level;

    private CustomImage lockImg;

    // WARNING : this.level cannot be 0, it starts at 1.

    public LevelBox(int x_, int y_, float WR, float HR, String fileName, byte level_, byte completedLevels)
            throws IOException, ParseException {
        super(x_, y_, WR, HR, fileName);
        this.lockImg = new CustomImage(x_, y_, WR, HR, "lock overlay.png");
        this.level = level_;

        this.updateLockStatus(completedLevels);

        if (this.isUnlocked) {
            this.lockImg.setVisible(false);
        }
    }

    public void updateLockStatus(byte completedLevels) {
        if (this.level >= completedLevels) {
            this.isUnlocked = true;
        }
    }
}
