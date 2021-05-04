package view;

import presenter.Main;
import model.*;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * An <code>CreatorMenu</code> is a user interface used to display all the settings and modifying them.
 * It contains a list of available resolutions and allows the user to change the resolution. The game must be
 * restarted in order to apply this change.
 */
public class CreatorMenu
        extends Menu {

    private final Pane leftMenu, middleMenu, rightMenu;
    private CustomImage leftMenuImage, rightMenuImage;
    private VBox gamePane, rightVBox;
    private CustomImage middleMenuBackground;
    private CustomButton saveButton, newMapButton, mainMenuButton;
    private Pane finalPane;

    private Block[][] gameBoard;
	private Block item;

    private int numberX = 10;
    private int numberY = 10;

    private TextField sizeXField;
    private TextField sizeYField;
    private TextField levelNameField;

	private double width;
	private double height;

    /**
     * Create a new <code>CreatorMenu</code> and pepare the attributes and the resolution list and choice.
     * @param parent_ The main <code>Pane</code> that we will be using to store the content. This pane should (but it's not
     *                mandatory) be the size of the window in order to be able to display content anywhere on
     *                the said window.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public CreatorMenu(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);
        this.width = width_;
        this.height = height_;

		this.leftMenu = new Pane();
        this.rightMenu = new Pane();
		
		if (Main.fullscreen) {
            this.leftMenuImage = new CustomImage(0, 0, WR, HR, "side menu perfect fit.png");
            this.rightMenuImage = new CustomImage(0, 0, WR, HR, "side menu perfect fit.png");
        } else {
            this.leftMenuImage = new CustomImage(0, 0, WR, HR, "left side menu.png");
            this.rightMenuImage = new CustomImage(0, 0, WR, HR, "right side menu.png");
        }

        this.middleMenu = new Pane();
        this.middleMenuBackground = new CustomImage(0, 0, WR, HR, "background empty.png");
        this.leftMenu.getChildren().add(this.middleMenuBackground);

        this.setPaneSizes();
        this.addBlockRightMenu();
        this.prepareInterfaces();
        this.prepareTextField();

        this.gamePane = new VBox();
        this.gamePane.setLayoutX(100*WR);
        this.gamePane.setLayoutY(25*HR);

        this.finalPane = new Pane();
        this.finalPane.setLayoutX(0);
        this.finalPane.setLayoutY(0);
        this.finalPane.setPrefWidth(ORIGINAL_WIDTH);
        this.finalPane.setMinWidth(ORIGINAL_WIDTH);
        this.finalPane.setMaxWidth(ORIGINAL_WIDTH);
        this.finalPane.setPrefHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMinHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMaxHeight(ORIGINAL_HEIGHT);

        this.leftMenu.getChildren().add(this.leftMenuImage);
        this.leftMenu.getChildren().addAll(
                this.levelNameField,
                this.sizeXField, this.sizeYField,
                this.saveButton, this.saveButton.overlay,
                this.newMapButton, this.newMapButton.overlay,
                this.mainMenuButton, this.mainMenuButton.overlay
        );

        this.rightMenu.getChildren().addAll(this.rightMenuImage, this.rightVBox);
        this.middleMenu.getChildren().add(this.gamePane);
        this.finalPane.getChildren().addAll(this.leftMenu, this.middleMenu, this.rightMenu);

    }

    /**
     * Set the sizes for the left, middle and right <code>Panes</code>.
     */
    private void setPaneSizes() {
        this.setLeftPaneSize();
        this.setMiddlePaneSize();
        this.setRightPaneSize();
    }

    /**
     * Set the size for the left <code>Pane</code>.
     */
    private void setLeftPaneSize() {
        this.leftMenu.setPrefWidth(350 * WR);
        this.leftMenu.setMaxWidth(350 * WR);
        this.leftMenu.setMinWidth(350 * WR);
        this.leftMenu.setPrefHeight(this.height);
        this.leftMenu.setMaxHeight(this.height);
        this.leftMenu.setMinHeight(this.height);
        this.leftMenu.setLayoutX(0);
        this.leftMenu.setLayoutY(0);
    }

    /**
     * Set the size for the middle <code>Pane</code>.
     */
    private void setMiddlePaneSize() {
        this.middleMenu.setPrefWidth(1220 * WR);
        this.middleMenu.setMaxWidth(1220 * WR);
        this.middleMenu.setMinWidth(1220 * WR);
        this.middleMenu.setPrefHeight(this.height);
        this.middleMenu.setMaxHeight(this.height);
        this.middleMenu.setMinHeight(this.height);
        this.middleMenu.setLayoutX(350 * WR);
        this.middleMenu.setLayoutY(0);
    }

    /**
     * Set the size for the right <code>Pane</code>.
     */
    private void setRightPaneSize() {
        this.rightMenu.setPrefWidth(350 * WR);
        this.rightMenu.setMaxWidth(350 * WR);
        this.rightMenu.setMinWidth(350 * WR);
        this.rightMenu.setPrefHeight(this.height);
        this.rightMenu.setMaxHeight(this.height);
        this.rightMenu.setMinHeight(this.height);
        this.rightMenu.setLayoutX((350 + 1220) * WR);
        this.rightMenu.setLayoutY(0);
    }

    /**
     * Prepare every image and button displaying information in the left and right <code>Panes</code>.
     */
    private void prepareInterfaces() {
        this.prepareSaveButton();
        this.prepareSaveButtonAction();
        this.prepareNewMapButton();
        this.prepareNewMapButtonAction();
        this.prepareMainMenuButton();
    }

    /**
     * Prepare the "Save" button to save a move.
     */
    private void prepareSaveButton() {
        this.saveButton = new CustomButton(65, 850, WR, HR, "save.png", (byte) 0);
    }

    /**
     * Prepare the <code>EventHandler</code> used with the "Save" button.
     */
    private void prepareSaveButtonAction() {
        this.saveButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(gameBoard != null) {
                int nPlayer = 0;
                int nBox = 0;
                int nGoal = 0;
                ArrayList<String> content = new ArrayList<>();
                for(Block[] line : gameBoard) {
                    String line_ = "";
                    for(Block elem : line) {
                        if(elem instanceof Wall) {
                            line_ += "#";
                        }else if(elem instanceof Player && !(elem.amIOnGoal())) {
                            line_ += "@";
                            nPlayer++;
                        }else if(elem instanceof Player && elem.amIOnGoal()) {
                            line_ += "+";
                            nPlayer++;
                            nGoal++;
                        }else if(elem instanceof Box && !(elem.amIOnGoal())) {
                            line_ += "$";
                            nBox++;
                        }else if(elem instanceof Box && elem.amIOnGoal()) {
                            line_ += "*";
                            nBox++;
                            nGoal++;
                        }else if(elem instanceof Goal) {
                            line_ += ".";
                            nGoal++;
                        }else if(elem == null) {
                            line_ += " ";
                        }
                    }
                    content.add(line_);
                }
                if(this.valideMapSave(nPlayer, nBox, nGoal)) {
                    Fichier.saveFile(getLevelName(), "freePlay", content);
                }else{
                    AlertBox.display("Error", "La carte n'est pas valide");
                }
            }else {
                AlertBox.display("Error", "Il n'y a pas de carte");
            }
        });
    }

    private boolean valideMapSave(int nPlayer, int nBox, int nGoal) {
        return nPlayer != 0 && nPlayer <= 1 && nGoal != 0 && nBox == nGoal;
    }

    private String getLevelName() {
        return levelNameField.getText().concat(".xsb");
    }

    /**
     * Prepare the "NewMap" button to undo a move.
     */
    private void prepareNewMapButton() {
        this.newMapButton = new CustomButton(65, 800, WR, HR, "newMap.png", (byte) 0);
    }

    /**
     * Prepare the <code>EventHandler</code> used with the "Restart" button.
     */
    private void prepareNewMapButtonAction() {
        this.newMapButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.gameBoard = new Block[numberX][numberY];
            this.gamePane.getChildren().removeAll(this.gamePane.getChildren());
            emptyMap();
        });
    }

    /**
     * Prepare the "Main menu" button to go back to the main menu.
     */
    private void prepareMainMenuButton() {
        this.mainMenuButton = new CustomButton(65, 900, WR, HR, "main menu.png", (byte) 1);
    }

    /**
     *
     *
     */
    private void prepareTextField() {
        this.levelNameField = new TextField();
        this.levelNameField.setPromptText("Level name...");
        this.levelNameField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.levelNameField.setPrefWidth(200*WR);
        this.levelNameField.setLayoutX(75*WR);
        this.levelNameField.setLayoutY(550*HR);

        this.sizeXField = new TextField();
        this.sizeXField.setPromptText("Length...");
        this.sizeXField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.sizeXField.setPrefWidth(100*WR);
        this.sizeXField.setLayoutX(125*WR);
        this.sizeXField.setLayoutY(600*HR);
        this.sizeXField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideEnter(sizeXField.getText())) {
                this.numberX = Integer.parseInt(sizeXField.getText());
            }else {
                this.numberX = 10;
            }
        });

        this.sizeYField = new TextField();
        this.sizeYField.setPromptText("Height...");
        this.sizeYField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.sizeYField.setPrefWidth(100*WR);
        this.sizeYField.setLayoutX(125*WR);
        this.sizeYField.setLayoutY(650*HR);
        this.sizeYField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(valideEnter(sizeYField.getText())) {
                this.numberY = Integer.parseInt(sizeYField.getText());
            }else {
                this.numberY = 10;
            }
        });
    }

    private boolean valideEnter(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    private void addBlockRightMenu() {
    	this.rightVBox = new VBox();
        this.rightVBox.setLayoutX(125*WR);
        this.rightVBox.setLayoutY(50*HR);
		
        for(int i=0; i < 7; i++) {
        	drawItemLevel(this.rightVBox, i);
        }
    }

    private void drawItemLevel(VBox vBox, int i) {
		Rectangle rect = new Rectangle(110*WR, 110*HR);
        Text nameElemText = new Text();
        switch (i) {
            case 0:
                nameElemText.setText("Block Air");
                setActionItem(rect, null);
                break;
            case 1:
                nameElemText.setText("Block Wall");
                setActionItem(rect, new Wall(0, 0));
                break;
        	case 2:
        	    nameElemText.setText("Block Player");
        	    setActionItem(rect, new Player(0, 0, false, null));
        	    break;
            case 3:
                nameElemText.setText("Block Player on Goal");
                setActionItem(rect, new Player(0, 0, true, null));
                break;
        	case 4:
        	    nameElemText.setText("Block Box");
        	    setActionItem(rect, new Box(0, 0, false));
        	    break;
        	case 5:
        	    nameElemText.setText("Block Box on Goal");
        	    setActionItem(rect, new Box(0, 0, true));
        	    break;
        	case 6:
        	    nameElemText.setText("Block Goal");
        	    setActionItem(rect, new Goal(0, 0));
        	    break;
        	}
        vBox.getChildren().addAll(nameElemText, rect);
	}

    private void emptyMap() {
        for(int y=0; y < numberY; y++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            for(int x=0; x < numberX; x++) {
                MapEditor mapEdit = new MapEditor(this.gameBoard, x, y, numberX, numberY, this.width-50, this.height-50, WR, HR);

                mapEdit.getElem().setOnMouseEntered(e -> {
                    mapEdit.getElem().setStyle("-fx-stroke: rgb(140, 55, 40); -fx-stroke-width: 2;");
                });
                mapEdit.getElem().setOnMouseExited(e -> {
                    mapEdit.getElem().setStyle("");
                });
                mapEdit.getElem().setOnMouseClicked(e -> {
                    mapEdit.setObjet(this.gameBoard, item);
                });

                hBox.getChildren().add(mapEdit.getElem());
            }
            this.gamePane.getChildren().add(hBox);
        }
	}

    private void setActionItem(Rectangle rect, Block objet) {
        try {
            ImagePattern modelImage = new ImagePattern(new Image(new FileInputStream("src\\main\\resources\\img\\" + (objet == null ? "air.png" : objet.getImage()))));
            rect.setFill(modelImage);
            rect.setOnMouseEntered(e -> {
                rect.setStyle("-fx-stroke: rgb(140, 55, 40); -fx-stroke-width: 2;");
            });
            rect.setOnMouseExited(e -> {
                rect.setStyle("");
            });
            rect.setOnMouseClicked(e -> {
                item = objet;
            });
        } catch (FileNotFoundException e) {
            AlertBox.display("Fatal error", "A .json file could not be found. Check if no file is missing." +
                    "Check if the names have not been changed or if any file has not been deleted. " +
                    "You can run the FileIntegrity checker for further information. Missing file : " + (objet == null ? "air.png" : objet.getImage()) + ".");
            System.exit(-1);
        }
    }

    /**
     * Return the currently used main menu button.
     * @return The currently used main menu button
     */
    public CustomButton getMainMenuButton() {
        return this.mainMenuButton;
    }
	
	/**
     * Return the currently used final <code>Pane</code> containing the other three main <code>Panes</code>
     * (Left, Middle and Right <code>Panes</code>).
     * @return The currently used final <code>Pane</code>
     */
    public Pane getFinalPane() {
        return this.finalPane;
    }
}