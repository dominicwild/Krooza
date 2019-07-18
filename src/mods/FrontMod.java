package mods;

import movingobjects.Car;

/**
 * A mod representing the base functionality for every front mod.
 *
 * @author DomPotts, DominicWild
 */
public class FrontMod extends BaseMod {

    protected int tier; //Tier of mod, determines stat differentiations. 
    protected float damage; //A value that is used to calculate collision damage
    protected float mass;

    public FrontMod(int tier, String imageName) {
        super(imageName);
        this.tier = tier;
    }

    @Override
    public void apply(Car car) {
        car.setMass(car.getMass() + this.mass);
    }

    @Override
    public void behave() {
        
    }
    
    public void collide(Car other){
        
    }

    @Override
    public void reverse(Car car) {
        car.setMass(car.getMass() - this.mass);
    }
    
    public static FrontMod emptyMod(){
        return new FrontMod(0,"empty");
    }
}
