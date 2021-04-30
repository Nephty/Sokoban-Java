package model;

/**
 * <code>Teleport</code> is a <code>Block</code> used to teleport the player to the second Teleporter.
 * There can only be 2 Teleporters on the map. If the player walks into one, he goes to the second one.
 * Each Teleport has an attribute with the instance of the other Teleport.
 */
public class Teleport extends Block{
    private Teleport nextTP;
    /**
     * <code>Teleport</code> Constructor
     * @param x_ The row of the <code>Teleport</code> in the blockList
     * @param y_ The line of the <code>Teleport</code> in the blockList
     * @param image The name of the image of the <code>Teleport</code>
     * @param texture The texture of the Teleport (Texture : %)
     * @param nextTP The instance of the other Teleport
     */
    public Teleport(int x_, int y_,String image,String texture,Teleport nextTP) {
        super(x_, y_,image, texture);
        this.nextTP = nextTP;
    }

    /**
     * @return Always True
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
        if (player1.amIOnGoal()){
            blockList[player1.getY()][player1.getX()] = new Goal(player1.getX(), player1.getY(), "objective.png",".");
            player1.invertIsOnGoal();
        } else {
            blockList[player1.getY()][player1.getX()] = null;
        }
        blockList[nextTP.getY()][nextTP.getX()] = player1;
        player1.setCurrTP(nextTP);
        player1.setValues(nextTP.getX(), nextTP.getY());
        return currBoxOnObj;
    }


    /**
     * nextTP accessor
     * @return The instance of the Teleport linked to this Teleport
     */
    public Teleport getNextTP(){
        return nextTP;
    }

    /**
     * Change the value of the Teleport attribute
     * @param nextTP The instance of the other Teleport
     */
    public void setNextTP(Teleport nextTP){
        this.nextTP = nextTP;
    }
}
