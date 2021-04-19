package view;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.JSONReader;
import model.JSONWriter;
import org.json.simple.parser.ParseException;

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
    private TextField musicField;
    private TextField effectField;

    /**
     * Create a new <code>OptionsMenu</code> and pepare the attributes and the resolution list & choice.
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
    public OptionsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR, background_);
        this.backButtonOptions = new CustomButton((int)((width_-480-5)), (int)((height_-96-5)), WR, HR, "back button.png");
        if (!Main.fullscreen) {
            this.backButtonOptions.setLayoutY(-50);
            this.backButtonOptions.overlay.setLayoutY(-50);
        }
        this.background = background_;
        prepareVolume();
        this.setComboBox();
        this.setMusicVolume();
        this.setEffectVolume();
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
    public void setComboBox() throws IOException, ParseException {
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
                ioException.printStackTrace();
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
        Label musicLabel = new Label("Music (0-1) :");
        musicLabel.setTextFill(Color.rgb(88, 38, 24));
        musicLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        musicField = new TextField(starterMusicVolume);
        musicField.setFont(new Font("Microsoft YaHei", 20 * WR));
        musicField.setMaxWidth(60*WR);



        musicVolume = new HBox();
        musicVolume.setLayoutX(100*WR);
        musicVolume.setLayoutY(250*HR);
        musicVolume.setSpacing(10);
        musicVolume.getChildren().addAll(musicLabel, musicField);
    }

    /**
     * Set the <code>Label</code> and the <code>TextField</code> for the <code>EffectVolume</code>
     */
    private void setEffectVolume(){
        Label effectLabel = new Label("Sound effect (0-1) :");
        effectLabel.setTextFill(Color.rgb(88, 38, 24));
        effectLabel.setFont(new Font("Microsoft YaHei", 25 * WR));

        effectField = new TextField(starterEffectVolume);
        effectField.setFont(new Font("Microsoft YaHei", 20 * WR));
        effectField.setMaxWidth(60*WR);



        effectVolume = new HBox();
        effectVolume.setLayoutX(100*HR);
        effectVolume.setLayoutY(350*WR);
        effectVolume.setSpacing(10);
        effectVolume.getChildren().addAll(effectLabel, effectField);
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
     * MusicField accessor
     * @return The MusicField <code>TextField</code> to get the input of the user
     */
    public TextField getMusicField(){
        return musicField;
    }

    /**
     * EffectField accessor
     * @return The MusicField <code>TextField</code> to get the input of the user
     */
    public TextField getEffectField(){
        return effectField;
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