package model;

/**
 * The <code>BooleanCouple</code> is used to know if the player has moved or not.
 * If the player moves, the <code>a</code> parameter is set to true.
 * And if the player pushes a <code>box</code>, the <code>b</code> parameter is set to true.
 */
public class BooleanCouple {
    private boolean a;
    private boolean b;

    public BooleanCouple(boolean a, boolean b) {
        this.a = a;
        this.b = b;
    }

    /**
     * a accessor
     * @return <code>a</code> value
     */
    public boolean isA() {
        return a;
    }

    /**
     * Set the parameter a
     * @param a The new value of a.
     */
    public void setA(boolean a) {
        this.a = a;
    }

    /**
     * b accessor
     * @return <code>b</code> value
     */
    public boolean isB() {
        return b;
    }

    /**
     * Set the parameter b
     * @param b The new Value of b
     */
    public void setB(boolean b) {
        this.b = b;
    }
}
