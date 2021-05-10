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
 * A <code>CreatorMenu</code> is a user interface used to let the user create is own level.
 * It has 3 menus : The left menu is used to change the settings of the map, save it,...
 *                  The right menu is a VBox with all the block of the game where the user can select the block he
 *   wants to add.
 *                  The middle menu is a square of MapEditor. If we click on one MapEditor, it changes with the block
 *   the user choose on the right menu.
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
    private Text numbX, numbY;

	private double width;
	private double height;

    /**
     * Create a new <code>CreatorMenu</code> and prepare the attributes and the resolution list and choice.
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
        this.gamePane.setLayoutX(25*WR);
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
                this.numbX, this.sizeXField,
                this.numbY, this.sizeYField,
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
     * Prepare the "Save" button to save the new level.
     */
    private void prepareSaveButton() {
        this.saveButton = new CustomButton(65, 850, WR, HR, "save.png", (byte) 1);
    }

    /**
     * Prepare the <code>EventHandler</code> used with the "Save" button.
     */
    private void prepareSaveButtonAction() {
        this.saveButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(gameBoard != null) {
                ArrayList<String> content = new ArrayList<>();
                for(Block[] line : gameBoard) {
                    String line_ = "";
                    for(Block elem : line) {
                        if (elem == null){
                            line_ += " ";
                        } else {
                            line_ += elem.getTexture();
                        }
                    }
                    content.add(line_);
                }
                if (getLevelName().equals(".xsb")){
                    File.saveFile("EmptyName.xsb", "freePlay", content);
                } else {
                    File.saveFile(getLevelName(), "freePlay", content);
                }
            }else {
                AlertBox.display("Error", "There's no map loaded");
            }
        });
    }

    private String getLevelName() {
        return levelNameField.getText().concat(".xsb");
    }

    /**
     * Prepare the "NewMap" button to undo a move.
     */
    private void prepareNewMapButton() {
        this.newMapButton = new CustomButton(65, 800, WR, HR, "newMap.png", (byte) 1);
    }

    /**
     * Prepare the <code>EventHandler</code> used with the "NewMap" button.
     */
    private void prepareNewMapButtonAction() {
        this.newMapButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try{
                this.numberX = Integer.parseInt(sizeXField.getText());
                this.numberY = Integer.parseInt(sizeYField.getText());
            } catch (NumberFormatException exc) {
                exc.printStackTrace();
                this.numberX = 10;
                this.numberY = 10;
            }
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
     * Prepare all the <Code>TextFields</Code> in the leftMenu
     * (levelName - width - height)
     */
    private void prepareTextField() {
        this.numbX = new Text();
        this.numbX.setText("Width :");
        this.numbX.setLayoutX(55*WR);
        this.numbX.setLayoutY(625*HR);
        styleText(numbX);
        this.numbY = new Text();
        this.numbY.setLayoutX(55*WR);
        this.numbY.setLayoutY(675*HR);
        styleText(numbY);
        this.numbY.setText("Height :");

        this.levelNameField = new TextField("Name Level");
        this.levelNameField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.levelNameField.setPrefWidth(200*WR);
        this.levelNameField.setLayoutX(75*WR);
        this.levelNameField.setLayoutY(550*HR);
        this.levelNameField.setStyle("-fx-border-width:0.5px; -fx-border-color: grey; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 20px; -fx-padding: 0px; -fx-font-weight: 700; -fx-alignment: center;");

        this.sizeXField = new TextField("10");
        this.sizeXField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.sizeXField.setPrefWidth(100*WR);
        this.sizeXField.setLayoutX(200*WR);
        this.sizeXField.setLayoutY(600*HR);
        this.sizeXField.setStyle("-fx-border-width:0.5px; -fx-border-color: grey; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 30px; -fx-padding: 0px; -fx-font-weight: 700; -fx-alignment: center;");

        this.sizeYField = new TextField("10");
        this.sizeYField.setFont(new Font("Microsoft YaHei", 20 * WR));
        this.sizeYField.setPrefWidth(100*WR);
        this.sizeYField.setLayoutX(200*WR);
        this.sizeYField.setLayoutY(650*HR);
        this.sizeYField.setStyle("-fx-border-width:0.5px; -fx-border-color: grey; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 30px; -fx-padding: 0px; -fx-font-weight: 700; -fx-alignment: center;");
    }

    /**
     * add all the blocks in the right menu
     */
    private void addBlockRightMenu() {
    	this.rightVBox = new VBox();
        this.rightVBox.setLayoutX(75*WR);
        this.rightVBox.setLayoutY(50*HR);
		
        for(int i=0; i < 8; i++) {
        	drawItemLevel(this.rightVBox, i);
        }
    }

    /**
     * draw all the items in the rightMenu
     * @param vBox The instance of the rightMenu
     * @param i The number of item we have already drawn
     */
    private void drawItemLevel(VBox vBox, int i) {
        HBox itemHBox = new HBox();
		Rectangle rect = new Rectangle(110*WR, 110*HR);
        Text nameElemText = new Text();
        styleText(nameElemText);
        switch (i) {
            case 0:
                nameElemText.setText("Air");
                setActionItem(rect, null);
                break;
            case 1:
                nameElemText.setText("Wall");
                setActionItem(rect, new Wall(0, 0));
                break;
        	case 2:
        	    nameElemText.setText("Player");
        	    setActionItem(rect, new Player(0, 0, false, null));
        	    break;
            case 3:
                nameElemText.setText("Player on Goal");
                setActionItem(rect, new Player(0, 0, true, null));
                break;
        	case 4:
        	    nameElemText.setText("Box");
        	    setActionItem(rect, new Box(0, 0, false));
        	    break;
        	case 5:
        	    nameElemText.setText("Box on Goal");
        	    setActionItem(rect, new Box(0, 0, true));
        	    break;
        	case 6:
        	    nameElemText.setText("Goal");
        	    setActionItem(rect, new Goal(0, 0));
        	    break;
            case 7:
                nameElemText.setText("Teleport");
                setActionItem(rect, new Teleport(0, 0,null));
                break;
        	}
        itemHBox.getChildren().addAll(rect, nameElemText);
        vBox.getChildren().add(itemHBox);
	}

    private void styleText(Text text) {
        text.setStyle("-fx-font-size: 13px; -fx-stroke: rgb(255, 249, 242); -fx-stroke-width: 1;");
    }

    /**
     * Set the middleMenu with the square of MapEditor
     */
    private void emptyMap() {
        for(int y=0; y < numberY; y++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            for(int x=0; x < numberX; x++) {
                MapEditor mapEdit = new MapEditor(this.gameBoard, x, y, numberX, numberY, 1320*WR, height-(100*WR), WR, HR);

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

    /**
     * Set the action to select the block on the rightMenu
     * @param rect The rectangle in the rightMenu
     * @param objet The object in the rightMenu
     */
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