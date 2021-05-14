package model;

import view.AlertBox;

import javax.naming.TimeLimitExceededException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The <code>Gen</code> class handle the generation. It has 2 possibility for the generation :
 * With the Difficulty (EASY-NORMAL-HARD) or with a custom size and a custom number of boxes.
 * The generation can only create squares and use DeadLocks method to generate a playable level.
 */
public class Gen {

    private final Random random = new Random();

    private int line;
    private int row;
    private int numberOfObject;
    private boolean[][] tabPosVal;
    public String[][] content;


    /**
     * Launch the generation with the given parameters.
     * @param width The width of the map (and the height)
     * @param numberOfObject The number of boxes in the game.
     */
    public void launch(int width, int numberOfObject){
        line = width;
        row = width;
        this.numberOfObject = numberOfObject;
        tabPosVal = new boolean[line][row];
        content = new String[line][row];
        createMap();
        contentToLevel();
    }
    /**
     * Launch the generation with the given difficulty
     * @param difficulty The difficulty of the level that will be generated (EASY - NORMAL - HARD)
     */
    public void launch(Difficulty difficulty){
        try {
            switch (difficulty) {
                case EASY:
                    line = 7;
                    row = 7;
                    numberOfObject = 3;
                    break;
                case NORMAL:
                    line = 15;
                    row = 15;
                    numberOfObject = 10;
                    break;
                case HARD:
                    line = 20;
                    row = 20;
                    numberOfObject = 30;
                    break;
                default:
                    throw new IllegalArgumentException("The " + difficulty + " isn't handled by the generation. \n" +
                            "Only EASY-NORMAL-HARD are handled.");
            }
            tabPosVal = new boolean[line][row];
            content = new String[line][row];
            createMap();
            contentToLevel();
        } catch (IllegalArgumentException e){
            AlertBox.display("Minor error", e.getMessage());
        }
    }

    /**
     * Reset the current generated level.
     */
    private void restart(){
        tabPosVal = new boolean[line][row];
        content = new String[line][row];
        createMap();
        contentToLevel();
    }

    /**
     * Create the default square for the generation. (empty with walls on the side)
     */
    private void createMap() {
        for(int i=0; i < line; i++) {
            if(i == 0 || i == (line-1)) {
                for(int n=0; n < row; n++){
                    content[i][n] = "#";
                }
            } else {
                for(int n=0; n < row; n++){
                    if(n == 0 || n == (row-1)) {
                        content[i][n] = "#";
                    }else {
                        content[i][n] = " ";
                    }
                }
            }
        }
    }

    /**
     * Add all the Blocks to the level. At first, we add the player.
     * Then, we add the Goal and we get the <code>DeadLocks</code>
     * Finally, we add the boxes. We get the new <code>DeadLocks</code> each time we add a Box.
     */
    private void contentToLevel() {
        try {
            Position posPlayer = randomPosition();
            content[posPlayer.getY()][posPlayer.getX()] = "@";
            nVP(posPlayer.getY(), posPlayer.getX());

            int boucle = 0;
            do {
                Position posGoal = randomPosition();
                if (!isACorner(posGoal.getY(),posGoal.getX())) {
                    // if the block has been changed, it returns true.
                    if (changeBlock(posGoal, ".")) {
                        boucle++;
                    }
                }
            }
            while (boucle != numberOfObject);
            getDeadLocks();

            boucle = 0;
            int tries = 0;
            do {
                Position posBox = randomPosition();
                //if we can put a box here
                if (tabPosVal[posBox.getY()][posBox.getX()]) {
                    // // if the block has been changed
                    if (changeBlock(posBox, "$")) {
                        tabPosVal[posBox.getY()][posBox.getX()] = false;
                        boucle++;
                        tries = 0;
                        getDeadLocks();
                    }else {
                        tries++;
                        if (tries > 200){
                            throw new TimeLimitExceededException("");
                        }
                    }
                }
            }
            while (boucle != numberOfObject);
        } catch (TimeLimitExceededException e){
            restart();
        }
    }


