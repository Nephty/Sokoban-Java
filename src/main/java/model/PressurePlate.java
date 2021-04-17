package model;

public class PressurePlate extends Block{

    private String effect;
    public PressurePlate(int x_, int y_,String image,String texture, String effect) {
        super(x_, y_, image,texture);
        this.effect = effect;
    }

    /**
     * @return (boolean) Always True
     */
    @Override
    public boolean canPass(){
        super.canPass();
        return true;
    }

    /**
     * move the player to the nextX,nextY position
     * @param nextX The x value of the next block
     * @param nextY The y value of the next block
     * @param blockList The blockList of the game
     * @param player1 Instance of the player who moves
     * @param returnValue BooleanCouple used to know if the player has moved
     * @param currBoxOnObj The current amount of box on Objectives
     * @return currBoxOnObj because we need to return it to update the counter if it changes
     */
    @Override
    public int push(int nextX, int nextY, Block[][] blockList, Player player1, BooleanCouple returnValue, int currBoxOnObj){
        super.push(nextX, nextY, blockList, player1,returnValue, currBoxOnObj);
        player1.setPlate(this);
        blockList[this.getY()][this.getX()] = player1;
        blockList[player1.getY()][player1.getX()] = null;
        player1.setValues(this.getX(), this.getY());
        return currBoxOnObj;
    }


    /**
     * Effect accessor
     * @return The effect of the pressure plate
     */
    public String getEffect(){
        return effect;
    }


}
