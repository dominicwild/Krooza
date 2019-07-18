
package mods;

import org.jsfml.graphics.Texture;
import handler.Assets;
import movingobjects.Car;

/**
 * A skin that changes depending on "Context". In this case context refers to
 * the amount of damage the player has taken.
 *
 * @author DominicWild
 */
public class ContextSkin extends Skin {

    private Texture baseSkin;           //Sprite shown by default.
    private Texture halfHealthCar;           //Sprite shown when car is half health.
    private Texture quarterHealthCar;        //Sprite shown when car is quarter health.

    /**
     * Creates a skin with contextual display of 3 health levels.
     *
     * @param baseSkin What sprite to be displayed at full health.
     * @param halfHealth What sprite to be displayed at half health.
     * @param quarterHealth What sprite to be displayed at quarter health.
     */
    public ContextSkin(String baseSkin, String halfHealth, String quarterHealth) {
        super(baseSkin);
        this.baseSkin = Assets.TEXTURE.get(baseSkin);
        this.halfHealthCar = Assets.TEXTURE.get(halfHealth);
        this.quarterHealthCar = Assets.TEXTURE.get(quarterHealth);
    }
    
    private void updateSprite(Car skinnedCar){
        double percentHealth = skinnedCar.getHealth()/skinnedCar.getMaxHealth();
        if(percentHealth > 0.5){
            this.setTexture(this.baseSkin);
        } else if(percentHealth > 0.25){
            this.setTexture(this.halfHealthCar);
        } else {
            this.setTexture(this.quarterHealthCar);
        }
    }
    
    @Override
    public void apply(Car car) {
        updateSprite(car);
    }
    
}