    /**
     * Change the value of the block in the content list.
     * @param posObject The position of the new object
     * @param textureObject The Texture of the object.
     * @return True if the block has been placed.
     */
    private boolean changeBlock(Position posObject, String textureObject) {
        if (content[posObject.getY()][posObject.getX()].equals(" ")){
            content[posObject.getY()][posObject.getX()] = textureObject;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generate a random position.
     * @return The new position generated randomly.
     */
    private Position randomPosition() {
        Position posObject = new Position();

        posObject.setY(random.nextInt(line -2) + 1);
        posObject.setX(random.nextInt(row -2) + 1);

        return posObject;
    }

    /**
     * Get all the deadlocks of the board (the squares where a box is blocked).
     */
    private void getDeadLocks() {
        for(int i=1; i < line-1; i++) {
            for(int n=1; n < row-1; n++) {
                    //if we're on a wall or a Goal.
                if (content[i][n].equals("#") || content[i][n].equals(".")) {
                    nVP(i, n);
                } else {
                    if (content[i-1][n].equals("#") || content[i + 1][n].equals("#") ||
                            content[i-1][n].equals("$") || content[i + 1][n].equals("$"))
                    {
                        // if we're in a corner
                        if (content[i][n - 1].equals("#") || content[i][n + 1].equals("#") ||
                                content[i][n - 1].equals("$") || content[i][n + 1].equals("$"))
                        {
                            nVP(i,n);
                        }else if (isDeadLine(i)){
                            nVP(i,n);
                        } else {
                            vP(i,n);
                        }
                    } else if (content[i][n - 1].equals("#") || content[i][n + 1].equals("#") ||
                            content[i][n - 1].equals("$") || content[i][n + 1].equals("$"))
                    {
                        if (content[i-1][n].equals("#") || content[i + 1][n].equals("#") ||
                                content[i-1][n].equals("$") || content[i + 1][n].equals("$"))
                        {
                            nVP(i,n);
                        }else if (isDeadRow(n)){
                            nVP(i,n);
                        } else {
                            vP(i,n);
                        }
                    } else{
                        vP(i,n);
                    }
                }
            }
        }
    }

    /**
     * Used to know if the block [i][n] is a corner.
     * @param i The line we want to test.
     * @param n The row we want to test.
     * @return True if the block (n,i) is a corner.
     */
    private boolean isACorner(int i, int n){
        return ((content[i][n-1].equals("#") || content[i][n+1].equals("#"))
                && (content[i+1][n].equals("#") || content[i-1][n].equals("#")));
    }


    /**
     * Used to know if the given line is dead.
     * (if the number of goals is equal to the number of boxes)
     * @param i The line we want the test.
     * @return True if the Line is dead.
     */
    private boolean isDeadLine(int i){
        int boxes=0;
        int goals=0;
        for (int n=0; n<row;n++){
            String elem = content[i][n];
            if (elem.equals("$")){
                boxes++;
            }else if (elem.equals(".")){
                goals++;
            }
        }
        return boxes == goals;
    }

    /**
     * Used to know if the given row is dead.
     * (if the number of goals is equal to the number of boxes)
     * @param n The row we want the test.
     * @return True if the row is dead.
     */
    private boolean isDeadRow(int n){
        int boxes=0;
        int goals=0;
        for (int i=0; i<line;i++){
            String elem = content[i][n];
            if (elem.equals("$")){
                boxes++;
            }else if (elem.equals(".")){
                goals++;
            }
        }
        return boxes == goals;
    }

    /**
     * Valid position : set the position [i][n] in the tabPosVal to true.
     * @param i The line we want to change.
     * @param n The row we want to change.
     */
    public void vP(int i, int n) {
        if(!tabPosVal[i][n]) {
            tabPosVal[i][n] = true;
        }
    }

    /**
     * Non valid position : set the position [i][n] in the tabPosVal to false.
     * @param i The line we want to change.
     * @param n The row we want to change.
     */
    public void nVP(int i, int n) {
        if(tabPosVal[i][n]) {
            tabPosVal[i][n] = false;
        }
    }

    /**
     * Content accessor
     * @return The ArrayList of String with the map that has been generated.
     */
    public ArrayList<String> getContent(){
        ArrayList<String> res = new ArrayList<>();
        for (String[] line : content){
            StringBuilder BobTheBuilder = new StringBuilder();
            for (String h : line){
                BobTheBuilder.append(h);
            }
            res.add(BobTheBuilder.toString());
        }
        return res;
    }
}
