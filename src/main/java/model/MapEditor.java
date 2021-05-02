package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.io.FileNotFoundException;

public class MapEditor {

    private Block objet;
    private Rectangle rect;

    public MapEditor(Block[][] gameBoard, int x_, int y_, int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeElem = autoSizeElem(numbElemX, numbElemY, spaceWidth, spaceHeight, WR, HR);

        gameBoard[x_][y_] = null;
        this.rect = new Rectangle(sizeElem, sizeElem);
        try{
            ImagePattern modelImage = new ImagePattern(new Image(new FileInputStream("src\\main\\resources\\img\\" + this.objet.getImage())));
            this.rect.setFill(modelImage);
        }catch(FileNotFoundException fileExcep) {
            fileExcep.printStackTrace();
        }
    }

    private double autoSizeElem(int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeX = (spaceWidth / numbElemX) * WR;
        double sizeY = (spaceHeight / numbElemY) * HR;
        if(sizeX < sizeY) {
            return sizeX;
        }else {
            return sizeY;
        }
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
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else if(item instanceof Player && !(item.amIOnGoal())) {
            this.objet = new Player(objet.getX(), objet.getY(), false, null);
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else if(item instanceof Player && item.amIOnGoal()) {
            this.objet = new Player(objet.getX(), objet.getY(), true, null);
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else if(item instanceof Box && !(item.amIOnGoal())) {
            this.objet = new Box(objet.getX(), objet.getY(), false);
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else if(item instanceof Box && item.amIOnGoal()) {
            this.objet = new Box(objet.getX(), objet.getY(), true);
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else if(item instanceof Goal) {
            this.objet = new Goal(objet.getX(), objet.getY());
            gameBoard[objet.getX()][objet.getY()] = objet;
            blockSetFill(rect, objet);
        }else {
            gameBoard[objet.getX()][objet.getY()] = null;
            this.objet = null;
            blockSetFill(rect, objet);
        }
    }

    private void blockSetFill(Rectangle rect, Block objet) {
        try{
            ImagePattern modelImage = new ImagePattern(new Image(new FileInputStream("src\\main\\resources\\img\\" + objet.getImage())));
            rect.setFill(modelImage);
        }catch(FileNotFoundException fileExcep) {
            fileExcep.printStackTrace();
        }
    }
}