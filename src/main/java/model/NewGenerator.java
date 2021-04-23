package model;

import java.io.*;
import java.util.ArrayList;

/**
 * The <code>Generator</code> class is a class using static methods that will define a random position on a squared map
 * for a given amount of boxes & objectives. Boxes can start on objectives. For further details : after testing
 * 1.000.000.000 random generations on a 10x10 map with 3 boxes and 3 objectives, there wasn't a single generation
 * that happened to have all three boxes on an objective, so we can consider that an already finished map will never
 * be generated.
 */
public class NewGenerator {
    private static ArrayList<String> content = new ArrayList<>();
    public static ArrayList<Boolean> canBeBox = new ArrayList<>();
    public static ArrayList<Boolean> canBeGoal = new ArrayList<>();
    public static boolean[][] mappedCanBeBox = new boolean[10][10];
    public static boolean[][] mappedCanBeGoal = new boolean[10][10];
    private final static String savesPath = "src\\main\\resources\\level\\generator\\";
    private static int randomX;
    private static int randomY;
    private static ArrayList<Position> boxes = new ArrayList<>();
    private static ArrayList<Position> goals = new ArrayList<>();


    public static void prepare() throws IOException {
        canBeBox = readGEN("easy boxes.gen");
        canBeGoal = readGEN("easy objectives.gen");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                mappedCanBeBox[y][x] = canBeBox.get(y*10+x);
                mappedCanBeGoal[y][x] = canBeGoal.get(y*10+x);
            }
        }
    }

    private static ArrayList<Boolean> readGEN(String fileName) {
        try (FileInputStream FileInputStream = new FileInputStream(savesPath + fileName);
             ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputStream)) {
            return (ArrayList<Boolean>) ObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static int randomIntGenerator(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static void generate() {
        for (int i = 0; i < 3; i++) {
            do {
                randomX = randomIntGenerator(0, 9);
                randomY = randomIntGenerator(0, 9);
            } while (!mappedCanBeBox[randomY][randomX]);
            boxes.add(new Position(randomX, randomY));

            do {
                randomX = randomIntGenerator(0, 9);
                randomY = randomIntGenerator(0, 9);
            } while (!mappedCanBeGoal[randomY][randomX]);
            goals.add(new Position(randomX, randomY));
        }
    }

    public static void setContentBasedOnCurrentGeneration() {
        for (int y = 0; y < 10; y++) {
            String line = "";
            for (int x = 0; x < 10; x++) {
                if (!canBeGoal.get(y * 10 + x)) {
                    line += "#";
                } else {
                    for (int i = 0; i < 3; i++) {
                        if (goals.get(i).getX() == x && goals.get(i).getY() == y) {
                            line += ".";
                        }
                        else if (boxes.get(i).getX() == x && boxes.get(i).getY() == y) {
                            line += "$";
                        } else {
                            line += " ";
                        }
                    }
                }
                content.add(line);
            }
        }
    }

    public static ArrayList<String> getContent() {
        return content;
    }
}
