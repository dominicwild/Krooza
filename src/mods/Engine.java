package mods;

import movingobjects.Car;

/**
 * A mod representing the engine of a Car. It adds acceleration and increases the max speed of a car.
 * @author DomPotts, DominicWild
 */
public class Engine extends BaseMod {

    private int engineModel; //a number from 0 to 8, representing the tier of engine	
    private float addedAcceleration;
    private float addedMaxSpeed;
    private ModState state;

    public Engine(int engineModel) {
        super("engine_" + engineModel);
        this.engineModel = engineModel;
        addedAcceleration = (float) (calcNewStats(engineModel)*0.05);
        addedMaxSpeed = (float) calcNewStats(engineModel);
        this.state = ModState.OCCUPIED;
    }
    
    public Engine(ModState state) {
        super("empty");
        this.engineModel = 0;
        this.addedAcceleration = 0;
        this.addedMaxSpeed = 0;
        this.state = state;
    }

 
    private float calcNewStats(int tier) {
        float newStat;
        if (tier <= 0 || tier <= 2) {
            newStat = (float) Math.log(tier + 1);
        } else if (tier <= 3 || tier <= 5) {
            newStat = (float) (Math.log((tier - 2)*4));
        } else {
            newStat = (float) Math.log((tier - 5) * (tier - 2) * 4);
        }
        return newStat;
    }

    public int getEngModel() {
        return this.engineModel;
    }

    public float getEngAcceleration() {
        return this.addedAcceleration;
    }

    public float getEngTopSpeed() {
        return this.addedMaxSpeed;
    } 
    
    @Override
    public void behave() {
        
    }

    @Override
    public void apply(Car car) {
        car.setAcceleration(car.getAcceleration() + this.addedAcceleration);
        car.setMaxSpeed(car.getMaxSpeed() + this.addedMaxSpeed);
    }

    @Override
    public void reverse(Car car) {
        car.setAcceleration(car.getAcceleration() - this.addedAcceleration);
        car.setMaxSpeed(car.getMaxSpeed() - this.addedMaxSpeed);
    }
    
    public static Engine emptyMod(){
        return new Engine(ModState.EMPTY);
    }
}
