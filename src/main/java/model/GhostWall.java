package model;


/**
 * The <code>GhostWall</code> is a block which has the same image than a wall but the player can move towards it.
 * It's used for the EasterEggs.
 */
public class GhostWall extends Wall{
    private static final String Texture = "=";
    private static final String Image = "wall.png";

    /**
     * <code>GhostWall</code> constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     */
    public GhostWall(int x_, int y_) {
        super(x_, y_);
    }

    /**
     * @return Always True
     */
    @Override
    public boolean canPass(){
        return true;
    }


    /**
     * move the player to the nextX,nextY position
     * @param nextX The row of the Block
     * @param nextY The line of the Block
     * @param blockList The list of all the blocks
     * @param player1 The instance of the player
     * @param returnValue BooleanCouple : used to know if the player moved or not
     * @param currBoxOnObj The current amount of box on Objectives
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    @Override
    public int push(int nextX, int nextY, Block[][] blockList, Player player1, BooleanCouple returnValue, int currBoxOnObj){
        blockList[player1.getY()][player1.getX()] = null;
        blockList[nextY][nextX] = player1;
        player1.setValues(nextX, nextY);
        return currBoxOnObj;
    }
}