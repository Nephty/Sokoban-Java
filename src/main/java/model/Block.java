package model;

public class Block {
    private int x;
    private int y;
    private String texture;

    /**
     * Block default constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     * @param texture The file name of the image of the block
     */
    public Block(int x_, int y_, String texture) {
        x = x_;
        y = y_;
        this.texture = texture;
        
    }

    /**
     * X accessor
     * @return (int) the x value of the object
     */
    public int getX() {
        return this.x;
    }

    /**
     * Y accessor
     * @return (int) the y value of the object
     */
    public int getY() {
        return this.y;
    }

    /**
     * X and Y setter
     * @param newValueX The new row of the Block
     * @param newValueY The new line of the Block
     */
    public void setValues(int newValueX, int newValueY) {
        this.x = newValueX;
        this.y = newValueY;
    }

    /**
     * X setter
     * @param newValue the new row of the Block
     */
    public void setX(int newValue){
        this.x = newValue;
    }

    /**
     * Y setter
     * @param newValue the new line of the Block
     */
    public void setY(int newValue) {
        this.y = newValue;
    }

    /**
     * Texture accessor
     * @return (String) name of the image of the block
     */
    public String getTexture(){
        return texture;
    }

    /**
     * Used to know if we can move towards the block
     * @param nextObj The Object after the Block
     * @return true if we can move, false otherwise()
     */
    public boolean canPass(Block nextObj){
        if (! (this instanceof Box) ){
            return this.canPass();
        }
        return false;
    }

    /**
     * Used in the canPass(Block nextObj) method
     * @return (boolean) true if we can move toward the block
     */
    public boolean canPass(){
        return false;
    }

    /**
     * Push the Block to the nextX,nextY position
     * @param nextX The row of the next Block
     * @param nextY The line of the next Block
     * @param blockList The list with all the blocks
     * @param player1 The instance of the player
     * @param returnValue BooleanCouple : used to know if the player moved or not
     * @param currBoxOnObj The current amount of box on Objectives
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    public int push(int nextX, int nextY, Block[][] blockList,Player player1, BooleanCouple returnValue, int currBoxOnObj){
        return currBoxOnObj;
    }

    /**
     * Always false in the Block class.
     * @return false (but change in the Player class)
     */
    public boolean amIOnGoal(){
        return false;
    }

}