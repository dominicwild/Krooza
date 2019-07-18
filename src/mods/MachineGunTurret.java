
package mods;

/**
 * A base turret type that fires and deals damage upon collision of bullets.
 * @author DominicWild
 */
public class MachineGunTurret extends CoolDownTurret {

    public MachineGunTurret(int tier) {
        super(tier, "machine", "bullet3", 0.1 + tier*0.01);
        this.damage = 3 + tier;
    }
    
}
