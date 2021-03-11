package main.java.model;

public class Box extends Block {
    public Box(int x_, int y_, String texture) {
        super(x_, y_,texture);
    }
    /*
    public void push(String Direction){
        
            //change the x,y of the box, depending on the direction
            //direction = RIGHT,LEFT,UP,DOWN
        
        switch (Direction) {
            case "LEFT":
                setX(getX()-1);
                break;
            
            case "RIGHT":
                setX(getX()+1);
                break;
            
            case "UP":
                setY(getY()-1);
                break;
            
            case "DOWN":
                setY(getY()+1);
        }
    }*/
}