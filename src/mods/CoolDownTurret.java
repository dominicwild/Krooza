
package mods;

import window.MainWindow;

/**
 * A turret with a cool down between shots.
 * @author DominicWild
 */
public class CoolDownTurret extends TurretMod{

    private int coolDownFrames;
    private final double COOL_DOWN_TIME;            //Cool down time in seconds.
    
    /**
     * Creates a turret that has a cool down associated with its fire function.
     * @param tier The tier of this turret, effects statistics.
     * @param turretName The name of the sprite that represents this turret.
     * @param bulletName The name of the bullet sprite this turret uses.
     * @param coolDownTime The cool down time in seconds this turret can fire between.
     */
    public CoolDownTurret(int tier, String turretName, String bulletName, double coolDownTime) {
        super(tier, turretName, bulletName);
        this.coolDownFrames = 0;
        this.COOL_DOWN_TIME = coolDownTime;
    }

    @Override
    public void behave() {
        super.behave(); 
        if(this.coolDownFrames > 0){
            coolDownFrames--;
        }
    }

    
    
    @Override
    public void fire(float speed, Object shooter) {
        if (coolDownFrames <= 0) {
           fireProjectile(speed,shooter);
           this.coolDownFrames = (int) (COOL_DOWN_TIME * MainWindow.FRAMERATE_TARGET);
        } 
    }
    
    protected void fireProjectile(float speed, Object shooter) {
        super.fire(speed, shooter);
    }

    
    
}
