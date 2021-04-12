package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LevelSaver {
    final String dateTimeFormat = "dd-MM-yyyy @ HH-mm-ss";
    final String savesPath = "src\\main\\resources\\level\\saves\\";
    public LevelSaver() {
    }

    public void saveLevel(ArrayList<Direction> movesHistory, byte level, String userFileName) throws IOException {
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

        try (FileOutputStream FileOutputStream = new FileOutputStream(savesPath + fileName);
             ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream)) {
            ObjectOutputStream.writeObject(movesHistory);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveLevel(ArrayList<Direction> movesHistory, String fileName) throws IOException {
        File saveFile = new File(savesPath + fileName);

        try (FileOutputStream FileOutputStream = new FileOutputStream(savesPath + fileName);
             ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(FileOutputStream)) {
            ObjectOutputStream.writeObject(movesHistory);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<Direction> getHistory(String fileName) {
        try (FileInputStream FileInputStream = new FileInputStream(savesPath + fileName);
             ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputStream)) {
            return (ArrayList<Direction>) ObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }
}
