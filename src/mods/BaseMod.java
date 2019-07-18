package mods;


import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import handler.Assets;
import movingobjects.Car;
import staticobjects.FrameBehaviour;

/**
 * A base class for all mods, collating their common variables and behaviours.
 * @author DomPotts, DominicWild
 */
public abstract class BaseMod extends Sprite implements FrameBehaviour {
    
    private String textureName;
    
    public BaseMod(String textureName) {
        super(Assets.getTexture(textureName));
        Vector2i size = super.getTexture().getSize();
        super.setOrigin(size.x / 2, size.y / 2);
        this.textureName = textureName;
    }
    
    /**
     * Apply the mods changes to the cars statistics.
     * @param car The car to change the statistics of.
     */
    public abstract void apply(Car car);
    /**
     * Reverses the changes of adding to the cars statistics.
     * @param car The car to change the statistics of.
     */
    public abstract void reverse(Car car);
   /**
    * The unique behaviour of a mod. To be triggered under specified conditions.
    */
    public void behave(){};
    
    public String getDescriptor(){
        return this.textureName;
    }
    
    public boolean equals(BaseMod m) {
        return this.textureName.equals(m.getDescriptor());
    }
    
}
