package view;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import model.JSONWriter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class OptionsMenu
        extends Menu {

    ComboBox<String> comboBox;

    public OptionsMenu(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
        this.setComboBox();
    }

    public OptionsMenu(Parent parent_, double width_, double height_, float WR, float HR, CustomImage background_)
            throws IOException, ParseException {
        super(parent_, width_, height_, WR, HR);
        this.background = background_;
        this.setComboBox();
    }

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

    public ComboBox<String> getComboBox() {
        return this.comboBox;
    }
}
