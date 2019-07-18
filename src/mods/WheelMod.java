package mods;

import movingobjects.Car;

/**
 * A base wheel mode class to make wheel mods from.
 *
 * @author DominicWild, DomPotts
 */
public class WheelMod extends BaseMod {

    protected int tier;
    protected float addedCarHandling;
    protected float addedCarAcceleration;
    protected float addedMaxSpeed;

    public WheelMod(int tier, String wheelName) {
        super(wheelName);
        this.tier = tier;
        this.addedCarAcceleration = (float) (0.05 + 0.01*tier);
        this.addedCarHandling = tier;
        this.addedMaxSpeed = 5 + tier;
    }

    @Override
    public void apply(Car car) {
        car.setMaxHandling(car.getMaxHandling() + this.addedCarHandling);
        car.setAcceleration(car.getAcceleration() + this.addedCarAcceleration);
        car.setMaxSpeed(car.getMaxSpeed() + this.addedMaxSpeed);
    }

    @Override
    public void behave() {
        
    }

    @Override
    public void reverse(Car car) {
        car.setMaxHandling(car.getMaxHandling() - this.addedCarHandling);
        car.setAcceleration(car.getAcceleration() - this.addedCarAcceleration);
        car.setMaxSpeed(car.getMaxSpeed() - this.addedMaxSpeed);
    }
    
    public static WheelMod emptyMod(){
        return new WheelMod(0,"empty");
    }
}
