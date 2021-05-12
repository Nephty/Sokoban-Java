package view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;

import java.util.ArrayList;

import model.FileGetter;
import presenter.Main;

/**
 * A <code>PlayingMenu</code> is a user interface used when the user is playing. It displays the total amount of moves
 * and pushes made during the game, the amount of <code>Boxes</code> that are placed on an <code>Objective</code>,
 * the time elapsed since the beginning of the game, the current level, the difficulty and average rating of the level,
 * a "Restart" and a "Main Menu" button. It also displays the current <code>Board</code> and its
 * current state.
 */
public class PlayingMenu extends Menu {


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
            middleMenuBackground, upButton, rightButton, leftButton, downButton;
    private CustomButton restartButton, mainMenuButton,saveButton, loadSaveButton;
    private Text totalMovesText, totalPushesText, objectivesText, timeText,
            currentLevelText, currentLevelAverageRatingText,
            currentLevelDifficultyText, youWonText;
    private Game game;
    private Button moveButton;
    private Pane finalPane;
    private ArrayList<Direction> movesHistory;
    private StopWatch stopWatch;


    private String currentMode = "campaign";
    private byte currentCampaignLevel = 6;
    private int imageLength;
    private int currentLevelPosX = 148;
    private int difficultyPosX = 0;
    private float currentLevelAverageRating;
    private int averageRatingPosX = 140;
    private Difficulty currentLevelDifficulty = Difficulty.NORMAL;
    private boolean currentLevelIsWon = false;
    private AudioPlayer beatPlayer;
    private AudioPlayer effectPlayer;
    private String[] keyBinds = new String[9];

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
     */
    public PlayingMenu(Parent parent_, double width_, double height_, float WR_, float HR_, AudioPlayer beatPlayer, AudioPlayer effectPlayer) {
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
        this.leftMenu.getChildren().add(this.middleMenuBackground);

        this.rickRollImage = new CustomImage(0,0,WR,HR,"secret.png");
        this.rickRollImage.setVisible(false);


        this.game = new Game(new Board());
        this.movesHistory = new ArrayList<>();

        this.setPaneSizes();
        ArrayList<String> baseLevel = FileGetter.loadFile("level06.xsb", "campaign");
        this.game.setBoard(new Board(baseLevel));
        this.prepareMapSize();
        this.prepareInterfaces();
        this.prepareTextBoxes();

        this.gamePane = new Pane();
        this.gamePane.setLayoutX(60 * WR);
        this.gamePane.setLayoutY(60 * HR);

        EventHandler keyEventHandler = (EventHandler<KeyEvent>) keyEvent -> {
            Direction direction;
            String str = keyEvent.getCode().toString();
            if(str.equals(keyBinds[0])) {
                direction = Direction.UP;
                game.setPlayerFacing(direction);
            }else if(str.equals(keyBinds[1])) {
                direction = Direction.DOWN;
                game.setPlayerFacing(direction);
            }else if(str.equals(keyBinds[2])) {
                direction = Direction.RIGHT;
                game.setPlayerFacing(direction);
            }else if(str.equals(keyBinds[3])) {
                direction = Direction.LEFT;
                game.setPlayerFacing(direction);
            }
            else if(str.equals(keyBinds[4])) {
                this.resetCounters();
                game.setPlayerFacing(Direction.DOWN);
                direction = Direction.RESTART;
            }else if(str.equals(keyBinds[5])) {
                LevelSaver.saveLevel(movesHistory, currentCampaignLevel, CompleteFieldBox.display("Enter a file name",
                        "Enter the name you want to use for the file.\nLeave blank for an automatic file name.",
                        "FileGetter name..."));
                direction = Direction.NULL;
            }else if(str.equals(keyBinds[6])) {
                String fileName = CompleteFieldBox.displayFileSelector("Enter file name", "FileGetter name :", "FileGetter name...");
                if (fileName != null && !fileName.equals("")) {
                    ArrayList<Direction> res = LevelSaver.getHistory(fileName, "");
                    if (res != null) {
                        this.game.getBoard().applyMoves(res);
                        updateMapTiles();




                    }
                }
                direction = Direction.NULL;
            }else if(str.equals(keyBinds[7])) {
                Console.open();
                direction = Direction.NULL;
            }else if(str.equals(keyBinds[8])) {
                Console.close();
                direction = Direction.NULL;
            }else {
                direction = Direction.NULL;
            }
            applyMove(direction);
        };
        prepareMoveButtons(keyEventHandler);
        prepareSaveButtons();
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

        this.leftMenu.getChildren().add(moveButton);
        this.leftMenu.getChildren().add(this.leftMenuImage);
        this.leftMenu.getChildren().addAll(
                this.moves, this.movesContainer, this.totalMovesText,
                this.pushes, this.pushesContainer, this.totalPushesText,
                this.objectives, this.objectivesContainer, this.objectivesText,
                this.time, this.timeContainer, this.timeText,
                this.restartButton, this.restartButton.overlay
        );

        this.rightMenu.getChildren().add(this.rightMenuImage);
        this.rightMenu.getChildren().addAll(
                this.currentLevelImg, this.currentLevelImgContainer, this.currentLevelText,
                this.difficultyImg, this.difficultyImgContainer, this.currentLevelDifficultyText,
                this.averageRatingImg, this.averageRatingImgContainer, this.currentLevelAverageRatingText,
                this.mainMenuButton, this.mainMenuButton.overlay, rightButton, downButton, leftButton,upButton,
                saveButton, loadSaveButton, saveButton.overlay, loadSaveButton.overlay
        );

        this.middleMenu.getChildren().addAll(this.gamePane, this.youWonText);
        this.finalPane.getChildren().addAll(this.leftMenu, this.middleMenu, this.rightMenu,this.rickRollImage);
    }

