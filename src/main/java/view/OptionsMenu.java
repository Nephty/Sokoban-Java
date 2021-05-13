package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import model.JSONReader;
import model.JSONWriter;
import presenter.Main;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An <code>OptionsMenu</code> is a user interface used to display all the settings and modifying them.
 * It contains a list of available resolutions and allows the user to change the resolution. The game must be
 * restarted in order to apply this change.
 */
public class OptionsMenu
        extends Menu {

    private HBox resolution;
    private HBox musicVolume;
    private HBox effectVolume;
    private CustomButton backButtonOptions;
    private String starterMusicVolume;
    private String starterEffectVolume;
    private AudioPlayer audioPlayer;
    private AudioPlayer effectPlayer;
    private HBox upHBox, downHBox, rightHBox, leftHBox, restartHBox, loadHBox, saveHBox, openConsHBox, closeConsHBox,
        autoPromptHBox;
    private TextField upControl, downControl, rightControl, leftControl;
    private TextField restartControl, loadControl, saveControl, openConsControl, closeConsControl;
    // If we want to move every keybind selector to the right of to the left, we can simply modify this value
    private final int keybindOffset = 0;
    private static boolean autoPromptRating = true;

    /**
     * Create a new <code>OptionsMenu</code> and pepare the attributes and the resolution list and choice.
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @param background_ The background displayed on the options menu
     * @param audioPlayer_ The <code>AudioPlayer</code> that will play the main theme music
     * @param effectPlayer_ The <code>AudioPlayer</code> that will be used to play sound effects
     */
    public OptionsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_, AudioPlayer audioPlayer_, AudioPlayer effectPlayer_) {
        super(parent_, width_, height_, WR, HR, background_);
        this.backButtonOptions = new CustomButton((int)((width_-480-5)), (int)((height_-96-5)), WR, HR, "back button.png");
        if (!Main.fullscreen) {
            this.backButtonOptions.setLayoutY(-50);
            this.backButtonOptions.overlay.setLayoutY(-50);
        }
        this.background = background_;
        this.audioPlayer = audioPlayer_;
        this.effectPlayer = effectPlayer_;
        prepareVolume();
        this.setComboBox();
        this.setMusicVolume();
        this.setEffectVolume();
        this.setControl();
    }

    /**
     * Set the starterMusicVolume and the starterEffectVolume of the game
     * Used to know what's the value of the volume when the game just opened
     */
    private void prepareVolume() {
        JSONReader reader = new JSONReader("data.json");
        starterMusicVolume = reader.getString("music");
        starterEffectVolume = reader.getString("effect");
    }

    /**
     * Create and prepare the <code>ComboBox</code> object that will display the available resolutions and
     * allow the user to change the resolution.
     */
    public void setComboBox() {
        Label resolutionLabel = new Label("Resolution :");
        resolutionLabel.setLayoutX(10*WR);
        resolutionLabel.setTextFill(Color.rgb(88, 38, 24));
        resolutionLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        AtomicReference<Byte> RESOLUTION_ID = new AtomicReference<>(getResolutionID());
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Fullscreen", "1280x720", "1600x900", "1920x1080", "2560x1440", "3840x2160");
        comboBox.getSelectionModel().select(RESOLUTION_ID.get());
        comboBox.setStyle("-fx-border-width:0.5px; -fx-border-color: grey; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 15px; -fx-padding: 0px; -fx-font-weight: 700;");

        comboBox.setOnAction(e -> {
            JSONWriter resolutionModifier = new JSONWriter("data.json");
            RESOLUTION_ID.set((byte) (comboBox.getSelectionModel().getSelectedIndex()));
            resolutionModifier.set("resolution", String.valueOf(RESOLUTION_ID.get()));
        });

        resolution = new HBox();
        resolution.setLayoutX(100*WR);
        resolution.setLayoutY(150*HR);
        resolution.setSpacing(54*WR);
        resolution.getChildren().addAll(resolutionLabel,comboBox);
    }

    /**
     * Set the <code>Label</code> and the <code>TextField</code> for the <code>MusicVolume</code>
     */
    private void setMusicVolume(){
        Label musicLabel = new Label("Music :");
        musicLabel.setTextFill(Color.rgb(88, 38, 24));
        musicLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        Slider sliderP = new Slider(0, 100, Double.parseDouble(starterMusicVolume)*100);
        sliderP.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                try {
                    JSONWriter writer = new JSONWriter("data.json");
                    writer.set("music", Double.toString(sliderP.getValue()/100));
                    audioPlayer.setVolume((double)sliderP.getValue()/100);
                } catch (NumberFormatException e1){
                    //Volume isn't between 0 and 1
                    AlertBox.display("Error", e1.getMessage());
                }
            }
        });
        sliderP.setShowTickMarks(true);
        sliderP.setShowTickLabels(true);
        sliderP.setMajorTickUnit(25);
        sliderP.setBlockIncrement(1);

        musicVolume = new HBox();
        musicVolume.setLayoutX(100*WR);
        musicVolume.setLayoutY(250*HR);
        musicVolume.setSpacing(105*WR);
        musicVolume.getChildren().addAll(musicLabel, sliderP);
    }

    /**
     * Set the <code>Label</code> and the <code>TextField</code> for the <code>EffectVolume</code>
     */
    private void setEffectVolume(){
        Label effectLabel = new Label("Sound effect :");
        effectLabel.setTextFill(Color.rgb(88, 38, 24));
        effectLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        Slider sliderS = new Slider(0, 100, Double.parseDouble(starterEffectVolume)*100);
        sliderS.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                try {
                    JSONWriter writer = new JSONWriter("data.json");
                    writer.set("effect", Double.toString(sliderS.getValue()/100));
                    effectPlayer.setVolume((double)sliderS.getValue()/100);
                } catch (NumberFormatException e1){
                    //Volume isn't between 0 and 1
                    AlertBox.display("Error", e1.getMessage());
                }
            }
        });
        sliderS.setShowTickMarks(true);
        sliderS.setShowTickLabels(true);
        sliderS.setMajorTickUnit(25);
        sliderS.setBlockIncrement(1);

        effectVolume = new HBox();
        effectVolume.setLayoutX(100*HR);
        effectVolume.setLayoutY(350*WR);
        effectVolume.setSpacing(30*WR);
        effectVolume.getChildren().addAll(effectLabel, sliderS);
    }

    private void setControl() {
        Label upLabel = new Label("Move upwards :");
        upLabel.setTextFill(Color.rgb(88, 38, 24));
        upLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label downLabel = new Label("Move downwards :");
        downLabel.setTextFill(Color.rgb(88, 38, 24));
        downLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label rightLabel = new Label("Move rightwards :");
        rightLabel.setTextFill(Color.rgb(88, 38, 24));
        rightLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label leftLabel = new Label("Move leftwards :");
        leftLabel.setTextFill(Color.rgb(88, 38, 24));
        leftLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        Label restartLabel = new Label("Restart game :");
        restartLabel.setTextFill(Color.rgb(88, 38, 24));
        restartLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label loadLabel = new Label("Load save :");
        loadLabel.setTextFill(Color.rgb(88, 38, 24));
        loadLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label saveLabel = new Label("Save progress :");
        saveLabel.setTextFill(Color.rgb(88, 38, 24));
        saveLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label openConsLabel = new Label("Open Console :");
        openConsLabel.setTextFill(Color.rgb(88, 38, 24));
        openConsLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label closeConsLabel = new Label("Close Console :");
        closeConsLabel.setTextFill(Color.rgb(88, 38, 24));
        closeConsLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label autoPromptLabel = new Label("Automatically prompt for rating :");
        autoPromptLabel.setTextFill(Color.rgb(88, 38, 24));
        autoPromptLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        CheckBox promptBox = new CheckBox();
        promptBox.setPrefSize(50, 50);
        promptBox.setSelected(true);
        promptBox.setOnAction(e -> {
            autoPromptRating = !autoPromptRating;
            System.out.println(autoPromptRating);
        });

        this.upControl = new TextField (this.getJsonControl("up"));
        textFieldProperty(upControl);
        this.upControl.setOnKeyPressed(event -> control(upControl, "up", event));

        this.downControl = new TextField (this.getJsonControl("down"));
        textFieldProperty(downControl);
        this.downControl.setOnKeyPressed(event -> control(downControl, "down", event));

        this.rightControl = new TextField (this.getJsonControl("right"));
        textFieldProperty(rightControl);
        this.rightControl.setOnKeyPressed(event -> control(rightControl, "right", event));

        this.leftControl = new TextField (this.getJsonControl("left"));
        textFieldProperty(leftControl);
        this.leftControl.setOnKeyPressed(event -> control(leftControl, "left", event));

        this.restartControl = new TextField (this.getJsonControl("restart"));
        textFieldProperty(restartControl);
        this.restartControl.setOnKeyPressed(event -> control(restartControl, "restart", event));

        this.loadControl = new TextField (this.getJsonControl("loadsave"));
        textFieldProperty(loadControl);
        this.loadControl.setOnKeyPressed(event -> control(loadControl, "loadsave", event));

        this.saveControl = new TextField (this.getJsonControl("savegame"));
        textFieldProperty(saveControl);
        this.saveControl.setOnKeyPressed(event -> control(saveControl, "savegame", event));

        this.openConsControl = new TextField (this.getJsonControl("closeconsole"));
        textFieldProperty(openConsControl);
        this.openConsControl.setOnKeyPressed(event -> control(openConsControl, "closeconsole", event));

        this.closeConsControl = new TextField (this.getJsonControl("openconsole"));
        textFieldProperty(closeConsControl);
        this.closeConsControl.setOnKeyPressed(event -> control(closeConsControl, "openconsole", event));

        this.upHBox = new HBox ();
        upHBox.setLayoutX(700*WR);
        upHBox.setLayoutY(150*HR);
        upHBox.setSpacing((45+keybindOffset)*WR);
        upHBox.getChildren().addAll(upLabel, upControl);
        this.downHBox = new HBox ();
        downHBox.setLayoutX(700*WR);
        downHBox.setLayoutY(200*HR);
        downHBox.setSpacing((10+keybindOffset)*WR);
        downHBox.getChildren().addAll(downLabel, downControl);
        this.rightHBox = new HBox ();
        rightHBox.setLayoutX(700*WR);
        rightHBox.setLayoutY(250*HR);
        rightHBox.setSpacing((18+keybindOffset)*WR);
        rightHBox.getChildren().addAll(rightLabel, rightControl);
        this.leftHBox = new HBox ();
        leftHBox.setLayoutX(700*WR);
        leftHBox.setLayoutY(300*HR);
        leftHBox.setSpacing((36+keybindOffset)*WR);
        leftHBox.getChildren().addAll(leftLabel, leftControl);

        this.restartHBox = new HBox ();
        restartHBox.setLayoutX(700*WR);
        restartHBox.setLayoutY(375*HR);
        restartHBox.setSpacing((62+keybindOffset)*WR);
        restartHBox.getChildren().addAll(restartLabel, restartControl);
        this.loadHBox = new HBox ();
        loadHBox.setLayoutX(700*WR);
        loadHBox.setLayoutY(425*HR);
        loadHBox.setSpacing((103+keybindOffset)*WR);
        loadHBox.getChildren().addAll(loadLabel, loadControl);
        this.saveHBox = new HBox ();
        saveHBox.setLayoutX(700*WR);
        saveHBox.setLayoutY(475*HR);
        saveHBox.setSpacing((55+keybindOffset)*WR);
        saveHBox.getChildren().addAll(saveLabel, saveControl);
        this.openConsHBox = new HBox ();
        openConsHBox.setLayoutX(700*WR);
        openConsHBox.setLayoutY(525*HR);
        openConsHBox.setSpacing((53+keybindOffset)*WR);
        openConsHBox.getChildren().addAll(openConsLabel, openConsControl);
        this.closeConsHBox = new HBox ();
        closeConsHBox.setLayoutX(700*WR);
        closeConsHBox.setLayoutY(575*HR);
        closeConsHBox.setSpacing((54+keybindOffset)*WR);
        closeConsHBox.getChildren().addAll(closeConsLabel, closeConsControl);
            
        this.autoPromptHBox = new HBox();
        autoPromptHBox.setLayoutX(700*WR);
        autoPromptHBox.setLayoutY(750*HR);
        autoPromptHBox.setSpacing((54+keybindOffset)*WR);
        autoPromptHBox.getChildren().addAll(autoPromptLabel, promptBox);
    }

    private void setJsonControl(String text, String touch) {
        JSONWriter writer = new JSONWriter("control.json");
        writer.set(text, touch);
    }

    private String getJsonControl(String text) {
        JSONReader reader = new JSONReader("control.json");
        return reader.getString(text);
    }

    private void control(TextField textField, String text, KeyEvent event) {
        if((event.getText().toUpperCase()).equals(" ") || (event.getText().toUpperCase()).equals("")) {
            textField.clear();
            textField.setText(event.getCode().toString());
            setJsonControl(text, event.getCode().toString());
        }else {
            textField.clear();
            textField.setText(event.getText().toUpperCase());
            setJsonControl(text, event.getCode().toString());
        }
    }

    private void textFieldProperty(TextField textField) {
        textField.setPrefWidth(55*WR);
        textField.setEditable(false);
        textField.setStyle("-fx-border-width:0.5px; -fx-border-color: grey; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 15px; -fx-padding: 0px; -fx-font-weight: 700; -fx-focus-color: red; -fx-alignment: center;"); //     -fx-focus-color: red; -fx-alignment: center; -fx-text-fill: black; -fx-opacity: 0.40;");
    }

    /**
     * Return the resolution of the <code>HBox</code>
     * @return The Resolution <code>HBox</code> containing the <code>Label</code> and the <code>ComboBox</code>
     */
    public HBox getResolution(){
        return resolution;
    }

    /**
     * MusicVolume HBox accessor
     * @return The MusicVolume HBox with the label and the TextField
     */
    public HBox getMusicVolume(){
        return musicVolume;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getEffectVolume(){
        return effectVolume;
    }

    /**
     * Return the resolution of the <code>HBox</code>
     * @return The Resolution <code>HBox</code> containing the <code>Label</code> and the <code>ComboBox</code>
     */
    public HBox getUpControl(){
        return upHBox;
    }

    /**
     * MusicVolume HBox accessor
     * @return The MusicVolume HBox with the label and the TextField
     */
    public HBox getDownControl(){
        return downHBox;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getRightControl(){
        return rightHBox;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getLeftControl(){
        return leftHBox;
    }

    /**
     * Return the resolution of the <code>HBox</code>
     * @return The Resolution <code>HBox</code> containing the <code>Label</code> and the <code>ComboBox</code>
     */
    public HBox getRestartControl(){
        return restartHBox;
    }

    /**
     * MusicVolume HBox accessor
     * @return The MusicVolume HBox with the label and the TextField
     */
    public HBox getLoadControl(){
        return loadHBox;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getSaveControl(){
        return saveHBox;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getOpenConsControl(){
        return openConsHBox;
    }

    /**
     * EffectVolume <code>HBox</code> accessor
     * @return The EffectVolume <code>HBox</code> with the <code>Label</code> and the <code>TextField</code>
     */
    public HBox getCloseConsControl(){
        return closeConsHBox;
    }

    /**
     * StarterMusicVolume accessor
     * @return The value of the musicVolume when the game opened
     */
    public String getStarterMusicVolume(){
        return starterMusicVolume;
    }

    /**
     * StarterEffectVolume accessor
     * @return The value of the Effect Volume when the game opened
     */
    public String getStarterEffectVolume(){
        return starterEffectVolume;
    }

    /**
     * BackButtonOptions accessor
     * @return The BackButtonOptions
     */
    public CustomButton getBackButtonOptions(){
        return backButtonOptions;
    }

    /**
     * AutoPromptHBox accessor
     * @return The AutoPromptHBox
     */
    public HBox getAutoPromptHBox() {
        return autoPromptHBox;
    }
        
     /**
     * AutoPromptRating accessor
     * @return The AutoPromptRating value
     */
    public static boolean doAutoPromptRating() {
        return autoPromptRating;
    }
}
