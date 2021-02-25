package model;

public class Block {
    private int x;
    private int y;

    public Block(int x_, int y_) {
        x = x_;
        y = y_;
    }

    // X Accessor
    public int getX() {
        return this.x;
    }

    // Y Accessor
    public int getY() {
        return this.y;
    }

    // X Modificator
    public void setX(int newValue) {
        this.x = newValue;
    }

    // Y Modificator
    public void setY(int newValue) {
        this.y = newValue;
    }
}