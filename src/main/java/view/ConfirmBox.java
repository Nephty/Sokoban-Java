package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean reply = false;

    /**
     * Static method to apply to the ConfirmBox class that pops up and pauses the execution of the
     * main window until the user closes the pop up. The user can answer to a question by yes or no
     * and the value will be returned as a boolean.
     * @param title The title of the pop up window
     * @param message The message displayed in the pop up window
     * @return boolean - true if the user answered yes, false otherwise
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(200);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton  = new Button("No");
        reply = false;

        yesButton.setOnAction(e -> {
            reply = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return reply;
    }
}
