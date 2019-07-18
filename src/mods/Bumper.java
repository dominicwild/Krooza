package mods;

import movingobjects.Car;

/**
 * A bumper front mod. This mod will introduce ramming damage. When the front of
 * the car, hits another car, damage will be dealt.
 *
 * @author DominicWild
 */
public class Bumper extends FrontMod{

    private double damage;              //The amount of damage a RAM does.

    public Bumper(int tier) {
        super(tier,"ramming_bumper");
        this.damage = 40 + tier*2;
    }
    
    @Override
    public void collide(Car other){
        other.inflictDamage(damage);
    }
    
}
