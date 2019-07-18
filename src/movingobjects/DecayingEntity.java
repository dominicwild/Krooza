
package movingobjects;

import org.jsfml.graphics.Transformable;
import physics.PhysicsEngine;
import window.MainWindow;

/**
 * An entity in the world which decays over time. This means that it disappears
 * after a specified amount of time. Created to avoid the development of lag
 * with many sprites staying on the screen.
 *
 * @author DominicWild
 */
public class DecayingEntity extends Moveable {

    private int lastingFrames;          //How many frames the sprite will last.

    /**
     * Declares an entity that will decay after a specified time, that is
     * stationary.
     *
     * @param object The object that will decay.
     * @param timeToLive The time, in seconds, for this object to decay.
     */
    public DecayingEntity(Transformable object, double timeToLive) {
        super(0, 0, object);
        this.lastingFrames = (int) (timeToLive*MainWindow.FRAMERATE_TARGET);
    }
    
    /**
     * Declares an entity that will decay after a specified time, that is able to move.
     * @param direction The direction this object is moving.
     * @param speed The speed the object is moving.
     * @param object The object that will decay.
     * @param timeToLive The time, in seconds, for this object to decay.
     */
    public DecayingEntity(float direction, float speed, Transformable object, double timeToLive) {
        super(direction,speed,object);
        this.lastingFrames = (int) (timeToLive*MainWindow.FRAMERATE_TARGET);
    }

    @Override
    public void behave() {
        if(this.lastingFrames <= 0 ){
            PhysicsEngine.objectsDelete.add(this);
        } else {
            this.lastingFrames--;
        }
    }
    
    
    
}
