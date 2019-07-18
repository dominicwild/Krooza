package movingobjects;

import helper.Helper;
import org.jsfml.system.Vector2f;
import mods.*;
import physics.PhysicsEngine;
import staticobjects.Carpse;
import window.MainWindow;
import world.EnemySpawner;

/**
 * An AI controlled car that attacks the target car. This cars AI will
 * repeatedly drive toward the player, mostly blindly.
 *
 * @author DominicWild
 */
public class EnemyCar extends Car {

    protected Car target;               //Used to track and follow car.
    protected float directionToPlayer;        //Tracks the angle that the player is from the car.
    private float degreesTurned;            //The number of degrees the car has turned (to stop car turning in circles)
    protected float reverseFrames;            //The number of frames to reverse (if car gets stuck).
    private int moveForwardFrames;          //Number of frames car MUST move forward without turning.
    private int bumpFrames;                 //Number of frames car has been stationary/bumping.
    protected float bumpTolerence;            //The amount of tolerence for determining if a bump occured.
    private float[] bumpData;               //Sampled speed history to determine if car is stuck.
    private int bumpIter;                   //Iterator to keep track of bump data.
    private int randDirection;              //Random direction to turn under certain conditions.
    private final int MAX_SCRAPS = 20;
    protected final int DAMAGE_FRAMES = (int) (MainWindow.FRAMERATE_TARGET*0.1);
    protected int damagedFrames;              //Amount of frames damaged sprite shows.

    public EnemyCar(Skin carBody, Car target) {
        super(carBody);
        this.target = target;
        this.degreesTurned = 0;
        this.moveForwardFrames = 0;
        this.bumpData = new float[5];
        this.bumpIter = -1;
        this.bumpTolerence = 3;
        this.reverseFrames = 0;
//        this.setMods(new FrontMod(1,"ramming_bumper"), new BackMod(1,"spike_deployer_armed",1000), new ArmourMod(1), new Engine(1), 
//                new ExhaustMod(1), new EnemyTurret("machine","worm_bolt",this), new WheelMod(1,"pixelWheel"));
        
        this.setMods(FrontMod.emptyMod(),BackMod.emptyMod(),ArmourMod.emptyMod(),Engine.emptyMod(),ExhaustMod.emptyMod(),TurretMod.emptyMod(),WheelMod.emptyMod(), null);
        this.setFrontMod(new Bumper(1));
    }

    @Override
    public void behave() {
        this.directionToPlayer = Helper.determineDirection(this.getObject().getPosition(), target.getObject().getPosition());
        this.checkBumps();
        this.accelerate();
        if (this.reverseFrames <= 0) {
            this.turnCar();
        } else {
            this.turnCarRandom();
        }
        if(!this.carBody.isRegularSkin() && this.damagedFrames <= 0){
            this.carBody.setRegularSkin();
        } else {
            this.damagedFrames--;
        }
        super.behave();
    }

    protected void turnCar() {
        if (moveForwardFrames <= 0) {
            float sign = calculateSign();
            this.degreesTurned += sign * this.handling;
            if (Math.signum(degreesTurned) != sign) { //Difference in sign, means difference in direction
                this.degreesTurned = 0; //Must reset count
            } else if (Math.abs(degreesTurned) >= 360) {
                this.moveForwardFrames = 10;
                this.degreesTurned = 0;
            }
            this.setDirection(Helper.boundDegrees(this.direction + sign * this.handling));
        } else {
            moveForwardFrames--;
        }
    }
    
    @Override
    public void destroyCar(){
       this.dropScrap();
       PhysicsEngine.objectsDelete.add(this);
       PhysicsEngine.objectsAdd.add(new Carpse("enemy_buggy_dead",this.getPosition(),this.direction,Car.getCarDeathAnimation()));
       EnemySpawner.spawnCount--;
    }
    
    protected void dropScrap(){
        int numScraps = Math.round((float)Math.random()*MAX_SCRAPS);
       Vector2f deathPos = this.getPosition();
       for(int i=0;i<numScraps;i++){
           Scrap.placeScrap(deathPos);
       }
    }

    private void turnCarRandom() {
        this.setDirection(Helper.boundDegrees(this.direction + this.randDirection * this.handling));
    }

    protected void accelerate() {
        if (this.reverseFrames > 0) {
            this.incrementSpeed(-this.acceleration);
            this.reverseFrames--;
        } else {
            this.nonReverse();
        }
    }
    
    protected void nonReverse(){
        this.incrementSpeed(this.acceleration);
    }

    private void checkBumps() {
        this.bumpIter = (this.bumpIter + 1) % this.bumpData.length;
        this.bumpData[bumpIter] = this.speed;
        if (Math.abs(Helper.getMean(bumpData)) <= this.bumpTolerence) {
            this.bumpFrames++;
        } else {
            this.bumpFrames = 0;
        }
        if (this.bumpFrames > 30) {
            this.reverseFrames = 80;
            this.randDirection = this.genRandomDirection();
        }
    }

    private int genRandomDirection() {
        if (Math.random() > 0.5) {
            return 1;
        } else {
            return -1;
        }
    }

    public float getDirectionToPlayer() {
        return directionToPlayer;
    }
    
    protected float calculateSign(){
        return Helper.findBestDirection(this.directionToPlayer,this.direction);
    }

    @Override
    public void inflictDamage(double amount) {
        super.inflictDamage(amount);
        if(!this.carBody.isDamagedSkin()){
        this.carBody.setDamagedSkin();
        }
        this.damagedFrames = DAMAGE_FRAMES;
    }
    
    

}
