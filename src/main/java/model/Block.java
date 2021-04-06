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

    // X AND Y Setter
    public void setValues(int newValueX, int newValueY) {
        this.x = newValueX;
        this.y = newValueY;
    }

    //X Setter
    public void setX(int newValue){
        this.x = newValue;
    }

    // Y Setter
    public void setY(int newValue) {
        this.y = newValue;
    }

    public String getTexture(){
        return texture;
    }

    public void move(Direction direction){
    }

    public boolean canPass(Block nextObj){
        if (! (this instanceof Box) ){
            return this.canPass();
        }
        return false;
    }

    public boolean canPass(){
        return false;
    }

}