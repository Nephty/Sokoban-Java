package model;


public class Goal extends Block {
    public Goal(int x_, int y_, String texture) {
        super(x_, y_,texture);
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
        if (player1.amIOnGoal()) {
            blockList[player1.getY()][player1.getX()] = this;
            blockList[this.getY()][this.getX()] = player1;
            int pLine = player1.getY();
            int pRow = player1.getX();
            player1.setValues(this.getX(), this.getY());
            this.setValues(pRow, pLine);
        } else {
            player1.changeGoalValue();
            blockList[this.getY()][this.getX()] = player1;
            blockList[player1.getY()][player1.getX()] = null;
            player1.setValues(this.getX(), this.getY());
        }
        return currBoxOnObj;
    }

}
