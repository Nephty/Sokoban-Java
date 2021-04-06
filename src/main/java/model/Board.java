package main.java.model;
import java.util.ArrayList;

public class Board {
    private final ArrayList<String> level;
    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Goal> goals;
    private Block[][] blockList;

    private Player player1;
    private int levelHeight = 0;
    private int levelWidth = 0;
    private int currBoxOnObj = 0;

    private int hiddenPressurePlateX;
    private int hiddenPressurePlateY;

    public Board(ArrayList<String> level) {
        this.level = level;
        loadMap(level);
        setBlockList();
    }
    /**
     * Read the String level and create the Arraylists of the walls/boxes and goals
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
                        Wall wall = new Wall(x,y,"M");
                        walls.add(wall);
                        x++;
                        break;

                    case '$':
                        Box newBox = new Box(x,y,"B");
                        boxes.add(newBox);
                        x++;
                        break;

                    case '.':
                        Goal newGoal = new Goal(x,y,"O");
                        goals.add(newGoal);
                        x++;
                        break;

                    case '@':
                        player1 = new Player(x,y,"P");
                        x++;
                        break;

                    case '+':
                        player1 = new PlayerOnObj(x,y,"$");
                        x++;
                        break;

                    case '*':
                        BoxOnObj newBoxOnObj = new BoxOnObj(x, y, "$");
                        boxes.add(newBoxOnObj);
                        currBoxOnObj++;
                        x++;
                        break;
                    case '=':
                        Wall newWall = new GhostWall(x,y, "=");
                        walls.add(newWall);
                        x++;
                        break;
                    case '^':
                        hiddenPressurePlateX = x;
                        hiddenPressurePlateY = y;
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
     * try to move the player in the direction in parameter
     move the player if he can go this way
     push the box if there's a box that can go this way too
     otherwise, he doesn't move
     * @param direction The direction of the move (RIGHT-UP-LEFT-DOWN)
     * @return BooleanCouple A couple of booleans, the first element is true
     *          if the player moved and the second element is true
     *          if the player pushes a box.
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
        if (nextX < this.levelWidth && nextX >= 0 && nextY < this.levelHeight && nextY >= 0) {
            nextObj = blockList[nextY][nextX];
            if (nextObj == null) {
                if (player1 instanceof PlayerOnObj) {
                    player1 = new Player(nextX, nextY, "P");
                    blockList[pLine][pRow] = new Goal(pRow, pLine, "O");
                    blockList[nextY][nextX] = player1;
                    //he isn't on a goal, he simply move forward.
                } else {
                    blockList[nextY][nextX] = player1;
                    blockList[pLine][pRow] = null;
                    player1.move(direction);
                }
                returnValue.setA(true);
                return returnValue;
            } else if (nextX2 < this.levelWidth && nextX2 >= 0 && nextY2 < this.levelHeight && nextY2 >= 0) {
                if (nextObj.canPass(blockList[nextY2][nextX2])) {
                    if (nextObj instanceof Goal) {
                        if (player1 instanceof PlayerOnObj) {
                            //if the player wants to move on an goal while he's already on one
                            blockList[pLine][pRow] = nextObj;
                            nextObj.setValues(pRow, pLine);
                            blockList[nextY][nextX] = player1;
                            player1.move(direction);
                        } else {
                            //if the player wants to move on an goal while's he isn't already on one
                            player1 = new PlayerOnObj(nextX, nextY, "$");
                            blockList[nextY][nextX] = player1;
                            blockList[pLine][pRow] = null;
                        }
                    } else if (nextObj instanceof GhostWall) {
                        blockList[pLine][pRow] = null;
                        blockList[nextY2][nextX2] = player1;
                        player1.setX(nextX2);
                        player1.setY(nextY2);
                    } else {
                        moveBox(nextObj, blockList[nextY2][nextX2], nextY, nextX, nextX2, nextY2, direction);
                        move(direction);
                        returnValue.setB(true);
                    }
                    returnValue.setA(true);
                }
                return returnValue;
            }
        }
        return returnValue;
    }

    /**
     * Check all the condition if the box can move up.
     * @param currBox The box object we want to move.
     * @param nextObj The object above the box.
     * @param bLine Line of the Box(Y)
     * @param bRow Row of the Box (X)
     * @param nextX Row of the next Block(X)
     * @param nextY Line of the next Block(x)
     * @return true if the box moves, false otherwise. (avoid an infinite loop if the box doesn't move)
     */
    private boolean moveBox(Block currBox,Block nextObj,int bLine, int bRow, int nextX, int nextY, Direction dir){
        if (nextObj == null){
            if (currBox instanceof BoxOnObj){
                blockList[bLine][bRow] = new Goal(bRow, bLine, "O");
                blockList[nextY][nextX] = new Box(nextX,nextY,"B");
                currBoxOnObj--;
                return true;
            }else{
                blockList[nextY][nextX] = currBox;
                currBox.move(dir);
                blockList[bLine][bRow] = null;
                return true;
            }
        }else if (nextObj instanceof Goal){
            if (currBox instanceof BoxOnObj){
                blockList[bLine][bRow] = nextObj;
                nextObj.setValues(bRow, bLine);
                blockList[nextY][nextX] = currBox;
                currBox.move(dir);
                return true;
            }else{
                blockList[bLine][bRow] = null;
                blockList[nextY][nextX] = new BoxOnObj(nextX, nextY, "&");
                currBoxOnObj++;
                return true;
            }
        }
        else{
            return false;
        }
    }

    public boolean isOnPressurePlate(){
        return ((player1.getX() == hiddenPressurePlateX) && (player1.getY() == hiddenPressurePlateY));
    }
    public void restart(){
        currBoxOnObj = 0;
        loadMap(level);
        setBlockList();
    }
}