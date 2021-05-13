
package model;

import javafx.scene.image.Image;

/**
 * <code>Goal</code> is one of the main Blocks of the game.
 * We have to put the <code>Boxes</code> on it.
 */
public class Goal extends Block {
    private static final String Texture = ".";
    private static final Image goalImg = loadImg("objective.png");

    /**
     * <code>Goal</code> constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     */
    public Goal(int x_, int y_) {
        super(x_, y_);
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

    /**
     * @return The Texture of the Goal : "."
     */
    @Override
    public String getTexture(){
        return Texture;
    }

    /**
     * Image accessor
     * @return The image of the goal (Image object)
     */
    @Override
    public Image getImage(){
        return goalImg;
    }

}
