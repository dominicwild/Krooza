
package mods;

import handler.Assets;
import helper.Animation;
import movingobjects.AnimatedProjectile;
import physics.PhysicsEngine;

/**
 * A rocket launcher. It deals high damage, with a long scaling cool down between shots.
 * @author DominicWild
 */
public class RocketLauncher extends CoolDownTurret{
    
    private Animation missileExplosion;
    
    public RocketLauncher(int tier) {
        super(tier, "rocket_launcher", "missile", (tier*0.5));
        this.damage = this.tier*15;
        this.missileExplosion = new Animation(47,41,7,7,100,"explosion_animation");
        this.missileExplosion.scale(2);
    }

    @Override
    public void fireProjectile(float speed, Object shooter) {
        PhysicsEngine.objectsAdd.add(
                new AnimatedProjectile(this.direction, speed, Assets.TEXTURE.get(this.bulletType),
                        super.getPosition(), 24 * speed, this.damage, shooter, this.missileExplosion)
        );
    }

}
