package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Application2 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        new Thread(() -> {
            //while (true) {
            //    System.out.println("hey I'm a loop on the other Thread");
            //}
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
    /*
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("I'm working on another thread");
        }));
    }
     */
}
