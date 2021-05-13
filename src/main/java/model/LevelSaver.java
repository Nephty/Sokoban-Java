package model;

import view.AlertBox;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A <code>LevelSave</code> is a class with two static methods : one to save current progression and one
 * to load a previously save progression. It links the user and the save files.
 */
public class LevelSaver {
    final static String dateTimeFormat = "dd-MM-yyyy @ HH-mm-ss";
    final static String savesPath = FileGetter.directory("saves");

    /**
     * Use the serialization to save the <code>movesHistory</code> in a file.
     * If the player leaves the name empty, we create a name for the file based on the date when he saves it.
     * @param movesHistory The ArrayList with all the Direction the player made before he saved the game.
     * @param level The level where we're saving the moves.
     * @param userFileName The name the player gave for the save file
     * @param currentLevelText Text naming the current level ("1", "2", "3",... for campaign and e.g. "MyLevel" for the freePlay)
     */
    public static void saveLevel(ArrayList<Direction> movesHistory, byte level, String userFileName, String currentLevelText) {
        boolean canSave = true;
        String fileName = "";
        try {
            Integer.parseInt(currentLevelText);
            if (userFileName.equals("")) {
                fileName = "level" + (level < 10 ? "0" + level : level);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
                String currentTime = LocalDateTime.now().format(formatter);
                fileName += " " + currentTime;
                fileName += ".mov";
            } else if (isValid(userFileName)) {
                if (!userFileName.endsWith(".mov")) {
                    fileName = userFileName + ".mov";
                } else {
                    fileName = userFileName;
                }
            } else {
                AlertBox.display("Minor Error", "Your file name contains forbidden characters.");
                canSave = false;
            }
        } catch (NumberFormatException exc) {
            if (userFileName.equals("")) {
                fileName = currentLevelText;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
                String currentTime = LocalDateTime.now().format(formatter);
                fileName += " " + currentTime;
                fileName += ".mov";
            } else if (isValid(userFileName)) {
                if (!userFileName.endsWith(".mov")) {
                    fileName = userFileName + ".mov";
                } else {
                    fileName = userFileName;
                }
            } else {
                AlertBox.display("Minor Error", "Your file name contains forbidden characters.");
                canSave = false;
            }
        }

        if (canSave) {
            try {
                FileOutputStream FileOutputStream = new FileOutputStream(savesPath + fileName);
                ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream);
                ObjectOutputStream.writeObject(movesHistory);
            } catch (IOException e) {
                AlertBox.display("Minor Error", "Could not save the file. Check the name and try again.");
            }
        }
    }

    /**
     * Read the contend of the a .mov file saved with the serialization
     * @param fileName The name of the file
     * @param origin The directory where we want to read the file.
     *               Only used if the we want to used it for the test, otherwise it reads in the saves folder
     * @return An arrayList of Direction read in the file
     */
    public static ArrayList<Direction> getHistory(String fileName, String origin) {
        String savePath;
        if (origin.equals("test")){
            savePath = FileGetter.directory("test");
        }else{
            savePath = savesPath;
        }
        try (InputStream InputStream = new BufferedInputStream(new FileInputStream(savePath+fileName));
             ObjectInputStream ObjectInputStream = new ObjectInputStream(InputStream)) {

            return (ArrayList<Direction>) ObjectInputStream.readObject();
        } catch (ClassNotFoundException e1){
            AlertBox.display("Minor error", "A serialized object could not be found in the file. " +
                    "Check if the file extension is correct.");
            return null;

        } catch (IOException exception) {
            AlertBox.display("Minor error", "Could not find the given file. Please try again.");
            return null;
        }
    }

    /**
     * Check if the given <code>String</code> is a valid file name.
     * @param s The <code>String</code> to validate
     * @return true if the <code>String</code> is a valid file name, else otherwise
     */
    private static boolean isValid(String s) {
        return !s.contains("/") && !s.contains("\\") && !s.contains(":") && !s.contains("*") && !s.contains("?") && !s.contains("\"") && !s.contains("<") && !s.contains(">") && !s.contains("|");
    }
}