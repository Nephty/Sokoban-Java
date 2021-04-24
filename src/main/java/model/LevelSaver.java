package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LevelSaver {
    final static String dateTimeFormat = "dd-MM-yyyy @ HH-mm-ss";
    final static String savesPath = "src\\main\\resources\\level\\saves\\";


    /**
     * Use the serialization to save the <code>movesHistory</code> in a file.
     * If the player leaves the name empty, we create a name for the file based on the date when he saves it.
     * @param movesHistory The ArrayList with all the Direction the player made before he saved the game.
     * @param level The level where we're saving the moves.
     * @param userFileName The name the player gave for the save file
     * @throws IOException Exception thrown when a error occurred while saving the level.
     */
    public static void saveLevel(ArrayList<Direction> movesHistory, byte level, String userFileName) throws IOException {
        String fileName;
        if (userFileName.equals("")) {
            fileName = "level" + (level < 10 ? "0" + level : level);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
            String currentTime = LocalDateTime.now().format(formatter);
            fileName += " " + currentTime;
            fileName += ".mov";
        } else {
            if (!userFileName.endsWith(".mov")) {
                fileName = userFileName + ".mov";
            } else {
                fileName = userFileName;
            }
        }

        FileOutputStream FileOutputStream = new FileOutputStream(savesPath + fileName);
        ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream);
        ObjectOutputStream.writeObject(movesHistory);

    }

    public static void saveLevel(ArrayList<Direction> movesHistory, String fileName) throws IOException {
        File saveFile = new File(savesPath + fileName);

        FileOutputStream FileOutputStream = new FileOutputStream(savesPath + fileName);
        ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream);
        ObjectOutputStream.writeObject(movesHistory);
    }

    /**
     * Read the contend of the a .mov file saved with the serialization
     * @param fileName The name of the file
     * @param origin The directory where we want to read the file.
     *               Only used if the we want to used it for the test, otherwise it reads in the saves folder
     * @throws IOException Exception thrown when there's an error while reading the file
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     * @return An arrayList of Direction read in the file
     */
    public static ArrayList<Direction> getHistory(String fileName, String origin) throws IOException, ClassNotFoundException{
        String savePath;
        if (origin.equals("test")){
            savePath = "src\\test\\resources\\";
        }else{
            savePath = savesPath;
        }
        try (FileInputStream FileInputStream = new FileInputStream(savePath + fileName);
             ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputStream)) {
            return (ArrayList<Direction>) ObjectInputStream.readObject();
        } catch (ClassNotFoundException e1){
            throw new ClassNotFoundException("A serialized object cannot be found");
        }
        catch (IOException exception) {
            throw new IOException("The object in the .mov file isn't in the good format");
        }
    }
}