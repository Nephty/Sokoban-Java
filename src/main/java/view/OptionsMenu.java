package view;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
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

    ComboBox<String> comboBox;

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
        this.background = background_;
        this.setComboBox();
    }

    /**
     * Create and prepare the <code>ComboBox</code> object that will display the available resolutions and
     * allow the user to change the resolution.
     * @throws IOException Exception thrown when a provided file name doesn't match any file
     * @throws ParseException Exception thrown if the .json file could not be parsed
     */
    public void setComboBox() throws IOException, ParseException {
        AtomicReference<Byte> RESOLUTION_ID = new AtomicReference<>(getResolutionID());
        this.comboBox = new ComboBox<>();
        this.comboBox.getItems().addAll("Native resolution", "1280x720", "1600x900", "1920x1080", "2560x1440", "3840x2160");
        this.comboBox.getSelectionModel().select(RESOLUTION_ID.get());
        this.comboBox.setLayoutX(500*this.WR);
        this.comboBox.setLayoutY(150*this.HR);

        this.comboBox.setOnAction(e -> {
            try {
                JSONWriter resolutionModifier = new JSONWriter("data.json");
                RESOLUTION_ID.set((byte) (this.comboBox.getSelectionModel().getSelectedIndex()));
                resolutionModifier.set("resolution", String.valueOf(RESOLUTION_ID.get()));
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Return the <code>ComboBox</code> used to display the available resolutions and allowing the user to change
     * the resolution.
     * @return The currently used <c
     */
    public ComboBox<String> getComboBox() {
        return this.comboBox;
    }
}
