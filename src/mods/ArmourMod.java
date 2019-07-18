package mods;


import movingobjects.Car;

/**
 * Armour that scales the cars health in respect to the tier of armour applied.
 * 
 * @author DomPotts, DominicWild
 */
public class ArmourMod extends BaseMod {

    private int armourTier;
    private int addedHealth;
    private float mass;
    private ModState state;

    public ArmourMod(int armourTier) {
        super("armour_" + armourTier);
        armourTier = armourTier;
        addedHealth =  100 * (armourTier + 1);
        mass = 2 + armourTier*3;
    }
    
    public ArmourMod(ModState state) {
        super("empty");
        armourTier = 0;
        addedHealth =  0;
        mass = 0;
        this.state = state;
    }

    public int getArmourTier() {
        return this.armourTier;
    }

    public float getCarHP() {
        return this.addedHealth;
    }

    public float getArmourWeight() {
        return this.mass;
    }

    @Override
    public void behave() {
        
    }

    @Override
    public void apply(Car car) {
        car.setHealth((car.getHealth() + this.addedHealth));
        car.setMaxHealth(car.getMaxHealth() + this.addedHealth);
        car.setMass(car.getMass() + this.mass);
    }

    @Override
    public void reverse(Car car) {
        car.setHealth((car.getHealth() - this.addedHealth));
        car.setMaxHealth(car.getMaxHealth() - this.addedHealth);
        car.setMass(car.getMass() - this.mass);
    }
    
    public static ArmourMod emptyMod(){
        return new ArmourMod(ModState.EMPTY);
    }
}
