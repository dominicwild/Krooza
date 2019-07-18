package mods;


import helper.Helper;
import org.jsfml.system.Vector2f;
import handler.Assets;
import movingobjects.Car;
import movingobjects.Projectile;
import physics.PhysicsEngine;
import window.MainWindow;

/**
 * A class representing a turret on a car.
 *
 * @author DomPotts, DominicWild
 */
public class TurretMod extends BaseMod {

    protected int tier;
    protected float direction;
    protected float mass;
    protected int damage;
    protected String bulletType;
    protected ModState state;
    
    public TurretMod(int tier, String turretName, String bulletName) {
        super(turretName);
        this.tier = tier;
        this.bulletType = bulletName;
        this.state = ModState.OCCUPIED;
        this.init();
    }
    
    public TurretMod(int tier, String turretName, String bulletName, ModState state) {
        super(turretName);
        this.tier = tier;
        this.bulletType = bulletName;
        this.state = state;
        this.init();
    }
    
    private void init(){
        this.damage = 2 + tier; 
        this.mass = 2 + tier;
    }

    public void fire(float speed, Object shooter) {
        PhysicsEngine.objectsAdd.add(new Projectile(this.direction, speed, Assets.TEXTURE.get(this.bulletType), super.getPosition(), 24*speed,this.damage,shooter));
    }

    /**
     * Aims turret towards the passed position on the screen.
     * @param targetPosition The position at which the turret should point.
     */
    public void updateAngle(Vector2f targetPosition) {
        Vector2f turretPos = this.getPosition();
        this.direction = Helper.determineDirection(turretPos, targetPosition);
        Helper.setAngle(this.direction, this);
    }

    @Override
    public void apply(Car car) {
        car.setMass(car.getMass() + this.mass);
    }

    @Override
    public void reverse(Car car) {
       car.setMass(car.getMass() - this.mass);
    }
    
    public void setAngle(float angle){
        float degreeAngle = Helper.boundDegrees(angle);
        Helper.setAngle(degreeAngle, this);
        this.direction = Helper.boundDegrees(degreeAngle);
    }

    public float getDirection() {
        return direction;
    }

    @Override
    public void behave() {
        if (this.state != ModState.EMPTY) {
            this.updateAngle(MainWindow.eventHandler.getMousePosition());
        }
    }

    public static TurretMod emptyMod(){
        return new TurretMod(0,"empty","empty",ModState.EMPTY);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}
