package movingobjects;


import helper.Helper;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import physics.PhysicsEngine;
import staticobjects.SandWorm;
import staticobjects.SolidEntity;

/**
 * A class that models a projectile. It is a sprite that constantly moves forward for a set range, until it disappears.
 * @author Alex Bentley, DominicWild
 */
public class Projectile extends Moveable {

    private Vector2f trajectory;
    private float range;
    private boolean toDisplay;
    private int damage;
    private Object shooter;

    public Projectile(float direction, float speed, Texture bullet, Vector2f startPoint, float range, int damage, Object shooter) {
        super(direction, speed, new Sprite(bullet));
        this.toDisplay = true;
        this.range = range;
        this.damage = damage;
        this.trajectory = Helper.calculateDirectionVector(-direction, speed);
        this.shooter =  shooter;
        Sprite bulletSprite = (Sprite) this.object;
        Helper.setAngle(direction, bulletSprite);
        bulletSprite.setOrigin(bullet.getSize().x / 2, bullet.getSize().y / 2); //Origin in middle
        bulletSprite.setPosition(startPoint.x, startPoint.y);
    }

    
    
    @Override
    public void collide(SolidEntity entity) {
        if (!entity.equals(shooter)) {
            if (entity.getClass() == Projectile.class) {
                this.projectileCollide((Projectile) entity);
            } else if (entity instanceof Car) {
                this.carCollide((Car) entity);
            } else if(entity.getClass() == SolidEntity.class){
                this.solidEntityCollide(entity);
            } else if(entity.getClass() == SandWorm.class){
                this.wormCollide((SandWorm)entity);
            }
        }
    }
    
    private void wormCollide(SandWorm worm){
        worm.inflictDamage(damage);
        this.solidEntityCollide(worm);
    }
    
    private void solidEntityCollide(SolidEntity ent){
        PhysicsEngine.objectsDelete.add(this);
        this.setToDisplay(false);
    }

    private void projectileCollide(Projectile p) {
        p.setToDisplay(false);
        PhysicsEngine.objectsDelete.add(this);
        this.setToDisplay(false);
    }

    private void carCollide(Car c) {
        c.inflictDamage(this.damage);
        PhysicsEngine.objectsDelete.add(this);
        this.setToDisplay(false);
    }

    @Override
    public void moveForward() {
        this.move(trajectory);
        this.range -= this.speed;
    }

    /**
     * Moves this projectile forward with its calculated trajectory.
     *
     * @param trajectory vector describing the Projectiles trajectory.
     */
    public void move(Vector2f trajectory) {
        object.move(trajectory);
    }

    public void update() {
        if (range >= 0) {
            this.toDisplay = true;
        } else {
            this.toDisplay = false;
        }
    }

    public boolean isToDisplay() {
        return toDisplay;
    }
    
    @Override
    public void behave() {
        this.moveForward();
        if(!this.toDisplay){
            PhysicsEngine.objectsDelete.add(this);
        }
        this.update();
    }

    public void setToDisplay(boolean toDisplay) {
        this.toDisplay = toDisplay;
    }

    public int getDamage() {
        return damage;
    }
    
    
    
}
