package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.AlertBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapEditor {

    private Block objet;
    private Rectangle rect;

    public MapEditor(Block[][] gameBoard, int x_, int y_, int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeElem = autoSizeElem(numbElemX, numbElemY, spaceWidth, spaceHeight, WR, HR);

        gameBoard[x_][y_] = null;
        this.rect = new Rectangle(sizeElem, sizeElem);
        try {
            ImagePattern modelImage = new ImagePattern(new Image(new FileInputStream("src\\main\\resources\\img\\" + (this.objet == null ? "air.png" : this.objet.getImage()))));
            this.rect.setFill(modelImage);
        } catch (FileNotFoundException e) {
            AlertBox.display("Fatal error", "A .png file could not be found. Check if no file is missing." +
                    "Check if the names have not been changed or if any file has not been deleted. " +
                    "You can run the FileIntegrity checker for further information. Missing file : " + (this.objet == null ? "air.png" : this.objet.getImage()) + ".");
            System.exit(-1);
        }
    }

    private double autoSizeElem(int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeX = (spaceWidth / numbElemX) * WR;
        double sizeY = (spaceHeight / numbElemY) * HR;
        return Math.min(sizeX, sizeY);
    }

    public Rectangle getElem() {
        return rect;
    }

    public Block getObjet() {
        return objet;
    }

    public void setObjet(Block[][] gameBoard, Block item) {
        if(item instanceof Wall) {
            this.objet = new Wall(objet.getX(), objet.getY());
        }else if(item instanceof Player && !(item.amIOnGoal())) {
            this.objet = new Player(objet.getX(), objet.getY(), false, null);
        }else if(item instanceof Player && item.amIOnGoal()) {
            this.objet = new Player(objet.getX(), objet.getY(), true, null);
        }else if(item instanceof Box && !(item.amIOnGoal())) {
            this.objet = new Box(objet.getX(), objet.getY(), false);
        }else if(item instanceof Box && item.amIOnGoal()) {
            this.objet = new Box(objet.getX(), objet.getY(), true);
        }else if(item instanceof Goal) {
            this.objet = new Goal(objet.getX(), objet.getY());
        } else {
            // TODO : cannot do null.getX/Y() for air blocks
            gameBoard[objet.getX()][objet.getY()] = null;
            objet = null;
        }
        // TODO : cannot do null.getX/Y() for air blocks
        gameBoard[objet.getX()][objet.getY()] = objet;
        blockSetFill(rect, objet);
    }

    private void blockSetFill(Rectangle rect, Block objet) {
        try {
            ImagePattern modelImage = new ImagePattern(new Image(new FileInputStream("src\\main \\resources\\img\\" + (this.objet == null ? "air.png" : objet.getImage()))));
            rect.setFill(modelImage);
        } catch (FileNotFoundException e) {
            AlertBox.display("Fatal error", "A .png file could not be found. Check if no file is missing." +
                    "Check if the names have not been changed or if any file has not been deleted. " +
                    "You can run the FileIntegrity checker for further information. Missing file : " + this.objet.getImage() + ".");
            System.exit(-1);
        }
    }
}