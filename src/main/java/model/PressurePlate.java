package model;

public class PressurePlate extends Block{

    private String effect;
    public PressurePlate(int x_, int y_, String texture, String effect) {
        super(x_, y_, texture);
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
     * @param nextX
     * @param nextY
     * @param blockList
     * @param player1
     * @param returnValue
     * @param currBoxOnObj
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


    public String getEffect(){
        return effect;
    }


}
