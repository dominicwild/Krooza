package mods;


import movingobjects.Car;


/**
 * A class representing a base back mod. Specific back mods extend this class.
 *
 * @author DomPotts, Dominic Wild
 */
public class BackMod extends CoolDownMod {

    protected int tier;             
    protected float mass;
    protected ModState state;

    /**
     * Creates a basic back mod.
     * @param tier The tier of the mod being made.
     * @param spriteName The path to the sprite for the back mod.
     * @param coolDown The amount of time this mod is to be on cool down after activation.
     */
    public BackMod(int tier, String spriteName, int coolDown, String spriteSheetCoolDown) {
        super(spriteName,spriteSheetCoolDown,coolDown); 
        this.tier = tier;
        this.mass =  2 + tier;
        this.state = ModState.OCCUPIED;
    }
    
    public BackMod(ModState state){
        super("empty","empty",0);
        this.tier = 0;
        this.mass = 0;
        this.state = state;
    }
    
    
    
    @Override
    public void behave() {
        
    }

    @Override
    public void apply(Car car) {
        car.setMass(car.getMass() + this.mass);
    }

    @Override
    public void reverse(Car car) {
        car.setMass(car.getMass() - this.mass);
    }
    
    public static BackMod emptyMod(){
        return new BackMod(ModState.EMPTY);
    }
}
