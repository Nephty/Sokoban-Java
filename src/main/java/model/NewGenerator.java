package model;

import view.AlertBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * The <code>Generator</code> class is a class using static methods that will define a random position on a squared map
 * for a given amount of boxes and objectives. Boxes can start on objectives. For further details : after testing
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
    private static ArrayList<Position> boxes = new ArrayList<>();
    private static ArrayList<Position> goals = new ArrayList<>();
    private static ArrayList<Position> boxesOnObj = new ArrayList<>();
    private static Player player;
    private static int mapLength = 10;
    private static int mapHeight = 10;
    private static int boxesCount = 3;


    public static void prepare() {
        canBeBox = readGEN("easy boxes.gen");
        canBeGoal = readGEN("easy objectives.gen");
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapLength; x++) {
                mappedCanBeBox[y][x] = canBeBox.get(y*mapHeight+x);
                mappedCanBeGoal[y][x] = canBeGoal.get(y*mapHeight+x);
            }
        }
    }

    private static ArrayList<Boolean> readGEN(String fileName) {
        try (FileInputStream FileInputStream = new FileInputStream(savesPath + fileName);
             ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputStream)) {
            return (ArrayList<Boolean>) ObjectInputStream.readObject();
        } catch (ClassNotFoundException exception) {
            AlertBox.display("Minor error", "A serialized object could not be found in the file. " +
                    "Check if the file extension is correct.");
        } catch (IOException exception) {
            AlertBox.display("Minor error", "Could not find the given file. Please try again.");
        }
        return new ArrayList<>();
    }

    private static int randomIntGenerator(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }


    public static void generate() {
        // generate objectives
        for (int i = 0; i < boxesCount; i++) {
            boolean correctPosition;
            Position pos = new Position();
            do {
                correctPosition = true;
                pos.setX(randomIntGenerator(0, mapLength - 1));
                pos.setY(randomIntGenerator(0, mapHeight - 1));
                for (Position position : goals) {
                    if (isOverlapping(pos, position)) {
                        correctPosition = false;
                        break;
                    }
                }
                // If it's a correct position, check if it is correctly located on the canBeGoal map
                // If not, there's no need to do it
                if (correctPosition) {
                    correctPosition = mappedCanBeGoal[pos.getY()][pos.getX()];
                }
            } while (!correctPosition);
            goals.add(pos);
        }

        // generate boxes
        for (int i = 0; i < boxesCount; i++) {
            boolean correctPosition;
            boolean isOnObj = false;
            Position pos = new Position();
            do {
                correctPosition = true;
                pos.setX(randomIntGenerator(0, mapLength - 1));
                pos.setY(randomIntGenerator(0, mapHeight - 1));
                // If it's on a goal, then add it to the boxesOnObj list
                for (Position position : goals) {
                    if (isOverlapping(pos, position)) {
                        isOnObj = true;
                        break;
                    }
                }

                for (Position position : boxes) {
                    if (isOverlapping(pos, position)) {
                        correctPosition = false;
                    }
                }
                // If it's a correct position, we must still check if it's not on another box on objective
                if (correctPosition) {
                    for (Position position : boxesOnObj) {
                        if (isOverlapping(pos, position)) {
                            correctPosition = false;
                        }
                    }
                }

                if (correctPosition) {
                    correctPosition = mappedCanBeBox[pos.getY()][pos.getX()];
                }

            } while (!correctPosition);
            if (isOnObj) {
                boxesOnObj.add(pos);
                for (int j = 0; j < goals.size(); j++) {
                    if (isOverlapping(pos, goals.get(j))) {
                        goals.remove(goals.get(j));
                    }
                }
            } else {
                boxes.add(pos);
            }
        }

        // generate player
        boolean correctPosition;
        boolean isOnObj = false;
        Position pos = new Position();
        do {
            correctPosition = true;
            pos.setX(randomIntGenerator(0, mapLength - 1));
            pos.setY(randomIntGenerator(0, mapHeight - 1));

            for (Position position : boxes) {
                if (isOverlapping(pos, position)) {
                    correctPosition = false;
                    break;
                }
            }
            if (correctPosition) {
                for (Position position : boxesOnObj) {
                    if (isOverlapping(pos, position)) {
                        correctPosition = false;
                        break;
                    }
                }
            }
            if (correctPosition) {
                for (Position position : goals) {
                    if (isOverlapping(pos, position)) {
                        isOnObj = true;
                    }
                }
             }

            if (correctPosition) {
                if (pos.getX() == 0 || pos.getX() == 9 || pos.getY() == 0 || pos.getY() == 9) {
                    correctPosition = false;
                }
            }
        } while (!correctPosition);
        player = new Player(pos.getX(), pos.getY(), isOnObj, null);
    }

    public static void setContentBasedOnCurrentGeneration() {
        for (int y = 0; y < mapHeight; y++) {
            String line = "";
            for (int x = 0; x < mapLength; x++) {
                boolean pass = false;
                if (y == 0 || y == 9 || x == 0 || x == 9) {
                    line += "#";
                }
                else {
                    for (Position pos : boxes) {
                        if (isOverlapping(new Position(x, y), pos)) {
                            line += "$";
                            pass = true;
                            break;
                        }
                    }

                    if (!pass) {
                        for (Position pos : goals) {
                            if (isOverlapping(new Position(x, y), pos)) {
                                line += ".";
                                pass = true;
                                break;
                            }
                        }
                    }

                    if (!pass) {
                        for (Position pos : boxesOnObj) {
                            if (isOverlapping(new Position(x, y), pos)) {
                                line += "*";
                                pass = true;
                                break;
                            }
                        }
                    }

                    if (!pass) {
                        if (isOverlapping(new Position(player.getX(), player.getY()), new Position(x, y))) {
                            line += "@";
                            pass = true;
                        }
                    }

                    if (!pass) {
                        line += " ";
                    }
                }
            }
            content.add(line);
        }
    }

    public static ArrayList<String> getContent() {
        return content;
    }

    public static String getConfig() {
        String res = "Boxes : " + boxesCount + " | Length : " + mapLength + " | Height : " + mapHeight + "\n";
        for (int i = 0; i < boxes.size(); i++) {
            res += "Box " + i + " :        X > " + boxes.get(i).getX() + " | Y > " + boxes.get(i).getY() + "\n";
        }
        for (int i = 0; i < boxesOnObj.size(); i++) {
            res += "Box on obj " + i + " : X > " + boxesOnObj.get(i).getX() + " | Y > " + boxesOnObj.get(i).getY() + "\n";
        }
        for (int i = 0; i < goals.size(); i++) {
            res += "Goal " + i + " :       X > " + goals.get(i).getX() + " | Y > " + goals.get(i).getY() + "\n";
        }
        res += "Player :       X > " + player.getX() + " | Y > " + player.getY();
        return res;
    }

    private static boolean isOverlapping(Position a, Position b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }
}
