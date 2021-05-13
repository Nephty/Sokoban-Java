package model;

/**
 * A <code>Position</code> object is a simple one dimensional array-like object that stores an x and a y value
 * that references to a position of a point in a graph or in a map.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Create a new <code>Position object without setting the x and y attributes.</code>
     */
    public Position() {
    }

    /**
     * Create a new <code>Position</code> object and set its x and y attributes.
     * @param x The x position of the object
     * @param y The y position of the object
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the current x position
     * @return The current x position
     */
    public int getX() {
        return x;
    }

    /**
     * Return the current y position
     * @return The current y position
     */
    public int getY() {
        return y;
    }

    /**
     * Set the value of x to the new value.
     * @param x The new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the value of y to the new value.
     * @param y The new y value
     */
    public void setY(int y) {
        this.y = y;
    }
}
