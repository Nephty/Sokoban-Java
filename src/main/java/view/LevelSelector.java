package view;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import presenter.Main;

/**
 * A <code>LevelSelector</code> is a user interface used to build different types of level selectors.
 * The <code>LevelSelector</code> is not an implementable class : we can not create a new <code>LevelSelector</code>
 * object and have it working as others level selectors, it is an abstract class.
 */
public abstract class LevelSelector
        extends Menu {

    protected Pane middleMenu, rightMenu, levelViewer, selectedLevelViewer;
    protected CustomImage middleMenuBackground, rightMenuImage;
    protected int completedLevels;
    protected byte page=0;
    protected CustomButton backButton, resumeButton, playButton, nextPageButton, previousPageButton;
    protected boolean hasSelected = false;

    protected Pane finalPane;

    /**
     * Create a new <code>LevelSelector</code> object used a level selector where the user can select and start a level
     * or resume one.
     * @param parent_ The <code>Parent</code> object that will contain the entire CampaignSelector
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public LevelSelector(Parent parent_, double width_, double height_, float WR, float HR) {
        super(parent_, width_, height_, WR, HR);

        this.rightMenu = new Pane();
        if (Main.fullscreen) {
            this.rightMenuImage = new CustomImage(0,0,WR,HR,"side menu perfect fit.png");
        } else {
            this.rightMenuImage = new CustomImage(0,0,WR,HR,"right side menu.png");
        }

        this.nextPageButton = new CustomButton(75,(int)height_-100, WR, HR, "right arrow.png");
        this.previousPageButton = new CustomButton(75, (int)height_-150, WR, HR, "left arrow.png");
        nextPageButton.setVisible(false);
        previousPageButton.setVisible(false);
        prepareNextPageButton();
        preparePreviousPageButton();

        //Used to show the map when the mouse is over a button.
        this.levelViewer = new Pane();
        this.levelViewer.setLayoutX(30*WR);
        this.levelViewer.setMaxWidth(290*WR);

        //Used to show the level we have selected.
        this.selectedLevelViewer = new Pane();
        this.selectedLevelViewer.setLayoutX(30*WR);
        this.selectedLevelViewer.setMaxWidth(350*WR);
        rightMenu.getChildren().addAll(rightMenuImage, selectedLevelViewer,levelViewer, nextPageButton, previousPageButton);

        //Back to main Menu
        this.backButton = new CustomButton(15, (int)((height_-96-5)), WR, HR, "back button.png");

        //Visible when a level is selected and it starts the game
        this.playButton = new CustomButton((int)(width_-845), (int)(height_-96-5), WR, HR, "start button.png");
        this.playButton.setVisible(false);

        //Visible when the player comeback from the playingMenu.
        //It starts the game where the player was.
        this.resumeButton = new CustomButton((int) (width_-1375), (int) height_-96-5, WR, HR, "resume button.png");
        this.resumeButton.setVisible(false);

        this.middleMenu = new Pane();

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

        //Global pane of the levelSelector
        this.finalPane = new Pane();
        this.finalPane.setLayoutX(0);
        this.finalPane.setLayoutY(0);
        this.finalPane.setPrefWidth(ORIGINAL_WIDTH);
        this.finalPane.setMinWidth(ORIGINAL_WIDTH);
        this.finalPane.setMaxWidth(ORIGINAL_WIDTH);
        this.finalPane.setPrefHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMinHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMaxHeight(ORIGINAL_HEIGHT);

        finalPane.getChildren().addAll(middleMenu, rightMenu);

    }

    /**
     * Create the buttons for the level selection.
     */
    public void setSelectors() {
        middleMenu.getChildren().removeAll(middleMenu.getChildren());
        this.middleMenuBackground = new CustomButton(0, 0, WR, HR, "background empty.png");
        this.middleMenu.getChildren().add(middleMenuBackground);
        middleMenu.getChildren().addAll(resumeButton, resumeButton.overlay,
                playButton, playButton.overlay,
                backButton, backButton.overlay);
    }

    /**
     * Set the EvenHandler for the nextPageButton.
     */
    private void prepareNextPageButton(){
        this.nextPageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            page++;
            nextPageButton.setVisible(false);
            previousPageButton.setVisible(true);
            this.setSelectors();
        });
    }

    /**
     * Set the EvenHandler for the previousPageButton.
     */
    private void preparePreviousPageButton(){
        previousPageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            page--;
            nextPageButton.setVisible(true);
            previousPageButton.setVisible(false);
            this.setSelectors();
        });
    }


    /**
     * Configure the middlePane and the rightPane.
     */
    private void setPaneSizes(){
        this.setMiddlePaneSize();
        this.setRightPaneSize();
    }

    /**
     * Configure the middlePane.
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
     * Configure the RightPane.
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
     * Return the currently selected level.
     * @return The currently selected level.
     */
    public abstract Object getSelectedLevel();

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

    /**
     * Set the value of the attribute hasSelected to the given boolean
     * @param value The new value
     */
    public void setHasSelected(boolean value) {
        this.hasSelected = value;
    }
}