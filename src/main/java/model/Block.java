package model;


import javafx.scene.image.Image;
import view.AlertBox;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The <code>Block</code> is the super class of all the blocks of the game.
 * Every blocks has their position (x,y) in attributes and they also have an Image (Image object)
 * and a Texture (Used in .xsb files). Each subclass of <code>Block</code> must implements the methods getTexture() and
 * getImage().
 */
public abstract class Block {
    public static final Image airImg = loadImg("air.png");
    private int x;
    private int y;

    /**
     * Block default constructor
     * @param x_ The row of the block in the blockList
     * @param y_ The line of the block in the blockList
     */
    public Block(int x_, int y_) {
        x = x_;
        y = y_;

    }

    /**
     * X accessor
     * @return The row of the Block
     */
    public int getX() {
        return this.x;
    }

    /**
     * Y accessor
     * @return The line of the Block
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
     * @return The texture of the Block (the char in .xsb file)
     */
    public abstract String getTexture();

    /**
     * Image accessor
     * @return The Image of the Block (Image object)
     */
    public abstract Image getImage();

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
     * Used for all the blocks except for the boxes.
     * @return true if we can move toward the block
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
     * This method always returns false in the class but it's implemented in the class <code>Player</code>
     * or in the class <code>Box</code>
     * @return True if the player/Box is on a goal
     */
    public boolean amIOnGoal(){
        return false;
    }

    public static Image loadImg(String name){
        try(FileInputStream inputStream = new FileInputStream(FileGetter.directory("img") + name)){
            return new Image(inputStream);
        } catch (IOException exc){
            AlertBox.display("Fatal error", "A .png file could not be found. Check if no file is missing.\n" +
                    "Check if the names have not been changed or if any file has not been deleted.\n" +
                    "You can run the FileIntegrity checker for further information.\n Missing file : " + name + ".");
            System.exit(-1);
            return null;
        }
    }
}