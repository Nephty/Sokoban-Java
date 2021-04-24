package view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import presenter.Main;

/**
 * A <code>PlayingMenu</code> is a user interface used when the user is playing. It displays the total amount of moves
 * and pushes made during the game, the amount of <code>Boxes</code> that are placed on an <code>Objective</code>,
 * the time elapsed since the beginning of the game, the current level, the difficulty and average rating of the level,
 * an "Undo", a "Restart" and a "Main Menu" button. It also displays the current <code>Board</code> and its
 * current state.
 */
public class PlayingMenu extends Menu {


    ArrayList<String> currentLevelString;
    private final Pane leftMenu, middleMenu, rightMenu;
    private CustomImage leftMenuImage, middleMenuImage, rightMenuImage, rickRollImage;
    private Pane gamePane;
    private CustomImage moves, movesContainer,
            pushes, pushesContainer,
            objectives, objectivesContainer,
            time, timeContainer,
            currentLevelImg, currentLevelImgContainer,
            difficultyImg, difficultyImgContainer,
            averageRatingImg, averageRatingImgContainer,
            middleMenuBackground;
    private CustomButton undoButton, restartButton, mainMenuButton;
    private Text totalMovesText, totalPushesText, objectivesText, timeText,
            currentLevelText, currentLevelAverageRatingText,
            currentLevelDifficultyText, youWonText;
    private Game game;
    Button moveUp, moveDown, moveLeft, moveRight;
    Pane finalPane;
    ArrayList<Direction> movesHistory;
    LevelSaver levelSaver = new LevelSaver();
    private StopWatch stopWatch;


    private byte currentLevel = 6;
    private String currentLevelName = "level06.xsb";
    private int maxWidth, limit, availableSpace, imageLength, remainingSpace, firstPosX;
    int currentLevelPosX = 148;
    int difficultyPosX = 0;
    float currentLevelAverageRating = (float) 0.27;
    int averageRatingPosX = 140;
    Difficulty currentLevelDifficulty = Difficulty.NORMAL;
    private boolean currentLevelIsWon = false;
    private AudioPlayer beatPlayer;
    private AudioPlayer effectPlayer;

