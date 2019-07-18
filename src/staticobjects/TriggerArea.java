
package staticobjects;

import helper.Animation;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;
import world.GameWorld;

/**
 * An area that triggers some transition or function at a key press when player car is within it.
 * @author DominicWild
 */
public abstract class TriggerArea extends SolidEntity {
    
    protected Prompt prompt;

    public TriggerArea(Transformable object, Sprite prompt) {
        super(object);
        this.init(prompt);
    }
    
    public TriggerArea(Transformable object, Sprite prompt, Animation areaAnimation, Vector2f spawnLocation) {
        super(object);
        this.init(prompt);
        object.setPosition(spawnLocation);
        areaAnimation.setPosition(object.getPosition());
        GameWorld.worldAnimationsLoop.add(areaAnimation);
    }
    
    private void init(Sprite prompt){
        this.prompt = new Prompt(prompt);
    }

    public abstract void triggerEffect();
    
    public void prompt(){
        this.prompt.prompt();
    }
    
    public void stopPrompt(){
        this.prompt.stopPrompt();
    }
    
    
}
