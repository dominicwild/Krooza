
package mods;

import handler.Assets;
import movingobjects.Car;
import org.jsfml.graphics.Texture;

/**
 * A base skin for a car. Updates based on if damage sustained.
 *
 * @author DominicWild
 */
public class Skin extends BaseMod {

    private Texture damagedSkin;
    private Texture regularSkin;
    
    /**
     * Creates a skin for a car.
     *
     * @param baseSkin What sprite to be displayed by default.
     */
    public Skin(String baseSkin) {
        super(baseSkin);
        this.regularSkin = Assets.getTexture(baseSkin);
        this.damagedSkin = this.regularSkin;
    }
    
    /**
     * Creates a skin with contextual display of 2 states, damaged and not-damaged.
     *
     * @param baseSkin What sprite to be displayed by default.
     * @param damagedSkin What sprite to be displayed on taking damage.
     */
    public Skin(String baseSkin, String damagedSkin) {
        super(baseSkin);
        this.regularSkin = Assets.getTexture(baseSkin);
        this.damagedSkin = Assets.getTexture(damagedSkin);
    }

    @Override
    public void reverse(Car car) {
       
    }

    @Override
    public void apply(Car car) {
        
    }
    
    public void toggleSkin(){
        if(this.regularSkin.equals(this.getTexture())){
            this.setTexture(this.damagedSkin);
        } else {
            this.setTexture(this.regularSkin);
        }
    }
    
    public void setDamagedSkin(){
        this.setTexture(this.damagedSkin);
    }
    
    public void setRegularSkin(){
        this.setTexture(this.regularSkin);
    }
    
    public boolean isRegularSkin(){
       return this.getTexture().equals(this.regularSkin);
    }
    
    public boolean isDamagedSkin(){
       return this.getTexture().equals(this.damagedSkin);
    }
    
    
    
}
