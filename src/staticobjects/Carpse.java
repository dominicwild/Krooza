package staticobjects;

import movingobjects.DecayingEntity;
import handler.Assets;
import helper.Animation;
import helper.Helper;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import world.GameWorld;

/**
 * A corpse of a car: A carpse. However also used for any sort of corpse.
 *
 * @author DominicWild
 */
public class Carpse extends DecayingEntity {

    private boolean animationDelay = false;         //Determines whether to have animation delay set for carpse appearance.
    private boolean toDraw = true;                  //Determines whether to draw this carpse or not.
    private Animation deathAnimation;               //The death animation for this carpse.

    public Carpse(String carpseSprite, Vector2f spawnLocation, float direction, Animation deathAnimation) {
        super(new Sprite(Assets.TEXTURE.get(carpseSprite)), 15);
        this.init(spawnLocation, direction, deathAnimation);
    }

    public Carpse(String carpseSprite, Vector2f spawnLocation, float direction, Animation deathAnimation, boolean animationDelay) {
        super(new Sprite(Assets.TEXTURE.get(carpseSprite)), 15);
        this.init(spawnLocation, direction, deathAnimation);
        this.animationDelay = animationDelay;
    }

    private void init(Vector2f spawnLocation, float direction, Animation deathAnimation) {
        this.object.setPosition(spawnLocation);
        this.deathAnimation = deathAnimation;
        this.layer = 0;
        this.deathAnimation.setPosition(spawnLocation);
        this.align(direction);
        GameWorld.worldAnimations.add(deathAnimation);
    }
    
    protected void align(float direction){
        this.deathAnimation.setAngle(direction);
        Helper.setAngle(direction, object);
    }

    @Override
    public void behave() {
        super.behave();
        if (animationDelay) {
            this.toDraw = this.deathAnimation.isFinished();
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        if (toDraw) {
            super.draw(target, states);
        }
    }

}
