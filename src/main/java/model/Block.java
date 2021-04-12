package model;

public class Block {
    private int x;
    private int y;
    private String texture;

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
     * @param newValueX
     * @param newValueY
     */
    public void setValues(int newValueX, int newValueY) {
        this.x = newValueX;
        this.y = newValueY;
    }

    /**
     * X setter
     * @param newValue
     */
    public void setX(int newValue){
        this.x = newValue;
    }

    /**
     * Y setter
     * @param newValue
     */
    public void setY(int newValue) {
        this.y = newValue;
    }

    /**
     * Texture accessor
     * @return (String) texture of the Block
     */
    public String getTexture(){
        return texture;
    }

    /**
     * Used to know if we can move towards the block
     * @param nextObj
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
     * @param nextX
     * @param nextY
     * @param blockList
     * @param player1
     * @param returnValue
     * @param currBoxOnObj
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    public int push(int nextX, int nextY, Block[][] blockList,Player player1, BooleanCouple returnValue, int currBoxOnObj){
        return currBoxOnObj;
    }

    /**
     * 
     * @return True if the Box/Player is on a Goal
     */
    public boolean amIOnGoal(){
        return false;
    }

}