package  model;

public class Goal extends Block {
    public Goal(int x_, int y_, String texture) {
        super(x_, y_,texture);
    }

    public boolean canPass(){
        super.canPass();
        return true;
    }

}
