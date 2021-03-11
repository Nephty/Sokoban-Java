package main.java.model;

public class Block {
    private int x;
    private int y;
    private String texture;

    public Block(int x_, int y_, String texture) {
        x = x_;
        y = y_;
        this.texture = texture;
        
    }

    // X Accessor
    public int getX() {
        return this.x;
    }

    // Y Accessor
    public int getY() {
        return this.y;
    }

    // X AND Y Modificator
    public void setValues(int newValueX, int newValueY) {
        this.x = newValueX;
        this.y = newValueY;
    }

    //X modificator
    public void setX(int newValue){
        this.x = newValue;
    }

    // Y Modificator
    public void setY(int newValue) {
        this.y = newValue;
    }

    public String getTexture(){
        return texture;
    }

    public void push(String direction){
        /*
            change the x,y of the box, depending on the direction
            direction = RIGHT,LEFT,UP,DOWN
        */
        if(this instanceof Box){
            /*
            change the x,y of the box, depending on the direction
            direction = RIGHT,LEFT,UP,DOWN
            */
            switch (direction) {
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

}