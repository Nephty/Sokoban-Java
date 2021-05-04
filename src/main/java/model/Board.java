package model;
import view.AlertBox;

import java.util.ArrayList;

public class Board {
    private ArrayList<String> level;
    private ArrayList<Block> world;
    private ArrayList<Box> boxes;
    private ArrayList<Goal> goals;
    private Block[][] blockList;

    private Player player1;
    private int levelHeight = 0;
    private int levelWidth = 0;
    private int currBoxOnObj = 0;

    private PressurePlate plate;

    /**
     * Creates a new <code>Board</code> objects that has no level.
     */
    public Board() {
    }

    /**
     * Creates a new <code>Board</code> object with a starting level.
     * @param level The level contained in the Board
     */
    public Board(ArrayList<String> level) {
        this.level = level;
        loadMap(level);
        setBlockList();
    }
    /**
     * Read the String level and create the Arraylists of the walls/boxes and goals
     * @param level The level contained in the Board
     */
    private void  loadMap(ArrayList<String> level) {
        world = new ArrayList<>();
        boxes = new ArrayList<>();
        goals = new ArrayList<>();

        Teleport tmpTeleport = null;

        int x = 0;
        int y = -1;

        for (String line : level) {
            if (this.levelWidth < x){
                this.levelWidth = x;
            }
            x = 0;
            y++;
            for (int i=0; i < line.length();i++) {
                char item = line.charAt(i);

                switch (item){

                    case ' ':
                        x++;
                        break;

                    case '#':
                        Wall wall = new Wall(x,y);
                        world.add(wall);
                        x++;
                        break;

                    case '$':
                        Box newBox = new Box(x,y,false);
                        boxes.add(newBox);
                        x++;
                        break;

                    case '.':
                        Goal newGoal = new Goal(x,y);
                        goals.add(newGoal);
                        x++;
                        break;

                    case '@':
                        player1 = new Player(x,y,false, null);
                        x++;
                        break;

                    case '+':
                        player1 = new Player(x,y,true, null);
                        x++;
                        break;
                    case '%':
                        if (tmpTeleport == null){
                            tmpTeleport = new Teleport(x,y,null);
                        } else if (tmpTeleport.getNextTP() != null){
                            // TODO : system.exit here ?
                            AlertBox.display("Minor error", "There can only be 2 Teleports in the map !");
                            System.exit(-1);
                        } else {
                            Teleport tp = new Teleport(x,y,tmpTeleport);
                            world.add(tp);
                            tmpTeleport.setNextTP(tp);
                            world.add(tmpTeleport);
                        }
                        x++;
                        break;

                    case '*':
                        Box newBoxOnObj = new Box(x, y,true);
                        boxes.add(newBoxOnObj);
                        currBoxOnObj++;
                        x++;
                        break;
                    case '=':
                        Wall newWall = new GhostWall(x,y);
                        world.add(newWall);
                        x++;
                        break;
                    case '1':
                        plate = new PressurePlate(x,y,"1","RickRoll");
                        x++;
                        break;
                    case '2':
                        plate = new PressurePlate(x,y, "2", "SecretMap");
                        x++;
                        break;
                    default:
                        throw new IllegalArgumentException(item + " isn't a supported character.\nPlease read the " +
                                "game documentation to know the supported characters");
                }
            }
        }
        this.levelHeight = y+1;
        if (player1 == null){
            // TODO : system.exit here ?
            AlertBox.display("Minor error", "There must be a player in your map (Texture = @)");
            System.exit(-1);
        }
        if (tmpTeleport != null && tmpTeleport.getNextTP() == null){
            // TODO : system.exit here ?
            AlertBox.display("Minor error", "There must be 0 or 2 Teleport in the game");
            System.exit(-1);
        }
    }
    /**
     * Create a 2D table with all the items of the game
     */
    private void setBlockList(){
        getWorld();
        blockList = new Block[levelHeight][levelWidth];
        for (Block item : world) {
            blockList[item.getY()][item.getX()] = item;
        }
    }

