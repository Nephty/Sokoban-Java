
package model;

public class Wall extends Block {
    /**
     * <code>Wall</code> Constructor
     * @param x_ The row of the <code>Wall</code> in the blockList
     * @param y_ The line of the <code>Wall</code> in the blockList
     * @param image The name of the image of the <code>Wall</code>
     * @param texture The texture of the wall (Texture : #)
     */
    public Wall(int x_, int y_,String image,String texture) {
        super(x_, y_,image, texture);
    }
}