package view;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.JSONWriter;
import presenter.Main;

/**
 * A <code>Console</code> is a user interface display apart from the main window. It contains a <code>TextField</code>
 * to input text and can recognize commands. The available commands are :
 * - setdata
 *       usage : /setdata [key] [value]
 *       action : set the value with the key [key] to the new value [value] in the data.json file
 */
public class Console {
    private static final Stage window = new Stage();
    private static final Pane mainPane = new Pane();
    private static final Scene mainScene = new Scene(mainPane, 600, 400);
    private static final String COMMAND_KEY = "/";
    private static final String SET_DATA_COMMAND = "setdata";
    private static final String RESET_RATING_COMMAND = "resetrating";
    private static final String RESET_DATA = "resetdata";

    /**
     * Open the console.
     */
    public static void open() {
        window.show();
    }

    /**
     * Close the console.
     */
    public static void close() {
        window.close();
    }

    /**
     * Prepare the required data by setting the main scene, the background and the different commands.
     */
    public static void prepare() {
        window.setScene(mainScene);

        Rectangle rect = new Rectangle(600, 400);
        rect.setFill(Color.rgb(0, 0, 0));

        TextField textInput = new TextField();
        textInput.setPrefWidth(300);
        textInput.setLayoutX(150);
        textInput.setLayoutY(195);

        prepareCommands(textInput);

        mainPane.getChildren().addAll(rect, textInput);
    }

    /**
     * Modify the data with the given key to the new given value.
     * @param key The key of the data
     * @param value The new value to assign
     */
    private static void setData(String key, String value) {
        JSONWriter jsonWriter = new JSONWriter("data.json");
        jsonWriter.set(key, value);
    }

    /**
     * Prepare the different commands for usage in the given <code>TextField</code>.
     * @param textInput The text input we want to assign the <code>EventHandler</code> to
     */
    private static void prepareCommands(TextField textInput) {
        textInput.setOnAction(e -> {
            String input = textInput.getText();
            textInput.setText("");

            if (input.startsWith(COMMAND_KEY)) {
                String command = input.substring(1);

                // COMMAND : /setdata [key] [value] --> Set the data with the key [key] to the value [value] in the data.json file
                if (command.startsWith(SET_DATA_COMMAND) && !command.endsWith(SET_DATA_COMMAND)) {
                    String key = command.substring(8);
                    String value;

                    if (key.startsWith("completed levels") && !key.endsWith("completed levels")) {
                        value = key.substring(17);
                        if (Integer.parseInt(value) >= 0) {
                            setData(key.substring(0, key.length() - value.length() - 1), value);
                        }
                    }
                    else if (key.startsWith("music") && !key.endsWith("music")) {
                        value = key.substring(6);
                        if (Float.parseFloat(value) >= AudioPlayer.MINIMUM_VOLUME && Float.parseFloat(value) <= AudioPlayer.MAXIMUM_VOLUME) {
                            setData(key.substring(0, key.length() - value.length() - 1), value);
                            Main.audioPlayer.setVolume(Float.parseFloat(value));
                        }
                    }
                    else if (key.startsWith("effect") && !key.endsWith("effect")) {
                        value = key.substring(7);
                        if (Float.parseFloat(value) >= AudioPlayer.MINIMUM_VOLUME && Float.parseFloat(value) <= AudioPlayer.MAXIMUM_VOLUME) {
                            setData(key.substring(0, key.length() - value.length() - 1), value);
                            Main.effectPlayer.setVolume(Float.parseFloat(value));
                        }
                    }
                    else if (key.startsWith("resolution") && !key.endsWith("resolution")) {
                        value = key.substring(11);
                        if (Integer.parseInt(value) <= 6 && Integer.parseInt(value) >= 0) {
                            setData(key.substring(0, key.length() - value.length() - 1), value);
                        }
                    }
                }

                else if (command.startsWith(RESET_RATING_COMMAND)) {

                    // COMMAND : /resetrating
                    JSONWriter jsonWriter = new JSONWriter("avg.json");
                    String modeKey = "c";  // key to specify campaign levels
                    for (int levelID = 0; levelID <= 15; levelID++) {
                        jsonWriter.set(modeKey + levelID + "q", "0");
                        jsonWriter.set(modeKey + levelID + "r", "0");
                    }

                    // COMMAND : /resetdata
                } else if (command.startsWith(RESET_DATA)) {
                    // /resetrating
                    JSONWriter jsonWriter = new JSONWriter("avg.json");
                    String modeKey = "c";  // key to specify campaign levels
                    for (int levelID = 0; levelID <= 15; levelID++) {
                        jsonWriter.set(modeKey + levelID + "q", "0");
                        jsonWriter.set(modeKey + levelID + "r", "0");
                    }

                    JSONWriter data = new JSONWriter("data.json");
                    data.set("completed levels", "0");
                    data.set("music", "0.5");
                    data.set("effect", "0.5");
                    data.set("resolution", "0");
                }

                // OTHER COMMANDS...
            }
        });
    }
}