    /**
     * Return the current <code>blockList</code> of the game.
     * @return current 2D table
    */
    public Block[][] getBlockList(){
        return blockList;
    }

    /**
     * Join all the blocks in one arrayList to set up the world blockList
     */
    private void getWorld(){
        world.addAll(boxes);
        world.addAll(goals);
        world.add(player1);
        if (plate != null) {
            world.add(plate);
        }
    }

    /**
     * levelHeight accessor.
     * @return int levelHeight
     */
    public int getLevelHeight(){
        return this.levelHeight;
    }

    /**
     * levelWidth accessor.
     * @return int levelWidth
     */
    public int getLevelWidth(){
        return this.levelWidth;
    }

    /**
     * Check if the all the boxes are on an goal.
     * @return True if the game is won
     */
    public boolean isWin(){
        return currBoxOnObj == boxes.size();
    }

    /**
     * Boxes arrayList accessor
     * @return An arrayList with all the boxes of the game
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public int getCurrBoxOnObj() {
        return currBoxOnObj;
    }

    /**
     * Set up all the parameters and try to move the player.
     * @param direction The direction of the move
     * @return BooleanCouple which is used to know if the player moved and if he pushed a box
     */
    public BooleanCouple move(Direction direction){
        int pRow = player1.getX();
        int pLine = player1.getY();
        int nextX;
        int nextY;
        int nextX2;
        int nextY2;

        BooleanCouple returnValue = new BooleanCouple(false, false);

        switch (direction) {
            case UP:
                nextY = pLine-1;
                nextX = pRow;
                nextY2= nextY-1;
                nextX2 = nextX;
                break;
            case DOWN:
                nextY = pLine+1;
                nextX = pRow;
                nextY2= nextY+1;
                nextX2 = nextX;
                break;

            case LEFT:
                nextY = pLine;
                nextX = pRow-1;
                nextY2= nextY;
                nextX2 = nextX-1;
                break;
            case RIGHT:
                nextY = pLine;
                nextX = pRow+1;
                nextY2= nextY;
                nextX2 = nextX+1;
                break;
            default:
                return returnValue;
        }
        //if the next case is still in the map size
        if (nextX < this.levelWidth && nextX >= 0 && nextY < this.levelHeight && nextY >= 0) {
            this.currBoxOnObj = player1.move(nextX,nextY,nextX2,nextY2,blockList,returnValue, currBoxOnObj, levelHeight, levelWidth);
        }
        return returnValue;
    }

    /**
     * Restart the map.
     */
    public void restart(){
        currBoxOnObj = 0;
        loadMap(level);
        setBlockList();
    }

    /**
     * Compare the current Board with another Board (Used for the tests)
     * @param map The Board we want to compare with
     * @return True if the Boards are equals
     */
    public boolean isEquals(Board map) {
        Block[][] blockList2 = map.getBlockList();
        try {
            for (int i = 0; i < this.blockList.length; i++) {
                for (int j = 0; j < this.blockList[0].length; j++) {
                    Block obj1 = this.blockList[i][j];
                    Block obj2 = blockList2[i][j];
                    if (obj1 == null || obj2 == null) {
                        return obj1 == obj2;
                    }
                    if (!obj1.getClass().equals(obj2.getClass())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }

    /**
     * Convert the blockList to an ArrayList String and returns it.
     * @return String ArrayList made from the blockList
     */
    public ArrayList<String> toArrayList(){
        ArrayList<String> res = new ArrayList<>();
        for (Block[] blocks : blockList) {
            String line = "";
            for (Block block : blocks) {
                if (block == null) {
                    line += " ";
                } else {
                    line += block.getTexture();
                }
            }
            res.add(line);
        }
        return res;
    }

    /**
     * Apply the moves to the current map
     * @param moves (ArrayList of the directions we want to apply
     */
    public void applyMoves(ArrayList<Direction> moves){
        for (Direction dir : moves){
            this.move(dir);
        }
    }

    /**
     * Player1 accessor
     * @return The current instance of the player1
     */
    public Player getPlayer1(){
        return player1;
    }
}