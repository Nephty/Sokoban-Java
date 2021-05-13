package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FileGetter;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The <code>CompleteFieldBox</code> is a pop up window that can have a title, display a message, ask
 * the user for some input and have a prompt message to invite the user to give their input.
 * The input will be given in a <code>TextField</code> (see <code>javafx.scene.control.TextField</code>).
 * According to the selected method, the pop up can contain a <code>FileChooser</code>
 * (see <code>javafx.stage.FileChooser</code>). Upon selecting a file, its name is written into the TextField and
 * the user can still modify it or confirm.
 */
public class CompleteFieldBox {
    /**
     * Static method to apply to the <code>CompleteFieldBox</code> class that pops up and pauses the execution of the
     * main window until the user enters a string and confirms.
     * @param title The title of the pop up window
     * @param message The message displayed in the pop up window
     * @param promptText The text to prompt in the text field for the user
     * @return The string the user entered
     */
    public static String display(String title, String message, String promptText) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(200);

        Label label = new Label();
        label.setText(message);

        TextField input = new TextField();
        input.setPrefWidth(300);
        input.setPromptText(promptText);

        Button closeButton = new Button("Confirm");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, input, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        window.setOnCloseRequest(e-> {
            window.close();
        });

        return input.getText();
    }

    /**
     * Static method to apply to the <code>CompleteFieldBox</code> class that pops up and pauses the execution of the
     * main window until the user enters a string and confirms. There is a button "Open..." that allows the user to
     * select a file, and the file's name will be entered in the text field box for the user to confirm the choice.
     * It is also checking if the file ends with ".mov" and is in the saves directory.
     * @param title The title of the pop up window
     * @param message The message displayed in the pop up window
     * @param promptText The text to prompt in the text field for the user
     * @return The string the user entered
     */
    public static String displayFileSelector(String title, String message, String promptText) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(200);

        Label label = new Label();
        label.setText(message);

        Label fileNotFoundLabel = new Label();
        fileNotFoundLabel.setVisible(false);
        AtomicInteger errorCount = new AtomicInteger(0);

        TextField input = new TextField();
        input.setPrefWidth(300);
        input.setPromptText(promptText);

        File directoryPath = new File(FileGetter.directory("saves"));
        String[] files = directoryPath.list();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(FileGetter.directory("saves")));

        Button openFileSelection = new Button("Open...");
        openFileSelection.setOnAction(e -> {
            File returnedFile = fileChooser.showOpenDialog(window);
            if (returnedFile != null) {
                input.setText(returnedFile.getName());
            }
        });

        Button closeButton = new Button("Confirm");
        closeButton.setOnAction(e -> {
            if (!Arrays.asList(files).contains(input.getText()) && !fileNotFoundLabel.isVisible()) {
                fileNotFoundLabel.setVisible(true);
                errorCount.getAndIncrement();
                if (input.getText().equals("")) {
                    fileNotFoundLabel.setText("Please enter a file name" + " (" + errorCount.toString() + ")");
                } else {
                    if (!input.getText().endsWith(".mov")) {
                        fileNotFoundLabel.setText("Wrong file extension" + " (" + errorCount.toString() + ")");
                    } else {
                        fileNotFoundLabel.setText("File not found" + " (" + errorCount.toString() + ")");
                    }
                }
            } else if (Arrays.asList(files).contains(input.getText())) {
                window.close();
            } else {
                if (!fileNotFoundLabel.isVisible()) {
                    fileNotFoundLabel.setVisible(true);
                }
                errorCount.getAndIncrement();
                if (fileNotFoundLabel.getText().endsWith(")")) {
                    if (input.getText().equals("")) {
                        fileNotFoundLabel.setText("Please enter a file name" + " (" + errorCount.toString() + ")");
                    } else {
                        if (!input.getText().endsWith(".mov")) {
                            fileNotFoundLabel.setText("Wrong file extension" + " (" + errorCount.toString() + ")");
                        } else {
                            fileNotFoundLabel.setText("File not found" + " (" + errorCount.toString() + ")");
                        }
                    }
                }
            }
        });

        window.setOnCloseRequest(e-> {
            e.consume();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, input, openFileSelection, closeButton, fileNotFoundLabel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return input.getText();
    }
}