    /**
     * Create a new <code>PlayingMenu</code> and all its attributes. Create the two side <code>Panes</code>,
     * the texts and images to display information about the on-going level, the central <code>Pane</code>,
     * the <code>Game</code> and the <code>Board</code> used for the current game, the moves history,
     * the <code>EventHandler</code> in order to use the keyboard  and the saving/loading features.
     * @param width_ The width of the menu (preferably the size of the window)
     * @param height_ The height of the menu (preferably the size of the window)
     * @param WR_ The width ratio that will be used to resize the components
     * @param HR_ The height ratio that will be used to resize the components
     * @param beatPlayer The <code>AudioPlayer</code> that will play the main theme music
     * @throws IOException Exception thrown when any provided file could not be found
     */
    public PlayingMenu(Parent parent_, double width_, double height_, float WR_, float HR_, AudioPlayer beatPlayer, AudioPlayer effectPlayer)
            throws IOException {
        super(parent_, width_, height_, WR_, HR_);
        this.leftMenu = new Pane();
        this.rightMenu = new Pane();
        this.beatPlayer = beatPlayer;

        this.effectPlayer = effectPlayer;
        this.effectPlayer.prepareMusic("crash.mp3");

        if (Main.fullscreen) {
            this.leftMenuImage = new CustomImage(0, 0, WR, HR, "side menu perfect fit.png");
            this.rightMenuImage = new CustomImage(0, 0, WR, HR, "side menu perfect fit.png");
        } else {
            this.leftMenuImage = new CustomImage(0, 0, WR, HR, "left side menu.png");
            this.rightMenuImage = new CustomImage(0, 0, WR, HR, "right side menu.png");
        }

        this.middleMenu = new Pane();
        this.middleMenuBackground = new CustomImage(0, 0, WR, HR, "background empty.png");
        this.middleMenu.getChildren().add(this.middleMenuBackground);

        this.rickRollImage = new CustomImage(0,0,WR,HR,"secret.png");
        this.rickRollImage.setVisible(false);


        this.game = new Game(new Board());
        this.movesHistory = new ArrayList<>();

        this.setPaneSizes();
        this.loadLevelFileAndInitializeBoard();
        this.prepareMapSize();
        this.prepareInterfaces();
        this.prepareTextBoxes();

        this.gamePane = new Pane();
        this.gamePane.setLayoutX(60 * WR);
        this.gamePane.setLayoutY(60 * WR);

        EventHandler keyEventHandler = (EventHandler<KeyEvent>) keyEvent -> {
            Direction direction;
            switch (keyEvent.getCode()) {
                case Z:
                case UP:
                    direction = Direction.UP;
                    game.setPlayerFacing(direction);
                    break;
                case S:
                case DOWN:
                    direction = Direction.DOWN;
                    game.setPlayerFacing(direction);
                    break;
                case Q:
                case LEFT:
                    direction = Direction.LEFT;
                    game.setPlayerFacing(direction);
                    break;
                case D:
                case RIGHT:
                    direction = Direction.RIGHT;
                    game.setPlayerFacing(direction);
                    break;
                case R:
                    this.resetCounters();
                    game.setPlayerFacing(Direction.DOWN);
                    direction = Direction.RESTART;
                    break;
                case F:
                    try {
                        levelSaver.saveLevel(movesHistory, currentLevel, CompleteFieldBox.display("Enter a file name",
                                "Enter the name you want to use for the file.\nLeave blank for an automatic file name.",
                                "File name..."));
                    } catch (IOException e) {
                        AlertBox.display("Error", "An error occurred while trying to save the level");
                    }
                    direction = Direction.NULL;
                    break;
                case G:
                    // TODO : what's taking so long to apply a lot of moves (200+ for example) ?
                    String fileName = CompleteFieldBox.displayFileSelector("Enter file name", "File name :", "File name...");
                    if (fileName != null && !fileName.equals("")) {
                        try {
                            ArrayList<Direction> res = levelSaver.getHistory(fileName, "");
                            for (Direction dir : res) {
                                applyMove(dir);
                                System.out.println("applied : " + dir);
                            }
                        } catch (IOException | ClassNotFoundException exception){
                            AlertBox.display("Error", "Error : "+exception.getMessage());
                        }
                    }
                    direction = Direction.NULL;
                    break;
                case K:
                    Console.open();
                    System.out.println("opened");
                    direction = Direction.NULL;
                    break;
                case L:
                    Console.close();
                    System.out.println("closed");
                    direction = Direction.NULL;
                    break;
                default:
                    direction = Direction.NULL;
            }
            applyMove(direction);
        };
        prepareMoveButtons(keyEventHandler);
        updateMapTiles();

        System.out.println(this.objectives);
        System.out.println(this.objectivesContainer);

        this.finalPane = new Pane();
        this.finalPane.setLayoutX(0);
        this.finalPane.setLayoutY(0);
        this.finalPane.setPrefWidth(ORIGINAL_WIDTH);
        this.finalPane.setMinWidth(ORIGINAL_WIDTH);
        this.finalPane.setMaxWidth(ORIGINAL_WIDTH);
        this.finalPane.setPrefHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMinHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMaxHeight(ORIGINAL_HEIGHT);

        this.leftMenu.getChildren().addAll(this.moveDown, this.moveUp, this.moveRight, this.moveLeft);
        this.leftMenu.getChildren().add(this.leftMenuImage);
        this.leftMenu.getChildren().addAll(
                this.moves, this.movesContainer, this.totalMovesText,
                this.pushes, this.pushesContainer, this.totalPushesText,
                this.objectives, this.objectivesContainer, this.objectivesText,
                this.time, this.timeContainer, this.timeText,
                this.undoButton, this.undoButton.overlay,
                this.restartButton, this.restartButton.overlay
        );

        this.rightMenu.getChildren().add(this.rightMenuImage);
        this.rightMenu.getChildren().addAll(
                this.currentLevelImg, this.currentLevelImgContainer, this.currentLevelText,
                this.difficultyImg, this.difficultyImgContainer, this.currentLevelDifficultyText,
                this.averageRatingImg, this.averageRatingImgContainer, this.currentLevelAverageRatingText,
                this.mainMenuButton, this.mainMenuButton.overlay
        );

        this.middleMenu.getChildren().addAll(this.gamePane, this.youWonText);
        this.finalPane.getChildren().addAll(this.leftMenu, this.middleMenu, this.rightMenu,this.rickRollImage);
    }

