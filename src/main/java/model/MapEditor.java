package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.AlertBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The <code>MapEditor</code> class is a class used in the <code>CreatorMenu</code>. Each square of the level is
 * a new MapEditor instance. It can change the value of its object. By using one MapEditor for each Block, we can
 * set all blocks individually.
 */
public class MapEditor {

    private Block objet;
    private final Rectangle rect;
    private final Position pos;

    /**
     * MapEditor constructor.
     * Set the size of the element, change the value of the gameBoard and create the rectangle for the MapEditor.
     * @param gameBoard The 2D table of block with all the blocks of the map.
     * @param x_ The row of the MapEditor
     * @param y_ The Line of the MapEditor
     * @param numbElemX The maximum number of Blocks in each rows.
     * @param numbElemY The maximum number of Blocks in each lines
     * @param spaceWidth The available width for the MapEditor square
     * @param spaceHeight The available height for the MapEditor square
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     */
    public MapEditor(Block[][] gameBoard, int x_, int y_, int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeElem = autoSizeElem(numbElemX, numbElemY, spaceWidth, spaceHeight, WR, HR);
        pos = new Position(x_, y_);
        gameBoard[x_][y_] = null;

        this.rect = new Rectangle(sizeElem, sizeElem);
        ImagePattern modelImage = new ImagePattern((this.objet == null ? Block.airImg : this.objet.getImage()));
        this.rect.setFill(modelImage);
    }

    /**
     * Resize the elements by dividing the available space by the number of elements.
     * @param numbElemX The maximum number of Blocks in each rows.
     * @param numbElemY The maximum number of Blocks in each lines
     * @param spaceWidth The available width for the MapEditor square
     * @param spaceHeight The available height for the MapEditor square
     * @param WR The width ratio that will be used to resize the components
     * @param HR The height ratio that will be used to resize the components
     * @return The new size for the MapEditor
     */
    private double autoSizeElem(int numbElemX, int numbElemY, double spaceWidth ,double spaceHeight, float WR, float HR) {
        double sizeX = ((spaceWidth / numbElemX) * WR);
        double sizeY = ((spaceHeight / numbElemY) * HR);
        if(sizeX <= sizeY) {
            return sizeX;
        }else {
            return sizeY;
        }
    }

    /**
     * Rectangle accessor
     * @return The rectangle of the MapEditor
     */
    public Rectangle getElem() {
        return rect;
    }

    /**
     * Object accessor.
     * @return The current object of the MapEditor
     */
    public Block getObjet() {
        return objet;
    }

    /**
     * Change the value of the object and put this new object in the gameBoard in the good position.
     * @param gameBoard The 2D table of block with all the blocks of the map.
     * @param item The new value of the Object.
     */
    public void setObjet(Block[][] gameBoard, Block item) {
        if(item instanceof Wall) {
            this.objet = new Wall(pos.getX(), pos.getY());
        }else if(item instanceof Player && !(item.amIOnGoal())) {
            this.objet = new Player(pos.getX(), pos.getY(), false, null);
        }else if(item instanceof Player && item.amIOnGoal()) {
            this.objet = new Player(pos.getX(), pos.getY(), true, null);
        }else if(item instanceof Box && !(item.amIOnGoal())) {
            this.objet = new Box(pos.getX(), pos.getY(), false);
        }else if(item instanceof Box && item.amIOnGoal()) {
            this.objet = new Box(pos.getX(), pos.getY(), true);
        }else if(item instanceof Goal) {
            this.objet = new Goal(pos.getX(), pos.getY());
        } else if (item instanceof Teleport){
            this.objet = new Teleport(pos.getX(), pos.getY(), null);
        } else {
            objet = null;
        }
        gameBoard[pos.getX()][pos.getY()] = objet;
        blockSetFill();
    }

    /**
     * Change the image of the MapEditor with the image of the object.
     */
    private void blockSetFill() {
        ImagePattern modelImage = new ImagePattern((this.objet == null ? Block.airImg : this.objet.getImage()));
        rect.setFill(modelImage);
    }
}