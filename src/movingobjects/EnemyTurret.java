
package movingobjects;

import helper.Helper;
import mods.TurretMod;
import staticobjects.SolidEntity;
import window.MainWindow;

/**
 * A type of turret with auto shooting capability. Used on enemies to shoot at the player.
 * @author DominicWild
 */
public class EnemyTurret extends TurretMod {
    
    private float turretHandling;           //The rate at which the turret turns.
    private SolidEntity entity;             //The entity this turret is attached to.
    private SolidEntity target;             //The target this turret should be shooting at.
    private float variation;                //The variation in the turrets aim towards the target.
    private float currentVariation;         //The current variation angle.
    private int variationFrames;            //The amount of frames until variation effects the turrets aim again.
    private final int VARIATION_FRAME_AMOUNT = (int) (MainWindow.FRAMERATE_TARGET*0.5);
    private double shootProbability = 1.0/MainWindow.FRAMERATE_TARGET;
    
    public EnemyTurret(String turretName, String bulletName, SolidEntity entity, SolidEntity target) {
        super(0, turretName,bulletName);
        this.turretHandling = 1;
        this.entity = entity;
        this.target = target;
        this.variation = 50;
        this.variationFrames = VARIATION_FRAME_AMOUNT;
        this.currentVariation = 0;
        this.damage = 20;
    }

    @Override
    public void behave() {
        this.turnTurret();
        if (entity instanceof Car) {
            Car car = (Car) entity;
            this.fire(33 + Math.abs(car.getSpeed()), car);
        } else {
            this.fire(33, entity);
        }
    }
    
    @Override
    public void fire(float speed, Object shooter) {
        double prob = Math.random();
        if(prob < shootProbability){ 
            super.fire(speed, shooter);
        }
    }

    /**
     * Turns turret to point towards the target, at a certain rate.
     */
    private void turnTurret() {
        float targetDirection = Helper.determineDirection(this.getPosition(), target.getObject().getPosition());
        if (this.variationFrames <= 0) { //If enough frames gone by without variation
            this.currentVariation = (float) ((this.variation / 2) * Math.random());
            if (Math.random() > 0.5) {
                this.currentVariation *= -1;
            }
            this.variationFrames = VARIATION_FRAME_AMOUNT;
        } else {
            this.variationFrames--;
        }
//        System.out.println(this.currentVariation);
        float sign = Helper.findBestDirection(targetDirection + this.currentVariation, this.getDirection());
        this.setAngle(this.getDirection() + sign * this.turretHandling);
    }

    public SolidEntity getTarget() {
        return target;
    }

    public void setBulletType(String bulletType) {
        this.bulletType = bulletType;
    }

    public void setShootProbability(double shootProbability) {
        this.shootProbability = shootProbability;
    }

    public void setTarget(SolidEntity target) {
        this.target = target;
    }
    
    
    
}