    /**
     * Try to move the player in the given <code>Direction</code>, increase the total moves count if the player was
     * able to move, increase the total pushes count if the player pushed a <code>Box</code> and update the
     * user interface with the new images for the on-going game.
     * @param direction The <code>Direction</code> in which the player tries to move
     */
    private void applyMove(Direction direction) {
        if (direction != Direction.NULL && direction != Direction.RESTART) {
            BooleanCouple moveResult = game.getBoard().move(direction);
            if (moveResult.isA()) {
                movesHistory.add(direction);
                game.addTotalMoves((byte) 1);
                totalMovesText.setText(String.valueOf(game.getTotalMoves()));
                if (moveResult.isB()) {
                    game.addTotalPushes((byte) 1);
                    objectivesText.setText(game.getBoard().getCurrBoxOnObj() + " / " + game.getBoard().getBoxes().size());
                    totalPushesText.setText(String.valueOf(game.getTotalPushes()));
                }

                if ((game.getTotalMoves()) % Math.pow(10, game.getTotalMovesMagnitude()) == 0) {
                    game.addTotalMovesMagnitude((byte) 1);
                    totalMovesText.setX(totalMovesText.getX() - 10);
                }

                // offset to keep the text centered
                if ((game.getTotalPushes()) % Math.pow(10, game.getTotalPushesMagnitude()) == 0 && game.getTotalPushes() != 0) {
                    game.addTotalPushesMagnitude((byte) 1);
                    totalPushesText.setX(totalPushesText.getX() - 10);
                }

                try {
                    updateMapTiles();
                } catch (FileNotFoundException fileNotFoundException) {
                    AlertBox.display("Fatal Error", fileNotFoundException.getMessage());
                    System.exit(-1);
                }
            }else{
                effectPlayer.getMediaPlayer().play();
                effectPlayer.restart();

            }
        }
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
     * Load the file corresponding to the selected level and set the new <code>Board</code> for the <code>Game</code>.
     */
    private void loadLevelFileAndInitializeBoard() throws IOException{
        String levelFileName = "level";
        if (this.currentLevel < 10) {
            levelFileName += "0";
        }
        levelFileName += String.valueOf(this.currentLevel);
        levelFileName += ".xsb";
        this.currentLevelString = Fichier.loadFile(levelFileName, "campaign");
        this.game.setBoard(new Board(this.currentLevelString));
    }

    /**
     * Prepare every image and button displaying information in the left and right <code>Panes</code>.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareInterfaces()
            throws FileNotFoundException {
        this.prepareMovesInterface();
        this.prepareObjectivesInterface();
        this.preparePushesInterface();
        this.prepareTimeInterface();
        this.prepareUndoButton();
        this.prepareRestartButton();
        this.prepareRestartButtonAction();
        this.prepareMainMenuButton();
        this.prepareCurrentLevelInterface();
        this.prepareDifficultyInterface();
        this.prepareAverageRatingInterface();
    }

    /**
     * Prepare every text box that will be used to write real-time changing information.
     */
    private void prepareTextBoxes() {
        this.prepareMovesText();
        this.prepareObjectivesText();
        this.preparePushesText();
        this.prepareTimeText();
        this.prepareDifficultyText();
        this.prepareAverageRatingText();
        this.prepareCurrentLevelText();
        this.prepareYouWonText();
    }

    /**
     * Prepare the moves information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareMovesInterface()
            throws FileNotFoundException {
        this.moves = new CustomImage(65, 100, this.WR, this.HR, "moves.png");
        this.movesContainer = new CustomImage(85, 150, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the pushes information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void preparePushesInterface()
            throws FileNotFoundException {
        this.pushes = new CustomImage(65, 210, this.WR, this.HR, "pushes.png");
        this.pushesContainer = new CustomImage(85, 260, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the amount of box on objectives information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareObjectivesInterface()
            throws FileNotFoundException {
        this.objectives = new CustomImage(65, 320, this.WR, this.HR, "objectives.png");
        this.objectivesContainer = new CustomImage(85, 370, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the elapsed time since the begging of the game information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareTimeInterface()
            throws FileNotFoundException {
        this.time = new CustomImage(65, 430, this.WR, this.HR, "time.png");
        this.timeContainer = new CustomImage(85, 480, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the "Undo" button to undo a move.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareUndoButton()
            throws FileNotFoundException {
        this.undoButton = new CustomButton(65, 800, WR, HR, "undo.png", (byte) 0);
    }

    /**
     *  Prepare the current level information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareCurrentLevelInterface()
            throws FileNotFoundException {
        this.currentLevelImg = new CustomImage(58, 100, WR, HR, "level.png");
        this.currentLevelImgContainer = new CustomImage(78, 150, WR, HR, "text container.png");
    }

    /**
     * Prepare the current level difficulty information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareDifficultyInterface()
            throws FileNotFoundException {
        this.difficultyImg = new CustomImage(58, 245, WR, HR, "difficulty.png");
        this.difficultyImgContainer = new CustomImage(78, 295, WR, HR, "text container.png");
    }

    /**
     * Prepare the current level average rating information display.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareAverageRatingInterface()
            throws FileNotFoundException {
        this.averageRatingImg = new CustomImage(50, 390, WR, HR, "average rating.png");
        this.averageRatingImgContainer = new CustomImage(80, 440, WR, HR, "text container.png");
    }

    /**
     * Prepare the "Restart" button to undo a move.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareRestartButton()
            throws FileNotFoundException {
        this.restartButton = new CustomButton(65, 900, WR, HR, "restart.png", (byte) 0);
    }

    /**
     * Prepare the <code>EventHandler</code> used with the "Restart" button.
     */
    private void prepareRestartButtonAction() {
        this.restartButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.game.getBoard().restart();
                this.resetCounters();
                try {
                    this.updateMapTiles();
                } catch (FileNotFoundException fileNotFoundException) {
                    AlertBox.display("Fatal Error", fileNotFoundException.getMessage());
                    System.exit(-1);
                }
                this.stopWatch.restart();
            }
        });
    }

    /**
     * Prepare the "Main menu" button to go back to the main menu.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void prepareMainMenuButton()
            throws FileNotFoundException {
        this.mainMenuButton = new CustomButton(50, 850, WR, HR, "main menu.png", (byte) 1);
    }

    /**
     * Prepare the real-time changing amount of moves information display.
     */
    private void prepareMovesText() {
        this.totalMovesText = new Text(165 * this.WR, 185 * this.HR, String.valueOf(this.game.getTotalMoves()));
        this.totalMovesText.setFont(this.font);
        this.totalMovesText.setFill(this.color);
    }

    /**
     * Prepare the real-time changing amount of pushes information display.
     */
    private void preparePushesText() {
        this.totalPushesText = new Text(165 * this.WR, 295 * this.WR, String.valueOf(this.game.getTotalPushes()));
        this.totalPushesText.setFont(this.font);
        this.totalPushesText.setFill(this.color);
    }

    /**
     * Prepare the real-time changing amount of boxes on objectives information display.
     */
    private void prepareObjectivesText() {
        String objectivesStr = this.game.getBoard().getCurrBoxOnObj() + " / " + this.game.getBoard().getBoxes().size();
        this.objectivesText = new Text(135 * this.WR, 405 * this.WR, objectivesStr);
        this.objectivesText.setFont(this.font);
        this.objectivesText.setFill(this.color);
    }

    /**
     * Prepare the real-time changing elapsed time information display.
     */
    private void prepareTimeText() {
        this.timeText = new Text(105 * this.WR, 515 * this.WR, "");
        stopWatch = new StopWatch(timeText);
        this.timeText.setFont(this.font);
        this.timeText.setFill(this.color);
    }

    /**
     * Prepare the current level information display.
     */
    private void prepareCurrentLevelText() {
        if (this.currentLevel < 10) {
            this.currentLevelPosX += 10;
        }
        this.currentLevelText = new Text(this.currentLevelPosX * WR, 185 * WR, String.valueOf(currentLevel));
        this.currentLevelText.setFont(this.font);
        this.currentLevelText.setFill(this.color);
    }

    /**
     * Prepare the current level difficulty information display.
     */
    private void prepareDifficultyText() {
        this.currentLevelDifficulty = Difficulty.NORMAL;
        switch (this.currentLevelDifficulty) {
            case EASY:
                this.difficultyPosX = 123;
                break;
            case NORMAL:
                this.difficultyPosX = 91;
                break;
            case HARD:
                this.difficultyPosX = 118;
                break;
            case EXTREME:
                this.difficultyPosX = 89;
                break;
        }
        this.currentLevelDifficultyText = new Text(this.difficultyPosX * WR, 330 * WR, String.valueOf(this.currentLevelDifficulty));
        this.currentLevelDifficultyText.setFont(this.font);
        this.currentLevelDifficultyText.setFill(this.color);
    }

    /**
     * Prepare the current level average rating information display
     */
    private void prepareAverageRatingText() {
        String averageRatingStr = (int) (this.currentLevelAverageRating * 100) + "%";
        if ((int) (this.currentLevelAverageRating) == 1) {
            this.averageRatingPosX -= 10;
        } else if (this.currentLevelAverageRating <= 0.1) {
            this.averageRatingPosX += 10;
        }
        this.currentLevelAverageRatingText = new Text(this.averageRatingPosX * WR, 475 * WR, averageRatingStr);
        this.currentLevelAverageRatingText.setFont(this.font);
        this.currentLevelAverageRatingText.setFill(this.color);
    }

    /**
     * Prepare the congratulations message to display when the user wins.
     */
    private void prepareYouWonText(){
        this.youWonText = new Text(100*WR,  150*HR, "YOU WON !");
        this.youWonText.setFont(new Font("Microsoft YaHei", 175*WR));
        this.youWonText.setFill(Color.rgb(88, 38, 24));
        this.youWonText.setVisible(false);
    }

    /**
     * Prepare the map size and the required data according to the resolution.
     */
    private void prepareMapSize() {
        this.maxWidth = this.game.getBoard().getLevelWidth();
        this.limit = (int) (20 * this.WR);
        this.availableSpace = (int) (1000 * WR);

        // case maxWidth <= 0 doesn't need to be checked because there will always be at least one block on every line
        if (this.maxWidth <= limit) {
            this.imageLength = (int) (50 * WR);
        }
        // no need to do limit < maxWidth <= 40 because maxWidth will always be > limit if we reach this point
        else if (maxWidth <= (int) ((1000 * WR) / 25)) {
            this.imageLength = (int) (this.availableSpace / this.maxWidth);
        }

        this.remainingSpace = (int) (220 * WR);
        this.firstPosX = (int) (460 * WR);
    }

    /**
     * Update the currently displayed map layout based on the new generated <code>Board</code> and its blocklist.
     * This method is used every time the user makes a move. Handles the task of showing the congratulations message
     * if the user won the game.
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    private void updateMapTiles()  // was previously called setMap()
            throws FileNotFoundException {
        Block[][] blockList = this.game.getBoard().getBlockList();
        final int spaceConstant = (int) ((this.imageLength)/HR);
        this.gamePane.getChildren().removeAll(this.gamePane.getChildren());

        String fileName = "";
        for (int y = 0; y < this.game.getBoard().getLevelHeight(); y++) {
            for (int x = 0; x < blockList[y].length; x++) {
                Block currentItem = blockList[y][x];
                if (currentItem != null && !(currentItem instanceof Player)) {
                    fileName = currentItem.getImage();
                } else if (currentItem != null) {  // No need to check if a player because the two possible cases are null or player
                    switch (this.game.getPlayerFacing()) {
                        case DOWN:
                            fileName = "player down.png";
                            break;
                        case UP:
                            fileName = "player up.png";
                            break;
                        case LEFT:
                            fileName = "player left.png";
                            break;
                        case RIGHT:
                            fileName = "player right.png";
                            break;
                        default:
                            break;
                    }
                } else {
                    fileName = "air.png";
                }
                CustomImage currentItemImg = new CustomImage((int) (x * spaceConstant), (int) (y * spaceConstant), this.WR, this.HR, fileName);
                this.gamePane.getChildren().add(currentItemImg);
            }
        }
        if (this.game.getBoard().isWin() && !currentLevelIsWon){
            //AlertBox.display("Victory !", "You won !");
            youWonText.setVisible(true);
            currentLevelIsWon = true;
            stopWatch.stop();
            addLevel();
        } else if (currentLevelIsWon && youWonText.isVisible()){
            youWonText.setVisible(false);
        }

        if (game.getBoard().getPlayer1().isOnPressurePlate()){
            PressurePlate plate = game.getBoard().getPlayer1().getPlate();
            if (plate.getEffect().equals("RickRoll")){
                this.rickRollImage.setVisible(true);
                this.beatPlayer.prepareMusic("secret.mp3");
                this.beatPlayer.play();
            }
        }
    }

    /**
     * Create a button for each move and assign the <code>EventHandler</code> used for the keyboard inputs.
     * @param keyEventHandler The <code>EventHandler</code> to assign to the button
     */
    // TODO : can we run this method with only one button and generify it ?
    private void prepareMoveButtons(EventHandler keyEventHandler) {
        this.moveUp = new Button();
        this.moveUp.setOnKeyPressed(keyEventHandler);

        this.moveLeft = new Button();
        this.moveLeft.setOnKeyPressed(keyEventHandler);

        this.moveDown = new Button();
        this.moveDown.setOnKeyPressed(keyEventHandler);

        this.moveRight = new Button();
        this.moveRight.setOnKeyPressed(keyEventHandler);
    }

    /**
     * Return the currently used main menu button.
     * @return The currently used main menu button
     */
    public CustomButton getMainMenuButton() {
        return this.mainMenuButton;
    }

    /**
     * Return the currently used Rick Roll image.
     * @return The currently used Rick Roll image
     */
    public CustomImage getRickRollImage(){
        return rickRollImage;
    }

    /**
     * Return the currently used final <code>Pane</code> containing the other three main <code>Panes</code>
     * (Left, Middle and Right <code>Panes</code>).
     * @return The currently used final <code>Pane</code>
     */
    public Pane getFinalPane() {
        return finalPane;
    }

    public CustomImage getTime() {
        return this.time;
    }

    public void applyNewBoard(Board newBoard)
            throws FileNotFoundException {
        this.game.setBoard(newBoard);
        this.updateMapTiles();
    }

    /**
     * Add one level to the completed level count in the data.json file.
     */
    private void addLevel() {
        if (currentLevel != -1) {
            try {
                JSONReader reader = new JSONReader("data.json");
                int currentCompletedLevels = reader.getByte("completed levels");
                if (currentCompletedLevels == (currentLevel - 1)) {
                    JSONWriter writer = new JSONWriter("data.json");
                    writer.set("completed levels", String.valueOf((currentCompletedLevels + 1)));
                }
            } catch (IOException | ParseException exc) {
                AlertBox.display("Error", "Error while trying to edit completed levels\n"+exc.getMessage());
            }
        }
    }

    /**
     * Change the current level to the given value.
     * @param level The number of the new level
     * @throws IOException Exception thrown when a provided file name doesn't match any file
     */
    public void setLevel(Byte level)
            throws IOException {
        this.currentLevel = level;
        this.currentLevelText.setText(String.valueOf(level));
        if (level > 9){
            this.currentLevelText.setX((currentLevelPosX-10)*WR);
        } else {
            this.currentLevelText.setX(currentLevelPosX*WR);
        }
        this.currentLevelIsWon = false;
        this.loadLevelFileAndInitializeBoard();
        this.prepareMapSize();
        this.resetCounters();
        this.updateMapTiles();
        stopWatch.restart();
    }

    /**
     * Change the current level to the level matching the given name.
     * @param name The name of the new level
     * @throws IOException Exception thrown when a provided file name doesn't match any file
     */
    public void setLevel(String name)
            throws IOException {
        this.currentLevel = -1;
        this.currentLevelName = name;
        String[] tmp = name.split(".xsb");
        String levelName = tmp[0];
        if (levelName.length() > 7){
            String tmpName ="";
            for (int j=0;j<=5;j++){
                tmpName += levelName.charAt(j);
            }
            tmpName += "...";
            levelName = tmpName;
        }
        this.currentLevelText.setX(currentLevelImgContainer.getX() + (currentLevelImgContainer.getWidth()/tmp[0].length()));
        this.currentLevelText.setText(levelName);
        this.currentLevelIsWon = false;
        this.currentLevelString = Fichier.loadFile(currentLevelName, "freePlay");
        this.game.setBoard(new Board(this.currentLevelString));
        this.prepareMapSize();
        this.resetCounters();
        this.updateMapTiles();
        stopWatch.restart();
    }

    /**
     * Change the current level to the random level generated
     * @param random The <code>ArrayList(String)</code> of the level
     * @throws FileNotFoundException Exception thrown when a provided file name doesn't match any file
     */
    public void setLevel(ArrayList<String> random) throws IOException{
        this.currentLevel = -1;
        this.currentLevelString = random;
        this.currentLevelText.setX(currentLevelImgContainer.getX()+20*WR);
        this.currentLevelText.setText("random");
        this.game.setBoard(new Board(currentLevelString));
        this.prepareMapSize();
        this.resetCounters();
        this.updateMapTiles();
        stopWatch.restart();
    }

    /**
     * Reset the counters of the amount of moves and pushes.
     */
    private void resetCounters(){
        game.getBoard().restart();
        movesHistory = new ArrayList<>();
        this.game.setPlayerFacing(Direction.DOWN);
        game.setTotalMoves(0);
        game.setTotalPushes(0);
        this.totalMovesText.setText("0");
        this.totalPushesText.setText("0");
        this.objectivesText.setText(
                this.game.getBoard().getCurrBoxOnObj()
                        + " / "
                        + this.game.getBoard().getBoxes().size());
        while (this.game.getTotalMovesMagnitude() > 1) {
            this.game.addTotalMovesMagnitude((byte) -1);
            this.totalMovesText.setX(this.totalMovesText.getX() + 10);
        }
        while (this.game.getTotalPushesMagnitude() > 1) {
            this.game.addTotalPushesMagnitude((byte) -1);
            this.totalPushesText.setX(this.totalPushesText.getX() + 10);
        }
        try {
            updateMapTiles();
        } catch (FileNotFoundException fileNotFoundException) {
            AlertBox.display("Fatal Error", fileNotFoundException.getMessage());
            System.exit(-1);
        }
        this.stopWatch.restart();
    }

    /**
     * Return the currently used <code>StopWatch</code>
     * @return The currently used <code>StopWatch</code>
     */
    public StopWatch getStopWatch() {
        return this.stopWatch;
    }
}