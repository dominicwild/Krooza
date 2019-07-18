package mods;

import camera.HUD;
import static camera.HUD.COOL_DOWN_SCALE;
import helper.Animation;

/**
 * A class representing a mod with a cool down. Mods with a cool down are
 * assumed to a very similar sprite sheet. Having all constant values for frame
 * width and height, number of frames in the animation and number of columns in
 * their sprite sheets.
 *
 * @author DominicWild
 */
public abstract class CoolDownMod extends BaseMod {

    protected Animation coolDownAnimation;          //The animation for the cool down of this mod.
    private static int NUM_FRAMES = 5;                  //The number of frames in the animation.
    private static int NUM_COLUMNS = 1;                 //The amount of columns in the sprite sheet.

    /**
     * Creates a CoolDownMod, with an animation for its cool down.
     *
     * @param textureName The path to the texture that shows what the mod looks
     * like.
     * @param sheetPath The path to the sprite sheet used for animation.
     * @param duration The duration of the cool down.
     */
    public CoolDownMod(String textureName, String sheetPath, int duration) {
        super(textureName);
        if (!sheetPath.equals("empty")) {
            this.coolDownAnimation = new Animation(HUD.COOL_DOWN_WIDTH, HUD.COOL_DOWN_HEIGHT, NUM_COLUMNS, NUM_FRAMES, duration / NUM_FRAMES, sheetPath);
            this.coolDownAnimation.setPlayed(true);
            this.coolDownAnimation.scale(COOL_DOWN_SCALE);
            HUD.coolDownAnimations.add(coolDownAnimation);
        }
    }

}
