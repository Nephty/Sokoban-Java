package main.java.model;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LogInMenu {
    public LogInMenu(double width_, double height_, Stage window, Scene targetScene, String password) {

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username :");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField("Nephty");
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 0, 1);

        Label passwordLabel = new Label("Password :");
        GridPane.setConstraints(passwordLabel, 1, 0);

        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button logInButton = new Button("Log In");
        GridPane.setConstraints(logInButton, 1, 2);
        logInButton.setOnAction(e -> {
            if (passwordInput.getText().equals(password)) {
                window.setScene(targetScene);
                window.setX((int)((2560/2)-(1920/2)));
                window.setY((int)((1440/2)-(1080/2)-180));
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput, logInButton); // TODO : fix

        window.setScene(new Scene(grid, width_, height_));
    }
}
