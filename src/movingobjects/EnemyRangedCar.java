package movingobjects;

import helper.Animation;
import helper.Helper;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import mods.Skin;
import physics.PhysicsEngine;
import staticobjects.Carpse;
import world.EnemySpawner;
import world.GameWorld;

/**
 * A car that chases the target from a distance and shoots projectiles.
 *
 * @author DominicWild
 */
public class EnemyRangedCar extends EnemyCar {
    
    private float directionToDrive;             //The direction this car should drive.     
    private double distanceFromPoint;            //The distance from the point the car is chasing.
    private double slowDownTolerence;           //Tolerance (Of distance) around which the car should begin to consider matching speeds.
    private double catchTolerence;              //Tolerence (Of speed) by which car should consider changing matched speed.
    private boolean manualSlow;                 //Indicates if cars slow speed is intentional.
    private Vector2f currentChasePoint;         //Point currently being chased.
    
    public EnemyRangedCar(Car target) {
        super(new Skin("enemy_truck","enemy_truck_red"), target);
        this.directionToDrive = 0;
        this.distanceFromPoint = 0;
        this.slowDownTolerence = 3;
        this.catchTolerence = 3;
        this.manualSlow = false;
        this.setTurret(new EnemyTurret("machine","worm_bolt",this,this.target));
    }
    
    @Override
    public void behave() {
        this.directionToDrive = closestChasePoint();
        super.behave();
    }
    
    private float closestChasePoint() {
        Vector2f[] chasePoints = this.target.getChasePoints();
        Vector2f position = this.getObject().getPosition();
        Vector2f[] delta = new Vector2f[]{Vector2f.sub(position, chasePoints[0]),
            Vector2f.sub(position, chasePoints[1])};
        double[] distances = new double[]{Math.hypot(delta[0].x, delta[0].y), Math.hypot(delta[1].x, delta[1].y)};
        if (distances[0] < distances[1]) {
            this.distanceFromPoint = distances[0];
            this.currentChasePoint = chasePoints[0];
            return Helper.determineDirection(position, chasePoints[0]);
        } else {
            this.distanceFromPoint = distances[1];
            this.currentChasePoint = chasePoints[1];
            return Helper.determineDirection(position, chasePoints[1]);
        }
    }
    
    protected void accelerate() {
        if (this.reverseFrames > 0 && !this.manualSlow) {
            this.incrementSpeed(-this.acceleration);
            this.reverseFrames--;
        } else if (this.distanceFromPoint <= (target.getSize().x * this.slowDownTolerence)) {
            this.regressToTargetSpeed();
        } else {
            this.nonReverse();
            this.manualSlow = false;
        }
    }
    
    private void regressToTargetSpeed() {
        double speedDifference = Math.abs(this.speed - target.getSpeed());
        if(speedDifference < this.catchTolerence){
            float sign = this.findSignToPoint();
            this.incrementSpeed(this.acceleration*sign);
        } else if (target.getSpeed() < this.speed) {
            this.incrementSpeed(-this.acceleration);
        } else {
            this.incrementSpeed(this.acceleration);
        }
        if(this.speed <= this.bumpTolerence){
            this.manualSlow = true;
        }
    }

    @Override
    protected float calculateSign() {
        return Helper.findBestDirection(this.directionToDrive, this.direction);
    }
    
    private float findSignToPoint(){
        Vector2f directionVector = Helper.calculateDirectionVector(this.direction, this.speed);
        double beforeDistance = Helper.getDistanceToPoint(this.getPosition(),this.currentChasePoint);
        double afterDistance = Helper.getDistanceToPoint(Vector2f.add(this.getPosition(), directionVector),this.currentChasePoint);
        if(Math.abs(beforeDistance - afterDistance) < 100){
            return 0;
        } else if(beforeDistance < afterDistance){
            return Math.signum(this.speed)*-1;
        } else {
            return Math.signum(this.speed);
        }
    }
    
    @Override
    public void destroyCar(){
       this.dropScrap();
       PhysicsEngine.objectsDelete.add(this);
       PhysicsEngine.objectsAdd.add(new Carpse("enemy_truck_dead",this.getPosition(),this.direction,Car.getCarDeathAnimation()));
       EnemySpawner.spawnCount--;
    }
    
}
