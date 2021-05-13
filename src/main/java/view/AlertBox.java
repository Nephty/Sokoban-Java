package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The <code>AlertBox</code> is a pop up window that can have a title and display a message. Showing the window
 * will pause the execution of the main window until the user closes the window either by pressing on the
 * close button in the top right corner or by pressing the X button displayed below the message.
 */
public class AlertBox {
    /**
     * Static method to apply to the AlertBox class that pops up a new window with the given title and message.
     * @param title The title of the pop up window
     * @param message The message displayed in the pop up window
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(500);
        window.setHeight(350);

        Label label = new Label();
        label.setText(message);
        label.setAlignment(Pos.CENTER);
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
