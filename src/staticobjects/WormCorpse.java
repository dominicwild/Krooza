
package staticobjects;

import helper.Animation;
import org.jsfml.system.Vector2f;

/**
 * A special case of a corpse for the worm. Needed to disable the functionality of aligning the dead body.
 * @author DominicWild
 */
public class WormCorpse extends Carpse{

    public WormCorpse(Vector2f spawnLocation) {
        super("worm_dead",spawnLocation,0,new Animation(150,150,4,4,200,"worm_death"),true);
    }

    @Override
    protected void align(float direction) {
        
    }
}