    /**
     * Set all the controls in the keyBinds table.
     * All the controls are set when a new level is loaded.
     */
    private void setControls() {
        JSONReader reader = new JSONReader("control.json");
        for (int j=0; j<9; j++){
            String tmpKey="";
            switch (j){
                case 0:
                    tmpKey = reader.getString("up");
                    break;
                case 1:
                    tmpKey = reader.getString("down");
                    break;
                case 2:
                    tmpKey = reader.getString("right");
                    break;
                case 3:
                    tmpKey = reader.getString("left");
                    break;
                case 4:
                    tmpKey = reader.getString("restart");
                    break;
                case 5:
                    tmpKey = reader.getString("savegame");
                    break;
                case 6:
                    tmpKey = reader.getString("loadsave");
                    break;
                case 7:
                    tmpKey = reader.getString("openconsole");
                    break;
                case 8:
                    tmpKey = reader.getString("closeconsole");
                    break;
            }
            keyBinds[j] = tmpKey;
        }
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
                updateMapTiles();
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
     * Prepare every image and button displaying information in the left and right <code>Panes</code>.
     */
    private void prepareInterfaces() {
        this.prepareMovesInterface();
        this.prepareObjectivesInterface();
        this.preparePushesInterface();
        this.prepareTimeInterface();
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
     */
    private void prepareMovesInterface() {
        this.moves = new CustomImage(65, 100, this.WR, this.HR, "moves.png");
        this.movesContainer = new CustomImage(85, 150, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the pushes information display.
     */
    private void preparePushesInterface() {
        this.pushes = new CustomImage(65, 210, this.WR, this.HR, "pushes.png");
        this.pushesContainer = new CustomImage(85, 260, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the amount of box on objectives information display.
     */
    private void prepareObjectivesInterface() {
        this.objectives = new CustomImage(65, 320, this.WR, this.HR, "objectives.png");
        this.objectivesContainer = new CustomImage(85, 370, this.WR, this.HR, "text container.png");
    }

    /**
     * Prepare the elapsed time since the begging of the game information display.
     */
    private void prepareTimeInterface() {
        this.time = new CustomImage(65, 430, this.WR, this.HR, "time.png");
        this.timeContainer = new CustomImage(85, 480, this.WR, this.HR, "text container.png");
    }

    /**
     *  Prepare the current level information display.
     */
    private void prepareCurrentLevelInterface() {
        this.currentLevelImg = new CustomImage(58, 100, WR, HR, "level.png");
        this.currentLevelImgContainer = new CustomImage(78, 150, WR, HR, "text container.png");
    }

    /**
     * Prepare the current level difficulty information display.
     */
    private void prepareDifficultyInterface() {
        this.difficultyImg = new CustomImage(58, 245, WR, HR, "difficulty.png");
        this.difficultyImgContainer = new CustomImage(78, 295, WR, HR, "text container.png");
    }

    /**
     * Prepare the current level average rating information display.
     */
    private void prepareAverageRatingInterface() {
        this.averageRatingImg = new CustomImage(50, 390, WR, HR, "average rating.png");
        this.averageRatingImgContainer = new CustomImage(80, 440, WR, HR, "text container.png");
    }

    /**
     * Prepare the "Restart" button to restart the game.
     */
    private void prepareRestartButton() {
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
                this.updateMapTiles();
                this.stopWatch.restart();
            }
        });
    }

    /**
     * Prepare the "Main menu" button to go back to the main menu.
     */
    private void prepareMainMenuButton() {
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
        this.totalPushesText = new Text(165 * this.WR, 295 * this.HR, String.valueOf(this.game.getTotalPushes()));
        this.totalPushesText.setFont(this.font);
        this.totalPushesText.setFill(this.color);
    }

    /**
     * Prepare the real-time changing amount of boxes on objectives information display.
     */
    private void prepareObjectivesText() {
        String objectivesStr = this.game.getBoard().getCurrBoxOnObj() + " / " + this.game.getBoard().getBoxes().size();
        this.objectivesText = new Text(135 * this.WR, 405 * this.HR, objectivesStr);
        this.objectivesText.setFont(this.font);
        this.objectivesText.setFill(this.color);
    }

    /**
     * Prepare the real-time changing elapsed time information display.
     */
    private void prepareTimeText() {
        this.timeText = new Text(105 * this.WR, 515 * this.HR, "");
        stopWatch = new StopWatch(timeText);
        this.timeText.setFont(this.font);
        this.timeText.setFill(this.color);
    }

    /**
     * Prepare the current level information display.
     */
    private void prepareCurrentLevelText() {
        if (this.currentCampaignLevel < 10) {
            this.currentLevelPosX += 10;
        }
        this.currentLevelText = new Text(this.currentLevelPosX * WR, 185 * HR, String.valueOf(currentCampaignLevel));
        this.currentLevelText.setFont(this.font);
        this.currentLevelText.setFill(this.color);
    }

    /**
     * Prepare the current level difficulty information display.
     */
    private void prepareDifficultyText() {
        this.currentLevelDifficulty = Difficulty.NORMAL;
        this.difficultyPosX = 91;
        this.currentLevelDifficultyText = new Text(this.difficultyPosX * WR, 330 * HR, String.valueOf(this.currentLevelDifficulty));
        this.currentLevelDifficultyText.setFont(this.font);
        this.currentLevelDifficultyText.setFill(this.color);
    }

    /**
     * Prepare the current level average rating information display
     */
    private void prepareAverageRatingText() {
        String averageRatingStr = (int) (this.currentLevelAverageRating) + "%";
        if ((int) (this.currentLevelAverageRating) == 1) {
            this.averageRatingPosX -= 10;
        } else if (this.currentLevelAverageRating <= 0.1) {
            this.averageRatingPosX += 10;
        }
        this.currentLevelAverageRatingText = new Text(this.averageRatingPosX * WR, 475 * HR, averageRatingStr);
        this.currentLevelAverageRatingText.setFont(this.font);
        this.currentLevelAverageRatingText.setFill(this.color);
    }

    /**
     * Prepare the congratulations message to display when the user wins.
     */
    private void prepareYouWonText(){
        this.youWonText = new Text(100*WR,  150*HR, "YOU WON !");
        this.youWonText.setFont(new Font("Microsoft YaHei", 175*HR));
        this.youWonText.setFill(Color.rgb(88, 38, 24));
        this.youWonText.setVisible(false);
    }

    /**
     * Prepare the map size and the required data according to the resolution.
     */
    private void prepareMapSize() {
        int maxWidth = this.game.getBoard().getLevelWidth();
        int limit = (int) (20 * this.WR);
        int availableSpace = (int) (1000 * WR);

        // case maxWidth <= 0 doesn't need to be checked because there will always be at least one block on every line
        if (maxWidth <= limit) {
            this.imageLength = (int) (50 * WR);
        }
        // no need to do limit < maxWidth <= 40 because maxWidth will always be > limit if we reach this point
        else if (maxWidth <= (int) ((1000 * WR) / 25)) {
            this.imageLength = (int) (availableSpace / Math.ceil(maxWidth/WR));
        }
    }

    /**
     * Update the currently displayed map layout based on the new generated <code>Board</code> and its blockList.
     * This method is used every time the user makes a move. Handles the task of showing the congratulations message
     * if the user won the game.
     */
    private void updateMapTiles() { // was previously called setMap()
        Block[][] blockList = this.game.getBoard().getBlockList();
        final int spaceConstant = (int) Math.ceil((this.imageLength)/HR);
        this.gamePane.getChildren().removeAll(this.gamePane.getChildren());

        Image blockImg = Block.airImg;
        for (int y = 0; y < this.game.getBoard().getLevelHeight(); y++) {
            for (int x = 0; x < blockList[y].length; x++) {
                Block currentItem = blockList[y][x];
                if (currentItem != null && !(currentItem instanceof Player)) {
                    blockImg = currentItem.getImage();
                } else if (currentItem != null) {  // No need to check if a player because the two possible cases are null or player
                    switch (this.game.getPlayerFacing()) {
                        case DOWN:
                            blockImg = Player.playerDownImg;
                            break;
                        case UP:
                            blockImg = Player.playerUpImg;
                            break;
                        case LEFT:
                            blockImg = Player.playerLeftImg;
                            break;
                        case RIGHT:
                            blockImg = Player.playerRightImg;
                            break;
                        default:
                            break;
                    }
                } else {
                    blockImg = Block.airImg;
                }
                CustomImage currentItemImg = new CustomImage((x * spaceConstant),(y * spaceConstant), this.WR, this.HR, blockImg);
                this.gamePane.getChildren().add(currentItemImg);
            }
        }
        if (this.game.getBoard().isWin() && !currentLevelIsWon){
            //AlertBox.display("Victory !", "You won !");
            youWonText.setVisible(true);
            currentLevelIsWon = true;
            stopWatch.stop();
            addLevel();

            if (currentMode.equals("campaign")) {
                String key = "";
                key += "c";
                key += currentCampaignLevel;

                boolean parsed = false;
                while (!parsed) {
                    String enteredString = CompleteFieldBox.display("Rating", "How would you rate this level ?", "Rating...");
                    parsed = true;
                    if (!enteredString.equals("")) {
                        try {
                            int newRating = Integer.parseInt(enteredString);
                            JSONReader jsonReader = new JSONReader("avg.json");
                            int prevRating = jsonReader.getInt(key + "r");
                            int qttRating = jsonReader.getInt(key + "q");
                            int newAverage = (int) ((prevRating * qttRating + newRating) / (qttRating + 1));

                            JSONWriter jsonWriter = new JSONWriter("avg.json");
                            jsonWriter.set(key + "r", String.valueOf(newAverage));
                            jsonWriter.set(key + "q", String.valueOf(qttRating + 1));
                        } catch (NumberFormatException exc) {
                            AlertBox.display("Error", "Could not read your number");
                            parsed = false;
                        }
                    }
                }
            }

        } else if (youWonText.isVisible()){
            youWonText.setVisible(false);
        }

        if (game.getBoard().getPlayer1().isOnPressurePlate()){
            PressurePlate plate = game.getBoard().getPlayer1().getPlate();

            if (plate.getEffect().equals("RickRoll")){
                this.rickRollImage.setVisible(true);
                this.beatPlayer.prepareMusic("secret.mp3");
                this.beatPlayer.play();

            } else if (plate.getEffect().equals("SecretMap")){
                setLevel(FileGetter.loadFile("secret.xsb","campaign"), "???","secret");
            }
        }
    }

    /**
     * set the saveButton and the loadSaveButton
     */
    private void prepareSaveButtons(){
        saveButton = new CustomButton(50, 550,WR,HR,"saveButton.png", (byte) 1);
        saveButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            LevelSaver.saveLevel(movesHistory, currentCampaignLevel, CompleteFieldBox.display("Enter a file name",
                    "Enter the name you want to use for the file.\nLeave blank for an automatic file name.",
                    "FileGetter name..."));
        });

        loadSaveButton = new CustomButton(50, 600, WR,HR,"loadSaveButton.png", (byte) 1);
        loadSaveButton.overlay.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            String fileName = CompleteFieldBox.displayFileSelector("Enter file name", "FileGetter name :", "FileGetter name...");
            if (fileName != null && !fileName.equals("")) {
                ArrayList<Direction> res = LevelSaver.getHistory(fileName, "");
                if (res != null) {
                    for (Direction dir : res) {
                        applyMove(dir);
                    }
                }
            }
        });

    }

    /**
     * Create a button for each move and assign the <code>EventHandler</code> used for the keyboard inputs.
     * @param keyEventHandler The <code>EventHandler</code> to assign to the button
     */
    private void prepareMoveButtons(EventHandler keyEventHandler) {
        this.moveButton = new Button();
        this.moveButton.setLayoutX(25*WR);
        this.moveButton.setLayoutY(25*HR);
        this.moveButton.setOnKeyPressed(keyEventHandler);

        upButton = new CustomImage(148,675,WR,HR,"upButton.png");
        upButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            game.setPlayerFacing(Direction.UP);
            applyMove(Direction.UP);
        });

        downButton = new CustomImage(148, 775,WR,HR,"downButton.png");
        downButton.addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {
            game.setPlayerFacing(Direction.DOWN);
            applyMove(Direction.DOWN);
        });

        leftButton = new CustomButton(78,725,WR,HR,"leftButton.png");
        leftButton.addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {
            game.setPlayerFacing(Direction.LEFT);
            applyMove(Direction.LEFT);
        });

        rightButton = new CustomButton(218,725,WR,HR,"rightButton.png");
        rightButton.addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {
            game.setPlayerFacing(Direction.RIGHT);
            applyMove(Direction.RIGHT);
        });
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

    /**
     * Add one level to the completed level count in the data.json file.
     */
    private void addLevel() {
        if (currentMode.equals("campaign")) {
            JSONReader reader = new JSONReader("data.json");
            int currentCompletedLevels = reader.getByte("completed levels");
            if (currentCompletedLevels == (currentCampaignLevel - 1)) {
                JSONWriter writer = new JSONWriter("data.json");
                writer.set("completed levels", String.valueOf((currentCompletedLevels + 1)));
            }
        }
    }

    /**
     *
     * @param level The <code>ArrayList</code> of Strings containing the characters making the level
     * @param name The name of the level
     * @param dest The file destination : campaign, freeplay or secret
     * destination, but "campaign" is. It will always be a coding error, it is not throwable by the user
     */
    public void setLevel(ArrayList<String> level, String name, String dest) {
        if (level != null) {
            switch (dest) {
                case "campaign":
                    this.currentCampaignLevel = Byte.parseByte(name);
                    this.currentLevelText.setText(name);
                    JSONReader jsonReader = new JSONReader("avg.json");
                    String key = "c" + this.currentCampaignLevel;
                    this.currentLevelAverageRatingText.setText(jsonReader.getString(key + "r") + "%");
                    if (currentCampaignLevel > 9) {
                        this.currentLevelText.setX((currentLevelPosX - 10) * WR);
                    } else {
                        this.currentLevelText.setX(currentLevelPosX * WR);
                    }

                    jsonReader = new JSONReader("difficulty.json");
                    String readDifficulty = jsonReader.getString(String.valueOf(currentCampaignLevel));
                    switch (readDifficulty) {
                        case "easy":
                            this.currentLevelDifficulty = Difficulty.EASY;
                            this.currentLevelDifficultyText.setX(123 * WR);
                            break;
                        case "hard":
                            this.currentLevelDifficulty = Difficulty.HARD;
                            this.currentLevelDifficultyText.setX(123 * WR);
                            break;
                        case "extreme":
                            this.currentLevelDifficulty = Difficulty.EXTREME;
                            this.currentLevelDifficultyText.setX(90 * WR);
                            break;
                        default:  // "normal" string with end up here so no need to make another case
                            this.currentLevelDifficultyText.setX(80 * WR);
                            this.currentLevelDifficulty = Difficulty.NORMAL;
                    }
                    this.currentLevelDifficultyText.setText(readDifficulty.toUpperCase());

                    break;
                case "freePlay":
                    this.currentLevelText.setX(currentLevelImgContainer.getX() + (currentLevelImgContainer.getWidth() / name.length()));
                    this.currentLevelText.setText(name);
                    this.currentLevelDifficultyText.setText("");
                    this.currentLevelAverageRatingText.setText("");
                    break;

                case "random":
                    this.currentLevelText.setX(currentLevelImgContainer.getX() + 20 * WR);
                    this.currentLevelText.setText("random");
                    this.currentLevelDifficultyText.setText("");
                    this.currentLevelAverageRatingText.setText("");
                    break;

                case "secret":
                    this.currentLevelText.setX(currentLevelImgContainer.getX() + 40 * WR);
                    this.currentLevelText.setText(name);
                    this.currentLevelDifficultyText.setText("");
                    this.currentLevelAverageRatingText.setText("");
                    break;
            }
            this.currentMode = dest;
            this.game.setBoard(new Board(level));
            this.currentLevelIsWon = false;
            this.prepareMapSize();
            this.resetCounters();
            this.updateMapTiles();
            stopWatch.restart();
            this.setControls();
        }
    }

    /**
     * Reset the counters of the amount of moves and pushes.
     */
    private void resetCounters(){
        game.getBoard().restart();
        currentLevelIsWon = false;
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
        updateMapTiles();
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