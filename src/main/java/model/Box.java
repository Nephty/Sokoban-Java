package model;
import javafx.scene.image.Image;

/**
 * The <code>Box</code> is a special <code>Block</code> of the game. It can be pushed by the player.
 * The player has to put all the boxes on the goal. When a Box is on a goal, the attribute <code>isOnGoal</code>
 * is equal to true.
 */
public class Box extends Block {
    private boolean isOnGoal;

    private static final String Texture = "$";
    private static final Image boxOnGoalImg = loadImg("boxOnGoal.png");
    private static final Image boxImg = loadImg("box.png");

    /**
     * Box constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     * @param isOnGoal Is the Box is on a goal.
     */
    public Box(int x_, int y_, boolean isOnGoal) {
        super(x_, y_);
        this.isOnGoal = isOnGoal;
    }

    /**
     * Use to know if we can push the Box
     * @param nextObj the Object which is after the Box
     * @return True if we can move the box
     */
    @Override
    public boolean canPass(Block nextObj){
        super.canPass(nextObj);
        if (nextObj instanceof  Box || nextObj instanceof Teleport){
            return false;
        }
        else return nextObj == null || nextObj.canPass(nextObj);
    }

    /**
     * @return (boolean) True if the Box is on a Goal.
     */
    @Override
    public boolean amIOnGoal(){
        super.amIOnGoal();
        return isOnGoal;
    }

    /**
     * Invert the value of isOnGoal.
     */
    public void invertIsOnGoal(){
        isOnGoal = !isOnGoal;
    }

    /**
     * Push the Box to the nextX,nextY position
     * @param nextX The row of the next Block
     * @param nextY The Line of the next Block
     * @param blockList The list of all the blocks
     * @param player1 The instance of the player
     * @param returnValue BooleanCouple : used to know if the player moved or not
     * @param currBoxOnObj The current amount of box on Objectives
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    @Override
    public int push(int nextX, int nextY, Block[][] blockList, Player player1, BooleanCouple returnValue, int currBoxOnObj){
        super.push(nextX, nextY, blockList,player1, returnValue, currBoxOnObj);
        Block nextObj = blockList[nextY][nextX];
        if (nextObj == null){
            //if the box is on a Goal
            if (amIOnGoal()){
                blockList[this.getY()][this.getX()] = new Goal(this.getX(), this.getY());
                blockList[nextY][nextX] = this;
                this.setValues(nextX, nextY);
                this.invertIsOnGoal();
                currBoxOnObj--;
            } else {
                blockList[nextY][nextX] = this;
                blockList[this.getY()][this.getX()] = null;
                this.setValues(nextX, nextY);
            }
        } else if (nextObj instanceof Goal){
            //if the box is on a Goal
            if (amIOnGoal()){
                blockList[this.getY()][this.getX()] = nextObj;
                nextObj.setValues(this.getX(), this.getY());
                blockList[nextY][nextX] = this;
                this.setValues(nextX, nextY);
            }else{
                blockList[this.getY()][this.getX()] = null;
                blockList[nextY][nextX] = this;
                this.setValues(nextX, nextY);
                this.invertIsOnGoal();
                currBoxOnObj++;
            }
        }
        returnValue.setB(true);
        return currBoxOnObj;
    }

    /**
     * Image accessor
     * @return Test if the box is on a Goal and return the image (Image object)
     */
    @Override
    public Image getImage(){
        if (amIOnGoal()){
            return boxOnGoalImg;
        }else {
            return boxImg;
        }
    }

    /**
     * @return The texture of the block used in the .xsb file.
     */
    @Override
    public String getTexture(){
        String res = Texture;
        if (amIOnGoal()){
            res = "*";
        }
        return res;
    }
}
