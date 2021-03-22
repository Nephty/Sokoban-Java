package main.java.model;

public class Box extends Block {
    public Box(int x_, int y_, String texture) {
        super(x_, y_,texture);
    }

    public void move(Direction direction){
        super.move(direction);
            //change the x,y of the box, depending on the direction
            //direction = RIGHT,LEFT,UP,DOWN
        switch (direction) {
            case LEFT:
                setX(getX()-1);
                break;
            
            case RIGHT:
                setX(getX()+1);
                break;
            
            case UP:
                setY(getY()-1);
                break;
            
            case DOWN:
                setY(getY()+1);
        }
    }

    public boolean canPass(Block nextObj){
        super.canPass(nextObj);
        if (nextObj instanceof  Box){
            return false;
        }
        else if (nextObj.canPass(nextObj)){
            return true;
        }
        else
            return false;
    }
}