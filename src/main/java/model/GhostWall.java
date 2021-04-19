package model;


public class GhostWall extends Wall{

    public GhostWall(int x_, int y_,String image,String texture) {
        super(x_, y_,image, texture);
    }

    /**
     * @return (boolean) Always True
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