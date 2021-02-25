package presenter;

public class Run {
    public static void main(String[] args) {
        model.Wall n = new model.Wall(0, 0);
        model.Air n2 = new model.Air(0, 0);
        model.Block n3 = new model.Block(0, 0);
        model.Box n4 = new model.Box(0, 0);
        model.BoxOnObjective n5 = new model.BoxOnObjective(0, 0);
        model.Player n6 = new model.Player(0, 0);
        model.PlayerOnObjective n7 = new model.PlayerOnObjective(0, 0);

        model.Map map = new model.Map();

        view.GUI gui = new view.GUI();

        System.out.println(n.getX());
        System.out.println(n6.getX());
    }
}