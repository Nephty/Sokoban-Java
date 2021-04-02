package  main.java.model;

public class PlayerOnObj extends Player{
    public PlayerOnObj(int x_, int y_, String texture) {
        super(x_, y_,texture);
    }

    public void move(String Direction){
        /*
            change the x,y of the player, depending on the direction
            direction = RIGHT,LEFT,UP,DOWN
        */
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
    }
}
