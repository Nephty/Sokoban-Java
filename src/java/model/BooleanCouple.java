package main.java.model;

public class BooleanCouple {
    private boolean a;
    private boolean b;

    public BooleanCouple() {
    }

    public BooleanCouple(boolean a, boolean b) {
        this.a = a;
        this.b = b;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
