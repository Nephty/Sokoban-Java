
package model;

import javafx.scene.image.Image;

/**
 * The <code>Wall</code> is a special <code>Block</code> of the game. If the player want to go in the direction of the wall,
 * he doesn't move.
 */
public class Wall extends Block {

    private static final String Texture = "#";
    private static final Image wallImg = loadImg("wall.png");
    /**
     * <code>Wall</code> Constructor
     * @param x_ The row of the <code>Wall</code> in the blockList
     * @param y_ The line of the <code>Wall</code> in the blockList
     */
    public Wall(int x_, int y_) {
        super(x_, y_);
    }

    /**
     * Texture accessor
     * @return The Texture of the Wall
     */
    @Override
    public String getTexture(){
        return Texture;
    }

    /**
     * Image accessor
     * @return The Image of the Wall (Image object)
     */
    @Override
    public Image getImage(){
        return wallImg;
    }
}