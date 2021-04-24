package model;


/**
 * The <code>GhostWall</code> is a block which has the same image than a wall but the player can move towards it.
 * It's used for the EasterEggs.
 */
public class GhostWall extends Wall{

    /**
     * <code>GhostWall</code> constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     * @param image The name of the image of the <code>GhostWall</code>
     * @param texture The texture of the <Code>GhostWall</Code> (Texture : =)
     */
    public GhostWall(int x_, int y_,String image,String texture) {
        super(x_, y_,image, texture);
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
        super.push(nextX, nextY, blockList, player1,returnValue, currBoxOnObj);
        blockList[player1.getY()][player1.getX()] = null;
        blockList[nextY][nextX] = player1;
        player1.setValues(nextX, nextY);
        return currBoxOnObj;
    }
}