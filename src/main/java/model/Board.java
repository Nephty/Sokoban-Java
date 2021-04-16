package model;
import java.util.ArrayList;

public class Board {
    private ArrayList<String> level;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Goal> goals;
    private Block[][] blockList;

    private Player player1;
    private int levelHeight = 0;
    private int levelWidth = 0;
    private int currBoxOnObj = 0;

    private PressurePlate plate;

    public Board() {
    }

    public Board(ArrayList<String> level) {
        this.level = level;
        loadMap(level);
        setBlockList();
    }
    /**
     * Read the String level and create the Arraylists of the walls/boxes and goals
     * @param level
     */
    private void  loadMap(ArrayList<String> level){
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        goals = new ArrayList<>();

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
                        Wall wall = new Wall(x,y,"wall.png","#");
                        walls.add(wall);
                        x++;
                        break;

                    case '$':
                        Box newBox = new Box(x,y,"box.png","$",false);
                        boxes.add(newBox);
                        x++;
                        break;

                    case '.':
                        Goal newGoal = new Goal(x,y,"objective.png", ".");
                        goals.add(newGoal);
                        x++;
                        break;

                    case '@':
                        player1 = new Player(x,y,"player down.png","@",false);
                        x++;
                        break;

                    case '+':
                        player1 = new Player(x,y,"player down.png","+",true);
                        x++;
                        break;

                    case '*':
                        Box newBoxOnObj = new Box(x, y, "box.png", "*",true);
                        boxes.add(newBoxOnObj);
                        currBoxOnObj++;
                        x++;
                        break;
                    case '=':
                        Wall newWall = new GhostWall(x,y, "wall.png","=");
                        walls.add(newWall);
                        x++;
                        break;
                    case '^':
                        plate = new PressurePlate(x,y,"air.png","^","RickRoll");
                        x++;
                        break;
                    default:
                        throw new IllegalArgumentException("Le contenu du fichier n'est pas compatible");
                }
            }
        }
        this.levelHeight = y+1;
    }
    /**
     * Create a 2D table y x with all the items of the game
     */
    private void setBlockList(){
        ArrayList<Block> world = getWorld();
        blockList = new Block[levelHeight][levelWidth];
        for (Block item : world) {
            blockList[item.getY()][item.getX()] = item;
        }
    }

    /**
     * Return the current BlockList of the game.
     * @return current blocklist (Block[][])
    */
    public Block[][] getBlockList(){
        return blockList;
    }

    /**
     * Join all the blocks in one arrayList to set up the world blockList
     * @return ArrayList<Block> with all the blocks of the game(wall, box, obj, player)
     */
    private ArrayList<Block> getWorld(){
        ArrayList<Block> world = new ArrayList<>();
        
        world.addAll(walls);
        world.addAll(boxes);
        world.addAll(goals);
        world.add(player1);
        if (plate != null) {
            world.add(plate);
        }

        return world;
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
     * Check if the all the boxes are on an goal AND if all the boxes are placed.
     * @return True if the game is won, false if not.
     */
    public boolean isWin(){
        return currBoxOnObj == boxes.size();
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public int getCurrBoxOnObj() {
        return currBoxOnObj;
    }

    /**
     *
     * @param direction The direction of the move (RIGHT-UP-LEFT-DOWN)
     * @return (BooleanCouple) used to know if the player moved and if he pushed a box
     */
    public BooleanCouple move(Direction direction){
        int pRow = player1.getX();
        int pLine = player1.getY();
        int nextX;
        int nextY;
        Block nextObj;
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
                        if (obj1 == obj2) {
                            return true;
                        }
                        return false;
                    }
                    if (!obj1.getClass().equals(obj2.getClass())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Take the blockList and return the ArrayList String made from it.
     * @return String ArrayList of the board
     */
    public ArrayList<String> toArrayList(){
        ArrayList<String> res = new ArrayList<>();
        for (int i=0;i<blockList.length;i++){
            String line = "";
            for (int j=0; j<blockList[i].length;j++){
                if (blockList[i][j] == null){
                    line += " ";
                }else {
                    line += blockList[i][j].getTexture();
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

    public Player getPlayer1(){
        return player1;
    }

}