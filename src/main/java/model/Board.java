package main.java.model;

import java.util.ArrayList;

public class Board {
    private String level
                    = "    #####\n"
                    + "    #   #\n"
                    + "    #$  #\n"
                    + "  ###  $##\n"
                    + "  #  $ $ #\n"
                    + "### # ## #   ######\n"
                    + "#   # ## #####  ..#\n"
                    + "# $  $          ..#\n"
                    + "##### #### #@#  ..#\n"
                    + "    #      ########\n"
                    + "    ########\n";

    private ArrayList<Wall> walls;
    private ArrayList<Box> boxes;
    private ArrayList<Objectiv> objectivs;
    private Block[][] blockList;

    private Player player1;
    private int levelHeight = 0;
    private int levelWidth = 0;
    private int currBoxOnObj = 0;

    public Board() {
        loadmap();
        setblockList();
    }
    /**
     * Read the String level and create the Arraylists of the walls/boxes and objectivs
     */
    private void loadmap(){
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        objectivs = new ArrayList<>();

        int x = 0;
        int y = 0;

        for (int i=0; i < level.length();i++) {
            char item = level.charAt(i);

            switch (item){

                case ' ':
                    x++;
                    break;

                case '#':
                    Wall wall = new Wall(x,y,"M");
                    walls.add(wall);
                    x++;
                    break;
                
                case '\n':
                    y++;
                    if (this.levelWidth < x){
                        this.levelWidth = x;
                    }
                    x=0;
                    break;
                
                case '$':
                    Box newBox = new Box(x,y,"B");
                    boxes.add(newBox);
                    x++;
                    break;
                
                case '.':
                    Objectiv newObjectiv = new Objectiv(x,y,"O");
                    objectivs.add(newObjectiv);
                    x++;
                    break;
                
                case '@':
                    player1 = new Player(x,y,"P");
                    x++;
                    break;
                
                default:
                    break;
            }
        }
        this.levelHeight = y;
    }
    /**
     * Create a 2D table y x with all the items of the game
     */
    private void setblockList(){
        ArrayList<Block> world = getWorld();
        blockList = new Block[levelHeight][levelWidth];
        for (Block item : world) {
            blockList[item.getY()][item.getX()] = item;
        }
    }

    /**
     * Return the current BlockList of the game.
     * @return current blocklist (Block[][])
    */
    public Block[][] getBlockList(){
        return blockList;
    }

    /**
     * Join all the blocks in one arrayList to set up the world blockList
     * @return ArrayList<Block> with all the blocks of the game(wall, box, obj, player)
     */
    private ArrayList<Block> getWorld(){
        ArrayList<Block> world = new ArrayList<>();
        
        world.addAll(walls);
        world.addAll(boxes);
        world.addAll(objectivs);
        world.add(player1);

        return world;
    }

    /**
     * levelHeight accessor.
     * @return int levelHeight
     */
    public int getlevelHeight(){
        return this.levelHeight;
    }

    /**
     * levelWidth accessor.
     * @return int levelWidth
     */
    public int getlevelWidth(){
        return this.levelWidth;
    }

    /**
     * Check if the all the boxes are on an objectiv AND if all the boxes are placed.
     * @return True if the game is won, false if not.
     */
    public boolean isWin(){
        return ((currBoxOnObj == objectivs.size()|| (currBoxOnObj == boxes.size())));
    }

    /**
     * try to move the player in the direction in parameter
            move the player if he can go this way
            push the box if there's a box that can go this way too
            otherwise, he doesn't move
     * @param direction The direction of the move (RIGHT-UP-LEFT-DOWN)
     */
    public void move(String direction){
        int pRow = player1.getX();
        int pLine = player1.getY();

        switch (direction) {
            case "UP":
                //is the case above a void case
                movePlayerUp(blockList[pLine-1][pRow], pLine, pRow);
                break;
            case "DOWN":
                movePlayerDown(blockList[pLine+1][pRow], pLine, pRow);
                break;

            case "LEFT":
                movePlayerLeft(blockList[pLine][pRow-1], pLine, pRow);
                break;
            case "RIGHT":
                movePlayerRight(blockList[pLine][pRow+1], pLine, pRow);
                break;
        }

    }

