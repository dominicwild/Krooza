
package movingobjects;

import helper.Helper;
import helper.Player;
import mods.FrontMod;
import mods.Skin;
import staticobjects.SolidEntity;
import window.MainWindow;
import world.GameWorld;

/**
 * A enemy car which heals other enemy cars. It will target the car with the
 * lowest HP, prioritizing other enemy healers.
 *
 * @author DominicWild
 */
public class EnemyHealer extends EnemyRangedCar {

    private boolean evade = false;

    public EnemyHealer() {
        super(Player.car);
        this.setSkin(new Skin("enemy_ambulance","enemy_ambulance_red"));
        this.decideTarget();
        this.turret.setDamage(-(2));
        EnemyTurret t = (EnemyTurret)this.turret;
        t.setBulletType("bullet_heal");
        t.setShootProbability(2.0/MainWindow.FRAMERATE_TARGET);
        this.setMaxHealth(this.getMaxHealth() + 50);
        this.setToMaxHP();
        this.setFrontMod(FrontMod.emptyMod());

    }
    
    private void decideTarget(){
        float lowestHealth = (float) 1.01; //As a percentage
        EnemyCar preferredTarget = null;
        for(SolidEntity entity : GameWorld.worldObjects){
            if(entity instanceof EnemyCar && entity != this){
                EnemyCar thisCar = (EnemyCar)entity;
                float thisHealth = thisCar.getHealthPercent();
                if(thisCar.getClass() == EnemyHealer.class){
                    thisHealth -= 0.1; //Give priority to other healers
                }
                if(thisHealth < lowestHealth){
                    lowestHealth = thisHealth;
                    preferredTarget = thisCar;
                }
            }
        }
        if (preferredTarget != null) {
//            System.out.println("Found Enemy target!");
            this.setTarget(preferredTarget);
            this.evade = false;
        } else {
            this.setTarget(Player.car);
            this.evade = true;
        }
    }

    /**
     * Find the worst direction if we're in evasive mode, to get away from the
     * player.
     *
     * @return The worst or best direction sign, depending on if we're evading.
     */
    @Override
    protected float calculateSign() {
        if (evade) {
            return Helper.findBestDirection(this.directionToPlayer, Helper.boundDegrees(this.direction + 180));
        } else {
            return super.calculateSign();
        }
    }

    @Override
    protected void nonReverse() {
        if (evade && Helper.getDistanceToPoint(this.getPosition(), Player.car.getPosition()) > 1000) {
            if (Math.abs(this.speed) < 0.1) {
                this.speed = 0;
            } else {
                this.incrementSpeed(-this.acceleration);
            }
        }
        this.incrementSpeed(this.acceleration);
    }



    @Override
    public void behave() {
        this.decideTarget();
        super.behave();
    }
    
    
    
    public void setTarget(Car target){
        this.target = target;
        EnemyTurret t = (EnemyTurret) this.turret;
        t.setTarget(target);
    }

    
    

}
