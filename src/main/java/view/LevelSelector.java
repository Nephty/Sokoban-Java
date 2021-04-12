package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class LevelSelector
        extends Menu {

	protected Pane middleMenu, rightMenu, levelViewer, selectedLevelViewer;
	protected CustomImage middleMenuBackground, rightMenuImage;
    protected int completedLevels;
    protected byte selectedLevel;
    protected CustomButton backButton, chooseButton, resumeButton, playButton;
    protected boolean hasSelected = false;

    protected Pane finalPane;

    public LevelSelector(Parent parent_, double width_, double height_, float WR, float HR)
            throws IOException, ParseException{
        super(parent_, width_, height_, WR, HR);

        this.rightMenu = new Pane();
        if (Main.fullscreen) {
            this.rightMenuImage = new CustomImage(0,0,WR,HR,"side menu perfect fit.png");
        } else {
            this.rightMenuImage = new CustomImage(0,0,WR,HR,"right side menu.png");
        }

        //Used to show the map when the mouse is over a button.
        this.levelViewer = new Pane();
        this.levelViewer.setLayoutY(350*HR);
        this.levelViewer.setMaxWidth(350*WR);

        //Used to show the level we have selected.
        this.selectedLevelViewer = new Pane();
        this.selectedLevelViewer.setLayoutY(350*HR);
        this.selectedLevelViewer.setMaxWidth(350*WR);
        rightMenu.getChildren().addAll(rightMenuImage, selectedLevelViewer,levelViewer);

        //Back tomain Menu
        this.backButton = new CustomButton(50, (int)((height_-96-5)), WR, HR, "back button.png");

        //Visible when a level is selected and it starts the game
        this.playButton = new CustomButton((int)(width_-900), (int)(height_-96-5), WR, HR, "start button.png");
        this.playButton.setVisible(false);

        //Visible when the player comeback from the playingMenu.
        //It starts the game where the player was.
        this.resumeButton = new CustomButton((int) (width_-900), (int) height_-96-5, WR, HR, "resume button.png");
        this.resumeButton.setVisible(false);

        this.middleMenu = new Pane();
        this.middleMenuBackground = new CustomButton(0,0,WR,HR,"background empty.png");
        this.middleMenu.getChildren().add(this.middleMenuBackground);

        //Pane with all the buttons for the selection
        this.middleMenuBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!this.resumeButton.isVisible() && this.hasSelected) {
                    this.resumeButton.setVisible(true);
                    this.playButton.setVisible(false);
                }
            }
        });
        this.setSelectors();

        if (!Main.fullscreen) {
            this.backButton.setLayoutY(-50);
            this.backButton.overlay.setLayoutY(-50);
            this.playButton.setLayoutY(-50);
            this.playButton.overlay.setLayoutY(-50);
            this.resumeButton.setLayoutY(-50);
            this.resumeButton.overlay.setLayoutY(-50);
        }



        this.setPaneSizes();

        //Global pane of the levelselector
        this.finalPane = new Pane();
        this.finalPane.setLayoutX(0);
        this.finalPane.setLayoutY(0);
        this.finalPane.setPrefWidth(ORIGINAL_WIDTH);
        this.finalPane.setMinWidth(ORIGINAL_WIDTH);
        this.finalPane.setMaxWidth(ORIGINAL_WIDTH);
        this.finalPane.setPrefHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMinHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMaxHeight(ORIGINAL_HEIGHT);


        middleMenu.getChildren().addAll(resumeButton, resumeButton.overlay,
                                            playButton, playButton.overlay,
                                            backButton, backButton.overlay);
        finalPane.getChildren().addAll(middleMenu, rightMenu);

    }
    
    /**
     * Create the button for the level selection.
     * Read the completed levels in the data.json file and create the good number of buttons.
     */
    public void setSelectors(){
    }

    /**
     * Configure the middlePane and the rightPane
     */
    private void setPaneSizes(){
        this.setMiddlePaneSize();
        this.setRightPaneSize();
    }

    /**
     * Configure the middlePane
     */
    private void setMiddlePaneSize() {
        this.middleMenu.setPrefWidth(1570 * WR);
        this.middleMenu.setMaxWidth(1570 * WR);
        this.middleMenu.setMinWidth(1570 * WR);
        this.middleMenu.setPrefHeight(this.height);
        this.middleMenu.setMaxHeight(this.height);
        this.middleMenu.setMinHeight(this.height);
        this.middleMenu.setLayoutX(0);
        this.middleMenu.setLayoutY(0);
    }

    /**
     * Configure the RightPane
     */
    private void setRightPaneSize() {
        this.rightMenu.setPrefWidth(350 * WR);
        this.rightMenu.setMaxWidth(350 * WR);
        this.rightMenu.setMinWidth(350 * WR);
        this.rightMenu.setPrefHeight(this.height);
        this.rightMenu.setMaxHeight(this.height);
        this.rightMenu.setMinHeight(this.height);
        this.rightMenu.setLayoutX(1570 * WR);
        this.rightMenu.setLayoutY(0);
    }


    /**
     * middleMenu accessor
     * @return (Pane) middleMenu
     */
    public Pane getMiddleMenu() {
        return this.middleMenu;
    }

    /**
     * selectedLevel accessor
     * @return (byte) selectedLevel
     */
    public byte getSelectedLevel(){
        return this.selectedLevel;
    }

    /**
     * rightMenu accessor
     * @return (Pane) rightMenu
     */
    public Pane getRightMenu() {
        return this.rightMenu;
    }

    /**
     * rightMenuImage accessor
     * @return (CustomImage) rightMenuImage
     */
    public CustomImage getRightMenuImage() {
        return this.rightMenuImage;
    }

    /**
     * levelViewer accessor
     * @return (Pane) levelViewer
     */
    public Pane getLevelViewer(){
        return this.levelViewer;
    }

    /**
     * finalPane accessor
     * @return (Pane) finalPane
     */
    public Pane getFinalPane(){
        return this.finalPane;
    }

    /**
     * backButton accessor
     * @return (CustomButton) backButton
     */
    public CustomButton getBackButton(){
        return this.backButton;
    }

    /**
     * resumeButton accessor
     * @return (CustomButton) resumeButton
     */
    public CustomButton getResumeButton(){
        return this.resumeButton;
    }

    /**
     * playButton accessor
     * @return (CustomButton) playButton
     */
    public CustomButton getPlayButton(){
        return playButton;
    }
}