    /**
     * Check all the conditions if the player can move. if he can, he moves.
     * @param nextObj the Object above the player
     * @param pLine The line of the player (Y)
     * @param pRow The row of the player (X)
     */
    private void movePlayerUp(Block nextObj,int pLine,int pRow){
        //move on an objectiv
        if (nextObj instanceof Objectiv){
            if (player1 instanceof PlayerOnObj){
                //if the player wants to move on an objectiv while he's already on one
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine-1][pRow] = player1;
                player1.move("UP");
                return;
            }else{
                //if the player wants to move on an objectiv while's he isn't already on one
                player1 = new PlayerOnObj(pRow, pLine-1, "$");
                blockList[pLine-1][pRow] = player1;
                blockList[pLine][pRow] = null;
                return;
            }
        //A box is above the player 
        }else if (nextObj instanceof Box || nextObj instanceof BoxOnObj){
            //if nothing is above the box
            if (moveBoxUp(nextObj, blockList[pLine-2][pRow], pLine-1, pRow)){
                movePlayerUp(blockList[pLine-1][pRow], pLine, pRow);
            }
            return;
        //nothing is above the player      
        }else if (nextObj == null){
            //a player is on an objectiv so he change to Player object
            if (player1 instanceof PlayerOnObj){
                player1 = new Player(pRow, pLine-1, "P");
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine-1][pRow] = player1;
                return;
            //he isn't on a objectiv, he simply move forward.
            }else{
                blockList[pLine-1][pRow] = player1;
                blockList[pLine][pRow] = null;  
                player1.move("UP");
                return;
            }
        }
    }
    
    /**
     * Check all the condition if the box can move up.
     * @param currBox The box object we want to move.
     * @param nextObj The object above the box.
     * @param pLine Line of the player(Y)
     * @param pRow Row of the player (X)
     * @return true if the box moves, false otherwise. (avoid an infinite loop if the box doesn't move)
     */
    private boolean moveBoxUp(Block currBox,Block nextObj,int pLine, int pRow){
        if (nextObj == null){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine-1][pRow] = new Box(pRow,pLine-1,"B");
                currBoxOnObj--;
                return true;
            }else{
                blockList[pLine-1][pRow] = currBox;
                currBox.push("UP");
                blockList[pLine][pRow] = null;
                return true;
            }
        }else if (nextObj instanceof Objectiv){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine-1][pRow] = currBox;
                currBox.push("UP");
                return true;
            }else{
                blockList[pLine][pRow] = null;
                blockList[pLine-1][pRow] = new BoxOnObj(pRow, pLine-1, "&");
                currBoxOnObj++;
                return true;
            }
        }    
        else{
            return false;
        }
    }

    /**
     * Check all the conditions if the player can move. if he can, he moves.
     * @param nextObj the Object under the player
     * @param pLine The line of the player (Y)
     * @param pRow The row of the player (X)
     */
    private void movePlayerDown(Block nextObj,int pLine,int pRow){
        //move on an objectiv
        if (nextObj instanceof Objectiv){
            if (player1 instanceof PlayerOnObj){
                //if the player wants to move on an objectiv while he's already on one
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine+1][pRow] = player1;
                player1.move("DOWN");
                return;
            }else{
                //if the player wants to move on an objectiv while's he isn't already on one
                player1 = new PlayerOnObj(pRow, pLine+1, "$");
                blockList[pLine+1][pRow] = player1;
                blockList[pLine][pRow] = null;
                return;
            }
        //A box is under the player 
        }else if (nextObj instanceof Box || nextObj instanceof BoxOnObj){
            //if the box can move, we move the player
            if (moveBoxDown(nextObj, blockList[pLine+2][pRow], pLine+1, pRow)){
                movePlayerDown(blockList[pLine+1][pRow], pLine, pRow);
            }
            return;
        //nothing is under the player      
        }else if (nextObj == null){
            //a player is on an objectiv so he change to Player object
            if (player1 instanceof PlayerOnObj){
                player1 = new Player(pRow, pLine+1, "P");
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine+1][pRow] = player1;
                return;
            //he isn't on a objectiv, he simply move backward.
            }else{
                blockList[pLine+1][pRow] = player1;
                blockList[pLine][pRow] = null;  
                player1.move("DOWN");
                return;
            }
        }
    }

    /**
     * Check all the condition if the box can move up.
     * @param currBox The box object we want to move.
     * @param nextObj The object under the box.
     * @param pLine Line of the player(Y)
     * @param pRow Row of the player (X)
     * @return true if the box moves, false otherwise. (avoid an infinite loop if the box doesn't move)
     */
    private boolean moveBoxDown(Block currBox,Block nextObj,int pLine, int pRow){
        if (nextObj == null){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine+1][pRow] = new Box(pRow,pLine+1,"B");
                currBoxOnObj--;
                return true;
            }else{
                blockList[pLine+1][pRow] = currBox;
                currBox.push("DOWN");
                blockList[pLine][pRow] = null;
                return true;
            }
        }else if (nextObj instanceof Objectiv){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine+1][pRow] = currBox;
                currBox.push("DOWN");
                return true;
            }else{
                blockList[pLine][pRow] = null;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow+1] = new BoxOnObj(pRow, pLine+1, "&");
                currBoxOnObj--;
                return true;
            }
        }    
        else{
            return false;
        }
    }

    /**
     * Check all the conditions if the player can move. if he can, he moves.
     * @param nextObj the Object to the left of the player
     * @param pLine The line of the player (Y)
     * @param pRow The row of the player (X)
     */
    private void movePlayerLeft(Block nextObj,int pLine,int pRow){
        //move on an objectiv
        if (nextObj instanceof Objectiv){
            if (player1 instanceof PlayerOnObj){
                //if the player wants to move on an objectiv while he's already on one
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow-1] = player1;
                player1.move("LEFT");
                return;
            }else{
                //if the player wants to move on an objectiv while's he isn't already on one
                player1 = new PlayerOnObj(pRow-1, pLine, "$");
                blockList[pLine][pRow-1] = player1;
                blockList[pLine][pRow] = null;
                return;
            }
        //A box is to the left of the player 
        }else if (nextObj instanceof Box || nextObj instanceof BoxOnObj){
            //if the box can move, we move the player
            if (moveBoxLeft(nextObj, blockList[pLine][pRow-2], pLine, pRow-1)){
                movePlayerLeft(blockList[pLine][pRow-1], pLine, pRow);
            }
            return;
        //nothing is to the left of the player      
        }else if (nextObj == null){
            //a player is on an objectiv so he change to Player object
            if (player1 instanceof PlayerOnObj){
                player1 = new Player(pRow-1, pLine, "P");
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine][pRow-1] = player1;
                return;
            //he isn't on a objectiv, he simply move to the left.
            }else{
                blockList[pLine][pRow-1] = player1;
                blockList[pLine][pRow] = null;  
                player1.move("LEFT");
                return;
            }
        }else{
            return;
        }
    }

        /**
     * Check all the condition if the box can move up.
     * @param currBox The box object we want to move.
     * @param nextObj The object to the left the box.
     * @param pLine Line of the player(Y)
     * @param pRow Row of the player (X)
     * @return true if the box moves, false otherwise. (avoid an infinite loop if the box doesn't move)
     */
    private boolean moveBoxLeft(Block currBox,Block nextObj,int pLine, int pRow){
        if (nextObj == null){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine][pRow-1] = new Box(pRow-1,pLine,"B");
                currBoxOnObj--;
                return true;
            }else{
                blockList[pLine][pRow-1] = currBox;
                currBox.push("LEFT");
                blockList[pLine][pRow] = null;
                return true;
            }
        }else if (nextObj instanceof Objectiv){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow-1] = currBox;
                currBox.push("LEFT");
                return true;
            }else{
                blockList[pLine][pRow] = null;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow-1] = new BoxOnObj(pRow-1, pLine, "&");
                currBoxOnObj++;
                return true;
            }
        }    
        else{
            return false;
        }
    }

    /**
     * Check all the conditions if the player can move. if he can, he moves.
     * @param nextObj the Object to the left of the player
     * @param pLine The line of the player (Y)
     * @param pRow The row of the player (X)
     */
    private void movePlayerRight(Block nextObj,int pLine,int pRow){
        //move on an objectiv
        if (nextObj instanceof Objectiv){
            if (player1 instanceof PlayerOnObj){
                //if the player wants to move on an objectiv while he's already on one
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow+1] = player1;
                player1.move("RIGHT");
                return;
            }else{
                //if the player wants to move on an objectiv while's he isn't already on one
                player1 = new PlayerOnObj(pRow+1, pLine, "$");
                blockList[pLine][pRow+1] = player1;
                blockList[pLine][pRow] = null;
                return;
            }
        //A box is to the left of the player 
        }else if (nextObj instanceof Box || nextObj instanceof BoxOnObj){
            //if the box can move, we move the player
            if (moveBoxRight(nextObj, blockList[pLine][pRow+2], pLine, pRow+1)){
                movePlayerRight(blockList[pLine][pRow+1], pLine, pRow);
            }
            return;
        //nothing is to the left of the player      
        }else if (nextObj == null){
            //a player is on an objectiv so he change to Player object
            if (player1 instanceof PlayerOnObj){
                player1 = new Player(pRow+1, pLine, "P");
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine][pRow+1] = player1;
                return;
            //he isn't on a objectiv, he simply move to the Right.
            }else{
                blockList[pLine][pRow+1] = player1;
                blockList[pLine][pRow] = null;  
                player1.move("RIGHT");
                return;
            }
        }
    }

    /**
     * Check all the condition if the box can move up.
     * @param currBox The box object we want to move.
     * @param nextObj The object to the right the box.
     * @param pLine Line of the player(Y)
     * @param pRow Row of the player (X)
     * @return true if the box moves, false otherwise. (avoid an infinite loop if the box doesn't move)
     */
    private boolean moveBoxRight(Block currBox,Block nextObj,int pLine, int pRow){
        if (nextObj == null){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = new Objectiv(pRow, pLine, "O");
                blockList[pLine][pRow+1] = new Box(pRow+1,pLine,"B");
                currBoxOnObj--;
                return true;
            }else{
                blockList[pLine][pRow+1] = currBox;
                currBox.push("RIGHT");
                blockList[pLine][pRow] = null;
                return true;
            }
        }else if (nextObj instanceof Objectiv){
            if (currBox instanceof BoxOnObj){
                blockList[pLine][pRow] = nextObj;
                nextObj.setValues(pRow, pLine);
                blockList[pLine][pRow+1] = currBox;
                currBox.push("RIGHT");
                return true;
            }else{
                blockList[pLine][pRow] = null;
                blockList[pLine][pRow+1] = new BoxOnObj(pRow+1, pLine, "&");
                currBoxOnObj++;
                return true;
            }
        }    
        else{
            return false;
        }
    }

}