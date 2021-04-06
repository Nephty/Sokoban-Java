package main.java.view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PlayingMenu extends Menu {

    //---------//
    // Objects //
    //---------//

    private Board currentBoard;
    ArrayList<String> currentLevelString;
    private Pane leftMenu, middleMenu, rightMenu;
    private CustomImage leftMenuImage, middleMenuImage, rightMenuImage;
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
    Text totalMovesText, totalPushesText, objectivesText, timeText, currentLevelText, currentLevelAverageRatingText, currentLevelDifficultyText;
    private Game game;
    Button moveUp, moveDown, moveLeft, moveRight;
    Pane finalPane;

    //------//
    // Data //
    //------//

    private byte currentLevel = 10;
    private int maxWidth, limit, availableSpace, imageLength, remainingSpace, firstPosX;
    int currentLevelPosX = 148;
    int difficultyPosX = 0;
    float currentLevelAverageRating = (float) 0.27;
    int averageRatingPosX = 140;
    Difficulty currentLevelDifficulty = Difficulty.NORMAL;


    public PlayingMenu(Parent parent_, double width_, double height_, float WR_, float HR_, Stage window_) throws IOException {
        super(parent_, width_, height_, WR_, HR_);
        this.leftMenu = new Pane();
        this.leftMenuImage = new CustomImage(0, 0, WR, HR, "left side menu.png");

        this.rightMenu = new Pane();
        this.rightMenuImage = new CustomImage(0, 0, WR, HR, "right side menu.png");

        this.middleMenu = new Pane();
        this.middleMenuBackground = new CustomImage(0, 0, WR, HR, "background empty.png");
        this.middleMenu.getChildren().add(this.middleMenuBackground);

        this.game = new Game(this.currentBoard);

        this.setPaneSizes();
        this.loadLevelFileAndInitializeBoard();
        this.prepareMapSize();
        this.prepareInterfaces();
        this.prepareTextBoxes();

        this.gamePane = new Pane();
        this.gamePane.setLayoutX(60 * WR);
        this.gamePane.setLayoutY(60 * WR);

        EventHandler keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
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
                        currentBoard.restart();
                        game.setPlayerFacing(Direction.DOWN);
                        game.setTotalMoves(0);
                        game.setTotalPushes(0);
                        totalMovesText.setText(String.valueOf(game.getTotalMoves()));
                        totalPushesText.setText(String.valueOf(game.getTotalPushes()));
                        objectivesText.setText(
                                currentBoard.getCurrBoxOnObj()
                                        + " / "
                                        + currentBoard.getBoxes().size());
                        while (game.getTotalMovesPow() > 1) {
                            game.addTotalMovesPow((byte) -1);
                            totalMovesText.setX(totalMovesText.getX() + 10);
                        }
                        while (game.getTotalPushesPow() > 1) {
                            game.addTotalPushesPow((byte) -1);
                            totalPushesText.setX(totalPushesText.getX() + 10);
                        }

                        try {
                            updateMapTiles();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }

                        game.setPlayerFacing(Direction.DOWN);
                        direction = Direction.RESTART;
                    default:
                        direction = Direction.NULL;
                }
                if (direction != Direction.NULL) {
                    BooleanCouple moveResult = currentBoard.move(direction);
                    if (moveResult.isA()) {
                        game.addTotalMoves((byte) 1);
                        totalMovesText.setText(String.valueOf(game.getTotalMoves()));
                        if (moveResult.isB()) {
                            game.addTotalPushes((byte) 1);
                            objectivesText.setText(currentBoard.getCurrBoxOnObj() + " / " + currentBoard.getBoxes().size());
                            totalPushesText.setText(String.valueOf(game.getTotalPushes()));
                        }

                        if ((game.getTotalMoves()) % Math.pow(10, game.getTotalMovesPow()) == 0) {
                            game.addTotalMovesPow((byte) 1);
                            totalMovesText.setX(totalMovesText.getX() - 10);
                        }

                        // offset to keep the text centered
                        if ((game.getTotalPushes()) % Math.pow(10, game.getTotalPushesPow()) == 0 && game.getTotalPushes() != 0) {
                            game.addTotalPushesPow((byte) 1);
                            totalPushesText.setX(totalPushesText.getX() - 10);
                        }

                        try {
                            updateMapTiles();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                    }
                }
            }
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
    }

    public PlayingMenu(Parent parent_, double width_, double height_, float WR, float HR, Stage window_, CustomImage background_)
            throws IOException {
        super(parent_, width_, height_, WR, HR);
        this.background = background_;

        this.leftMenu = new Pane();
        this.leftMenuImage = new CustomImage(0, 0, WR, HR, "left side menu.png");

        this.rightMenu = new Pane();
        this.rightMenuImage = new CustomImage(0, 0, WR, HR, "right side menu.png");

        this.middleMenu = new Pane();

        this.setPaneSizes();
        this.loadLevelFileAndInitializeBoard();
        this.prepareMapSize();
        this.prepareInterfaces();
        this.prepareTextBoxes();

        this.game = new Game(this.currentBoard);

        this.gamePane = new Pane();
        this.gamePane.setLayoutX(60 * WR);
        this.gamePane.setLayoutY(60 * WR);

        EventHandler keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
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
                        currentBoard.restart();
                        game.setPlayerFacing(Direction.DOWN);
                        direction = Direction.RESTART;
                    default:
                        direction = Direction.NULL;
                }
                BooleanCouple moveResult = currentBoard.move(direction);
                if (direction != Direction.NULL && moveResult.isA()) {
                    game.addTotalMoves((byte) 1);
                    totalMovesText.setText(String.valueOf(game.getTotalMoves()));
                    if (moveResult.isB()) {
                        game.addTotalPushes((byte) 1);
                        objectivesText.setText(currentBoard.getCurrBoxOnObj() + " / " + currentBoard.getBoxes().size());
                        totalPushesText.setText(String.valueOf(game.getTotalPushes()));
                    }

                    if ((game.getTotalMoves()) % Math.pow(10, game.getTotalMovesPow()) == 0) {
                        game.addTotalMovesPow((byte) 1);
                        totalMovesText.setX(totalMovesText.getX() - 10);
                    }

                    // offset to keep the text centered
                    if ((game.getTotalPushes()) % Math.pow(10, game.getTotalPushesPow()) == 0 && game.getTotalPushes() != 0) {
                        game.addTotalPushes((byte) 1);
                        totalPushesText.setX(totalPushesText.getX() - 10);
                    }

                    try {
                        updateMapTiles();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        };
        prepareMoveButtons(keyEventHandler);
        updateMapTiles();

        this.finalPane = new Pane();
        this.finalPane.setLayoutX(0);
        this.finalPane.setLayoutY(0);
        this.finalPane.setPrefWidth(ORIGINAL_WIDTH);
        this.finalPane.setMinWidth(ORIGINAL_WIDTH);
        this.finalPane.setMaxWidth(ORIGINAL_WIDTH);
        this.finalPane.setPrefHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMinHeight(ORIGINAL_HEIGHT);
        this.finalPane.setMaxHeight(ORIGINAL_HEIGHT);
    }

    private void setPaneSizes() {
        this.setLeftPaneSize();
        this.setMiddlePaneSize();
        this.setRightPaneSize();
    }

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

    private void loadLevelFileAndInitializeBoard() throws IOException {
        String levelFileName = "level";
        if (this.currentLevel < 10) {
            levelFileName += "0";
        }
        levelFileName += String.valueOf(this.currentLevel);
        levelFileName += ".xsb";
        this.currentLevelString = Fichier.loadFile(levelFileName, "");
        this.setCurrentBoard(new Board(this.currentLevelString));
    }

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

    private void prepareTextBoxes() {
        this.prepareMovesText();
        this.prepareObjectivesText();
        this.preparePushesText();
        this.prepareTimeText();
        this.prepareDifficultyText();
        this.prepareAverageRatingText();
        this.prepareCurrentLevelText();
    }


    private void prepareMovesInterface()
            throws FileNotFoundException {
        this.moves = new CustomImage(65, 100, this.WR, this.HR, "moves.png");
        this.movesContainer = new CustomImage(85, 150, this.WR, this.HR, "text container.png");
    }

    private void preparePushesInterface()
            throws FileNotFoundException {
        this.pushes = new CustomImage(65, 210, this.WR, this.HR, "pushes.png");
        this.pushesContainer = new CustomImage(85, 260, this.WR, this.HR, "text container.png");
    }

    private void prepareObjectivesInterface()
            throws FileNotFoundException {
        this.objectives = new CustomImage(65, 320, this.WR, this.HR, "objectives.png");
        this.objectivesContainer = new CustomImage(85, 370, this.WR, this.HR, "text container.png");
    }

    private void prepareTimeInterface()
            throws FileNotFoundException {
        this.time = new CustomImage(65, 430, this.WR, this.HR, "time.png");
        this.timeContainer = new CustomImage(85, 480, this.WR, this.HR, "text container.png");
    }

    private void prepareUndoButton()
            throws FileNotFoundException {
        this.undoButton = new CustomButton(65, 800, WR, HR, "undo.png", (byte) 0);
    }

    private void prepareCurrentLevelInterface()
            throws FileNotFoundException {
        this.currentLevelImg = new CustomImage(58, 100, WR, HR, "level.png");
        this.currentLevelImgContainer = new CustomImage(78, 150, WR, HR, "text container.png");
    }

    private void prepareDifficultyInterface()
            throws FileNotFoundException {
        this.difficultyImg = new CustomImage(58, 245, WR, HR, "difficulty.png");
        this.difficultyImgContainer = new CustomImage(78, 295, WR, HR, "text container.png");
    }

    private void prepareAverageRatingInterface()
            throws FileNotFoundException {
        this.averageRatingImg = new CustomImage(50, 390, WR, HR, "average rating.png");
        this.averageRatingImgContainer = new CustomImage(80, 440, WR, HR, "text container.png");
    }

    private void prepareRestartButton()
            throws FileNotFoundException {
        this.restartButton = new CustomButton(65, 900, WR, HR, "restart.png", (byte) 0);
    }

    private void prepareRestartButtonAction() {
        this.restartButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.currentBoard.restart();
                this.game.setPlayerFacing(Direction.DOWN);
                this.totalMovesText.setText("0");
                this.totalPushesText.setText("0");
                this.objectivesText.setText(
                        this.currentBoard.getCurrBoxOnObj()
                                + " / "
                                + this.currentBoard.getBoxes().size());
                while (this.game.getTotalMovesPow() > 1) {
                    this.game.addTotalMovesPow((byte) -1);
                    this.totalMovesText.setX(this.totalMovesText.getX() + 10);
                }
                while (this.game.getTotalPushesPow() > 1) {
                    this.game.addTotalPushesPow((byte) -1);
                    this.totalPushesText.setX(this.totalPushesText.getX() + 10);
                }
                this.game.setTotalMoves(0);
                this.game.setTotalPushes(0);

                try {
                    this.updateMapTiles();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
    }

    private void prepareMainMenuButton() throws FileNotFoundException {
        this.mainMenuButton = new CustomButton(50, 850, WR, HR, "main menu.png", (byte) 1);
    }


    private void prepareMovesText() {
        this.totalMovesText = new Text(165 * this.WR, 185 * this.HR, String.valueOf(this.game.getTotalMoves()));
        this.totalMovesText.setFont(this.font);
        this.totalMovesText.setFill(this.color);
    }

    private void preparePushesText() {
        this.totalPushesText = new Text(165 * this.WR, 295 * this.WR, String.valueOf(this.game.getTotalPushes()));
        this.totalPushesText.setFont(this.font);
        this.totalPushesText.setFill(this.color);
    }

    private void prepareObjectivesText() {
        String objectivesStr = this.currentBoard.getCurrBoxOnObj() + " / " + this.currentBoard.getBoxes().size();
        this.objectivesText = new Text(135 * this.WR, 405 * this.WR, objectivesStr);
        this.objectivesText.setFont(this.font);
        this.objectivesText.setFill(this.color);
    }

    private void prepareTimeText() {
        String timeStr = "00:00:00";
        this.timeText = new Text(105 * this.WR, 515 * this.WR, timeStr);
        this.timeText.setFont(this.font);
        this.timeText.setFill(this.color);
    }

    private void prepareCurrentLevelText() {
        if (this.currentLevel < 10) {
            this.currentLevelPosX += 10;
        }
        this.currentLevelText = new Text(this.currentLevelPosX * WR, 185 * WR, String.valueOf(currentLevel));
        this.currentLevelText.setFont(this.font);
        this.currentLevelText.setFill(this.color);
    }

    private void prepareDifficultyText() {
        this.currentLevelDifficulty = Difficulty.NORMAL;
        switch (this.currentLevelDifficulty) {
            case EASY -> this.difficultyPosX = 123;
            case NORMAL -> this.difficultyPosX = 91;
            case HARD -> this.difficultyPosX = 118;
            case EXTREME -> this.difficultyPosX = 89;
        }
        this.currentLevelDifficultyText = new Text(this.difficultyPosX * WR, 330 * WR, String.valueOf(this.currentLevelDifficulty));
        this.currentLevelDifficultyText.setFont(this.font);
        this.currentLevelDifficultyText.setFill(this.color);
    }

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

    private void prepareMapSize() {
        this.maxWidth = this.currentBoard.getLevelWidth();
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

    private void updateMapTiles()  // was previously called setMap()
            throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        Block[][] blockList = this.currentBoard.getBlockList();
        final int spaceConstant = (int) ((this.imageLength) / this.WR);
        this.gamePane.getChildren().removeAll(this.gamePane.getChildren());

        String fileName = "";
        for (int y = 0; y < this.currentBoard.getLevelHeight(); y++) {
            for (int x = 0; x < blockList[y].length; x++) {
                Block currentItem = blockList[y][x];
                if (currentItem instanceof Wall) {
                    fileName = "wall.png";
                } else if ((currentItem instanceof Box) && !(currentItem instanceof BoxOnObj)) {
                    fileName = "box.png";
                } else if (currentItem instanceof BoxOnObj) {
                    fileName = "boxonobjective.png";
                } else if (currentItem instanceof Goal) {
                    fileName = "objective.png";
                } else if (currentItem instanceof Player) {
                    fileName = switch (this.game.getPlayerFacing()) {
                        case DOWN -> "player down.png";
                        case UP -> "player up.png";
                        case LEFT -> "player left.png";
                        case RIGHT -> "player right.png";
                        default -> fileName;
                    };
                } else {  // if item is null (air) or something else
                    fileName = "air.png";
                }
                CustomImage currentItemImg = new CustomImage((int) (x * spaceConstant), (int) (y * spaceConstant), this.WR, this.HR, fileName);
                this.gamePane.getChildren().add(currentItemImg);
            }
        }
    }

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

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public CustomButton getMainMenuButton() {
        return this.mainMenuButton;
    }

    public Pane getLeftMenu() {
        return this.leftMenu;
    }

    public Pane getMiddleMenu() {
        return this.middleMenu;
    }

    public Pane getRightMenu() {
        return this.rightMenu;
    }

    public CustomImage getRightMenuImage() {
        return this.rightMenuImage;
    }

    public CustomImage getLeftMenuImage() {
        return this.leftMenuImage;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public Button getMoveUp() {
        return moveUp;
    }

    public Button getMoveDown() {
        return moveDown;
    }

    public Button getMoveLeft() {
        return moveLeft;
    }

    public Button getMoveRight() {
        return moveRight;
    }

    public Pane getFinalPane() {
        return finalPane;
    }

    public CustomImage getMoves() {
        return moves;
    }

    public CustomImage getMovesContainer() {
        return this.movesContainer;
    }

    public CustomImage getPushes() {
        return this.pushes;
    }

    public CustomImage getPushesContainer() {
        return this.pushesContainer;
    }

    public CustomImage getObjectives() {
        return this.objectives;
    }

    public CustomImage getObjectivesContainer() {
        return this.objectivesContainer;
    }

    public CustomImage getTime() {
        return this.time;
    }

    public CustomImage getTimeContainer() {
        return this.timeContainer;
    }

    public CustomImage getCurrentLevelImg() {
        return this.currentLevelImg;
    }

    public CustomImage getCurrentLevelImgContainer() {
        return this.currentLevelImgContainer;
    }

    public CustomImage getDifficultyImg() {
        return this.difficultyImg;
    }

    public CustomImage getDifficultyImgContainer() {
        return this.difficultyImgContainer;
    }

    public CustomImage getAverageRatingImg() {
        return this.averageRatingImg;
    }

    public CustomImage getAverageRatingImgContainer() {
        return this.averageRatingImgContainer;
    }

    public CustomButton getUndoButton() {
        return this.undoButton;
    }

    public CustomButton getRestartButton() {
        return this.restartButton;
    }

    public Text getTotalMovesText() {
        return this.totalMovesText;
    }

    public Text getTotalPushesText() {
        return this.totalPushesText;
    }

    public Text getObjectivesText() {
        return this.objectivesText;
    }

    public Text getTimeText() {
        return this.timeText;
    }

    public Text getCurrentLevelText() {
        return this.currentLevelText;
    }

    public Text getCurrentLevelAverageRatingText() {
        return this.currentLevelAverageRatingText;
    }

    public Text getCurrentLevelDifficultyText() {
        return this.currentLevelDifficultyText;
    }

    public int getAverageRatingPosX() {
        return this.averageRatingPosX;
    }
}
