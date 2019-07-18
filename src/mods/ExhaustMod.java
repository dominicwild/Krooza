package mods;

import movingobjects.Car;

/**
 * A mod representing the boost functionality applied to a car.
 *
 * @author DomPotts, DominicWild
 */
public class ExhaustMod extends CoolDownMod {

    private int exhaustTier; //number 0 to 2 representing tier of exhaust.
    private float addedSpeed; //added acceleration value for the car whilst boosting.
    private float addedMaxSpeed; //added Top Speed value for the car whilst boosting.
    private int exhaustDuration; //Number of milliseconds the boost is active for.
    private ModState state;

    public ExhaustMod(int exhaustTier) {
        super("exhaust_" + exhaustTier, "ExhaustCoolDownAnimation", 5000); 
        this.exhaustTier = exhaustTier;
        this.addedSpeed = exhaustTier * 4;
        this.addedMaxSpeed = 15 + 6 * exhaustTier;
        this.exhaustDuration = (1 + exhaustTier) * 500;
        this.state = ModState.OCCUPIED;
    }
    
    public ExhaustMod(ModState state){
        super("empty","empty",0);
        this.exhaustTier = 0;
        this.addedMaxSpeed = 0;
        this.addedSpeed = 0;
        this.exhaustDuration = 0;
        this.state = state;
    }

    public int getExhaustTier() {
        return this.exhaustTier;
    }

    public float getAddedSpeed() {
        return this.addedSpeed;
    }

    public float getExhaustMaxSpeed() {
        return this.addedMaxSpeed;
    }

    public int getExhaustDuration() {
        return this.exhaustDuration;
    }

    @Override
    public void apply(Car car) {
        car.setSpeed(car.getSpeed() + this.addedSpeed);
        car.setMaxSpeed(car.getMaxSpeed() + this.addedMaxSpeed);
    }

    @Override
    public void behave() {

    }

    @Override
    public void reverse(Car car) {
        car.setMaxSpeed(car.getMaxSpeed() - this.addedMaxSpeed);
    }

    public void activateCoolDown() {
        this.coolDownAnimation.replay();
    }
    
    public boolean onCoolDown(){
        return !this.coolDownAnimation.isFinished();
    }
    
    public static ExhaustMod emptyMod(){
        return new ExhaustMod(ModState.EMPTY);
    }
}
