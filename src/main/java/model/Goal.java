
package model;

import view.AlertBox;

/**
 * <code>Goal</code> is one of the main Blocks of the game.
 * We have to put the <code>Boxes</code> on it.
 */
public class Goal extends Block {
    /**
     * <code>Goal</code> constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     * @param image The name of the image of the <code>Goal</code>
     * @param texture The texture of the <Code>Goal/Code> (Texture : .)
     */
    public Goal(int x_, int y_, String image,String texture) {
        super(x_, y_,image,texture);
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
        if (player1.amIOnGoal()) {
            blockList[player1.getY()][player1.getX()] = this;
            blockList[this.getY()][this.getX()] = player1;
            int pLine = player1.getY();
            int pRow = player1.getX();
            player1.setValues(this.getX(), this.getY());
            this.setValues(pRow, pLine);
        } else if (player1.getCurrTp() != null){
            blockList[this.getY()][this.getX()] = player1;
            blockList[player1.getY()][player1.getX()] = player1.getCurrTp();
            System.out.println(blockList[this.getY()][this.getX()].getTexture());
            player1.setValues(this.getX(), this.getY());
            player1.invertIsOnGoal();
            player1.setCurrTP(null);
        } else {
            player1.invertIsOnGoal();
            blockList[this.getY()][this.getX()] = player1;
            blockList[player1.getY()][player1.getX()] = null;
            player1.setValues(this.getX(), this.getY());
        }
        return currBoxOnObj;
    }

}
