package world;

import handler.Assets;
import handler.SpriteHandler;
import org.jsfml.graphics.Sprite;

/**
 * @author Will Fantom
 */
public class Tile extends Sprite {
    //TEXTURE
    private String texture;
    //TYPE
    private TileType type;
    //POSITION
    private int xPos,yPos;
    //VISIBLE
    private boolean visible;
    private boolean occupied = false;
    
    /**
     * Creates a tile at a set position
     *
     * @param x
     * @param y
     */
    public Tile(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.setPosition(this.xPos, this.yPos);
    }

    /**
     * Sets a tile to a given texture from assets
     *
     * @param texture
     */
    public void setTexture(String texture) {
        this.setTexture(Assets.getTexture(texture));
        this.texture = texture;
        SpriteHandler.centerOrigin(this);
    }

    /**
     * Sets a tile to a given texture from assets and rotates by a set degree
     *
     * @param texture
     */
    public void setTexture(String texture, int rotation) {
        this.setTexture(Assets.getTexture(texture));
        SpriteHandler.centerOrigin(this);
        this.texture = texture;
        this.rotate(90 * rotation);
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public boolean getIsVisible() {
        return this.visible;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public String getTextureString() {
        return texture;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    

}
