
package model;

public class Wall extends Block {

    private static final String Texture = "#";
    private static final String Image = "wall.png";
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
     * @return The name of the image of the wall
     */
    @Override
    public String getImage(){
        return Image;
    }
}