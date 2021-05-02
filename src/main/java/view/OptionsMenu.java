package view;

import presenter.Main;
import model.JSONReader;
import model.JSONWriter;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Slider;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import java.io.IOException;
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
    private HBox upHBox, downHBox, rightHBox, leftHBox, restartHBox, trucHBox, saveHBox, openConsHBox, closeConsHBox;
    private TextField upControl, downControl, rightControl, leftControl;
    private TextField restartControl, trucControl, saveControl, openConsControl, closeConsControl;

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
     * @throws IOException Exception thrown when a provided file name doesn't match any file (during the <code>setComboBox()</code> method)
     * @throws ParseException Exception thrown if the .json file could not be parsed (during the <code>setComboBox()</code> method)
     */
    public OptionsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_, AudioPlayer audioPlayer_, AudioPlayer effectPlayer_)
            throws IOException, ParseException{
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
    private void prepareVolume(){
        try {
            JSONReader reader = new JSONReader("data.json");
            starterMusicVolume = reader.getString("music");
            starterEffectVolume = reader.getString("effect");
        } catch (IOException | ParseException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Create and prepare the <code>ComboBox</code> object that will display the available resolutions and
     * allow the user to change the resolution.
     * @throws IOException Exception thrown when a provided file name doesn't match any file
     * @throws ParseException Exception thrown if the .json file could not be parsed
     */
    public void setComboBox() throws IOException, ParseException{
        Label resolutionLabel = new Label("Resolution :");
        resolutionLabel.setLayoutX(10*WR);
        resolutionLabel.setTextFill(Color.rgb(88, 38, 24));
        resolutionLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        AtomicReference<Byte> RESOLUTION_ID = new AtomicReference<>(getResolutionID());
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Native resolution", "1280x720", "1600x900", "1920x1080", "2560x1440", "3840x2160");
        comboBox.getSelectionModel().select(RESOLUTION_ID.get());

        comboBox.setOnAction(e -> {
            try {
                JSONWriter resolutionModifier = new JSONWriter("data.json");
                RESOLUTION_ID.set((byte) (comboBox.getSelectionModel().getSelectedIndex()));
                resolutionModifier.set("resolution", String.valueOf(RESOLUTION_ID.get()));
            } catch (IOException | ParseException ioException) {
                AlertBox.display("Error", "Error occurred while trying to save the Resolution" +
                        ioException.getMessage());
            }
        });

        resolution = new HBox();
        resolution.setLayoutX(100*WR);
        resolution.setLayoutY(150*HR);
        resolution.setSpacing(10);
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
                }catch (IOException | ParseException exc) {
                    AlertBox.display("Error", "An error occured while writing in the JSON file\n" +
                            exc.getMessage());
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
        musicVolume.setSpacing(10);
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
                }catch (IOException | ParseException exc) {
                    AlertBox.display("Error", "An error occured while writing in the JSON file\n" +
                            exc.getMessage());
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
        effectVolume.setSpacing(10);
        effectVolume.getChildren().addAll(effectLabel, sliderS);
    }

    private void setControl() {
        Label upLabel = new Label("Touch Up :");
        upLabel.setTextFill(Color.rgb(88, 38, 24));
        upLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label downLabel = new Label("Touch Down :");
        downLabel.setTextFill(Color.rgb(88, 38, 24));
        downLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label rightLabel = new Label("Touch Right :");
        rightLabel.setTextFill(Color.rgb(88, 38, 24));
        rightLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label leftLabel = new Label("Touch Left :");
        leftLabel.setTextFill(Color.rgb(88, 38, 24));
        leftLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        Label restartLabel = new Label("Touch Restart :");
        restartLabel.setTextFill(Color.rgb(88, 38, 24));
        restartLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label trucLabel = new Label("Touch Truc :");
        trucLabel.setTextFill(Color.rgb(88, 38, 24));
        trucLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label saveLabel = new Label("Touch Save :");
        saveLabel.setTextFill(Color.rgb(88, 38, 24));
        saveLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label openConsLabel = new Label("Touch Open-Console :");
        openConsLabel.setTextFill(Color.rgb(88, 38, 24));
        openConsLabel.setFont(new Font("Microsoft YaHei", 25 * WR));
        Label closeConsLabel = new Label("Touch Close-Console :");
        closeConsLabel.setTextFill(Color.rgb(88, 38, 24));
        closeConsLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        this.upControl = new TextField (this.getControl("upTouch"));
        this.upControl.setPrefWidth(40*WR);
        this.upControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(upControl, "upTouch")) {
                setControl("upTouch", upControl.getText());
            }
        });
        this.downControl = new TextField (this.getControl("downTouch"));
        this.downControl.setPrefWidth(40*WR);
        this.downControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(downControl, "downTouch")) {
                setControl("downTouch", downControl.getText());
            }
        });
        this.rightControl = new TextField (this.getControl("rightTouch"));
        this.rightControl.setPrefWidth(40*WR);
        this.rightControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(rightControl, "rightTouch")) {
                setControl("rightTouch", rightControl.getText());
            }
        });
        this.leftControl = new TextField (this.getControl("leftTouch"));
        this.leftControl.setPrefWidth(40*WR);
        this.leftControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(leftControl, "leftTouch")) {
                setControl("leftTouch", leftControl.getText());
            }
        });

        this.restartControl = new TextField (this.getControl("restartTouch"));
        this.restartControl.setPrefWidth(40*WR);
        this.restartControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(restartControl, "restartTouch")) {
                setControl("restartTouch", restartControl.getText());
            }
        });
        this.trucControl = new TextField (this.getControl("trucTouch"));
        this.trucControl.setPrefWidth(40*WR);
        this.trucControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(trucControl, "trucTouch")) {
                setControl("trucTouch", trucControl.getText());
            }
        });
        this.saveControl = new TextField (this.getControl("saveTouch"));
        this.saveControl.setPrefWidth(40*WR);
        this.saveControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(saveControl, "saveTouch")) {
                setControl("saveTouch", saveControl.getText());
            }
        });
        this.openConsControl = new TextField (this.getControl("consOpenTouch"));
        this.openConsControl.setPrefWidth(40*WR);
        this.openConsControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(openConsControl, "consOpenTouch")) {
                setControl("consOpenTouch", openConsControl.getText());
            }
        });
        this.closeConsControl = new TextField (this.getControl("consCloseleftTouch"));
        this.closeConsControl.setPrefWidth(40*WR);
        this.closeConsControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideControl(closeConsControl, "consCloseleftTouch")) {
                setControl("consCloseleftTouch", closeConsControl.getText());
            }
        });

        this.upHBox = new HBox ();
        upHBox.setLayoutX(700*WR);
        upHBox.setLayoutY(150*HR);
        upHBox.setSpacing(10);
        upHBox.getChildren().addAll(upLabel, upControl);
        this.downHBox = new HBox ();
        downHBox.setLayoutX(700*WR);
        downHBox.setLayoutY(200*HR);
        downHBox.setSpacing(10);
        downHBox.getChildren().addAll(downLabel, downControl);
        this.rightHBox = new HBox ();
        rightHBox.setLayoutX(700*WR);
        rightHBox.setLayoutY(250*HR);
        rightHBox.setSpacing(10);
        rightHBox.getChildren().addAll(rightLabel, rightControl);
        this.leftHBox = new HBox ();
        leftHBox.setLayoutX(700*WR);
        leftHBox.setLayoutY(300*HR);
        leftHBox.setSpacing(10);
        leftHBox.getChildren().addAll(leftLabel, leftControl);

        this.restartHBox = new HBox ();
        restartHBox.setLayoutX(700*WR);
        restartHBox.setLayoutY(375*HR);
        restartHBox.setSpacing(10);
        restartHBox.getChildren().addAll(restartLabel, restartControl);
        this.trucHBox = new HBox ();
        trucHBox.setLayoutX(700*WR);
        trucHBox.setLayoutY(425*HR);
        trucHBox.setSpacing(10);
        trucHBox.getChildren().addAll(trucLabel, trucControl);
        this.saveHBox = new HBox ();
        saveHBox.setLayoutX(700*WR);
        saveHBox.setLayoutY(475*HR);
        saveHBox.setSpacing(10);
        saveHBox.getChildren().addAll(saveLabel, saveControl);
        this.openConsHBox = new HBox ();
        openConsHBox.setLayoutX(700*WR);
        openConsHBox.setLayoutY(525*HR);
        openConsHBox.setSpacing(10);
        openConsHBox.getChildren().addAll(openConsLabel, openConsControl);
        this.closeConsHBox = new HBox ();
        closeConsHBox.setLayoutX(700*WR);
        closeConsHBox.setLayoutY(575*HR);
        closeConsHBox.setSpacing(10);
        closeConsHBox.getChildren().addAll(closeConsLabel, closeConsControl);
    }

    private void setControl(String text, String touch) {
        try {
            JSONWriter writer = new JSONWriter("control.json");
            writer.set(text, touch);
        } catch (IOException | ParseException exc) {
            exc.printStackTrace();
        }
    }

    private String getControl(String text) {
        try {
            JSONReader reader = new JSONReader("control.json");
            return reader.getString(text);
        } catch (IOException | ParseException exc) {
            exc.printStackTrace();
        }
        return "/";
    }

    private boolean valideControl(TextField textField, String text) {
        if(textField.getText().length() > 0) {
            textField.setText(Character.toString(textField.getText().charAt(textField.getText().length()-1)).toUpperCase());
            textField.setStyle("-fx-text-box-border: green; -fx-focus-color: green;");
            checkOtherControl(text, textField);
            return true;
        }else {
            textField.setText("/");
            textField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            //textField.setStyle("-fx-background-color: rgb(200, 30, 30);");
            return false;
        }
    }

    private void checkOtherControl(String text, TextField textField) {
        String[] key = new String[] {"upTouch", "downTouch", "rightTouch", "leftTouch", "restartTouch", "trucTouch", "saveTouch", "consOpenTouch", "consCloseleftTouch"};
        for(String keyName : key) {
            if(!(text.equals(keyName))) {
                if(getControl(keyName).equals(textField.getText())) {
                    //textField
                }
            }
        }
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
    public HBox getTrucControl(){
        return trucHBox;
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
}