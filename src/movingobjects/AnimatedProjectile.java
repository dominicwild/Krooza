
package movingobjects;

import helper.Animation;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import world.OverWorld;

/**
 * A projectile that has an animation on collision.
 *
 * @author DominicWild
 */
public class AnimatedProjectile extends Projectile {

    private Animation onHitAnimation;
    
    public AnimatedProjectile(float direction, float speed, Texture bullet, Vector2f startPoint, float range, int damage, Object shooter, Animation onHitAnimation) {
        super(direction, speed, bullet, startPoint, range, damage, shooter);
        this.onHitAnimation = onHitAnimation;
    }

    @Override
    public void setToDisplay(boolean toDisplay) {
        super.setToDisplay(toDisplay); 
        if(!toDisplay){
            this.onHitAnimation.setPosition(this.getPosition());
            this.onHitAnimation.replay();
            OverWorld.worldAnimations.add(onHitAnimation);
        }
    }

    
    
    
    
}
