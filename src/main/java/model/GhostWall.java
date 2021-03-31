package model;

public class GhostWall extends Wall{

    public GhostWall(int x_, int y_,String texture) {
        super(x_, y_,texture);
    }

    @Override
    public boolean canPass(){
        return true;
    }
}
