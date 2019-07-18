package movingobjects;

import static java.lang.Float.NaN;

import helper.Helper;
import org.jsfml.graphics.Transformable;
import staticobjects.SolidEntity;

/**
 * A class representing any movable object within the world.
 * @author DominicWild, Alex Bentley
 */
public class Moveable extends SolidEntity {

    protected float direction;            //The direction the object will move in, in degrees.
    protected float speed;                //The rate at which the object moves per frame.
    protected float mass;                 //The mass of this Moveable.

    public Moveable(float direction, float speed, Transformable object) {
        super(object);
        this.direction = direction;
        this.speed = speed;
        this.mass = 1; 
    }

    /**
     * Moves the object in its appointed direction.
     */
    public void moveForward() {
        object.move(Helper.calculateDirectionVector(-direction, speed));
    }

    /**
     * Edit the velocity of this object (and the one it is colliding with) to
     * model the result of a collision.
     *
     * @param entity The other Moveable that is involved in this collision.
     */
    public void collide(SolidEntity entity) {
        
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDirection(float direction) {
        if (direction != NaN) {
            this.direction = direction;
            Helper.setAngle(direction, object);
        }
    }
    
    public float getMass() {
        return this.mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getDirection() {
        return direction;
    }
    
    

    

}
