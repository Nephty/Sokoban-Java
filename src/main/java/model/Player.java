package model;

public class Player extends Block {
    private boolean isOnGoal;
    private PressurePlate plate;
    public Player(int x_, int y_,String texture, boolean isOnGoal) {
        super(x_, y_,texture);
        this.isOnGoal = isOnGoal;
    }


    /**
     * Move the player to the nextX,nextY position
     * @param nextX The x value of the next block
     * @param nextY The y value of the next block
     * @param nextX2 The x value of the 2nd block (player - next block - 2nd block)
     * @param nextY2 The y value of the 2nd block (player - next block - 2nd block)
     * @param blockList the list with all the blocks
     * @param returnValue BooleanCouple : used to know if the player moved or not
     * @param currBoxOnObj The current amount of box on Objectives
     * @param levelHeight The height of the level
     * @param levelWidth The width of the level
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    public int move(int nextX, int nextY, int nextX2, int nextY2, Block[][] blockList,
                            BooleanCouple returnValue, int currBoxOnObj, int levelHeight, int levelWidth){
        Block nextObj = blockList[nextY][nextX];
        if (nextObj == null) {
            if (this.amIOnGoal()){
                this.changeGoalValue();
                blockList[this.getY()][this.getX()] = new Goal(this.getX(), this.getY(), "objective.png");
                blockList[nextY][nextX] = this;
                this.setValues(nextX, nextY);
            } else if (isOnPressurePlate()){
                blockList[this.getY()][this.getX()] = plate;
                blockList[nextY][nextX] = this;
                this.setValues(nextX, nextY);
                this.setPlate(null);
            } else {
                blockList[nextY][nextX] = this;
                blockList[this.getY()][this.getX()] = null;
                this.setValues(nextX, nextY);
            }
            returnValue.setA(true);
            
        } 
        else if (nextX2 < levelWidth && nextX2 >= 0 && nextY2 < levelHeight && nextY2 >= 0){
            if (nextObj.canPass(blockList[nextY2][nextX2])) {
                currBoxOnObj = nextObj.push(nextX2, nextY2, blockList, this,returnValue, currBoxOnObj);
                //if a box as moved, we have to move the player too.
                if (returnValue.isB()){
                    this.move(nextX, nextY, nextX2, nextY2, blockList, returnValue, currBoxOnObj, levelHeight, levelWidth);
                }
                returnValue.setA(true);
            }
        }
        return currBoxOnObj;
    }

    /**
     * @return (boolean) True if the Player is on a Goal.
     */
    @Override
    public boolean amIOnGoal(){
        return isOnGoal;
    }

    /**
     * Change the value of isOnGoal
     */
    public void changeGoalValue(){
        if (isOnGoal == true){
            isOnGoal = false;
        }else{
            isOnGoal = true;
        }
    }

    /**
     * Set the pressure plate where the player is.
     * @param plate The instance of the pressure plate
     */
    public void setPlate(PressurePlate plate){
        this.plate = plate;
    }

    /**
     *
     * @return True if the player is on a pressurePlate
     */
    public boolean isOnPressurePlate(){
        return (plate != null);
    }

    /**
     * Plate accessor
     * @return The plate where the player is
     */
    public PressurePlate getPlate(){
        return plate;
    }
}
