
package staticobjects;

import handler.Assets;
import helper.Animation;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import window.MainWindow;
import world.GameWorld;

/**
 * An area in which the transitions between worlds takes place.
 *
 * @author DominicWild
 */
public class WorldArea extends TriggerArea implements Drawable {

    private GameWorld currentWorld;
    private GameWorld transitionWorld;
    private boolean test = false;

    /**
     * For testing, a portal to another world is spawned.
     * @param transitionWorld The world to transition to.
     * @param currentWorld The current world to transition from.
     * @param spawnLocation The spawn location of this portal.
     */
    public WorldArea(GameWorld transitionWorld, GameWorld currentWorld, Vector2f spawnLocation) {
        super(new Sprite(Assets.getTexture("portal_base")), new Sprite(Assets.getTexture("eKey")),
                new Animation(560, 504, 10, 82, 50, "portal"), spawnLocation);
        this.init(currentWorld, transitionWorld);
        this.test = true;
    }
    
    public WorldArea(GameWorld transitionWorld, GameWorld currentWorld, Sprite transitionSprite){
        super(transitionSprite, new Sprite(Assets.getTexture("eKey")));
        this.init(currentWorld, transitionWorld);
        this.layer = 0;
    }
    
    private void init(GameWorld currentWorld, GameWorld transitionWorld){
        this.currentWorld = currentWorld;
        this.transitionWorld = transitionWorld;
        this.prompt.setBaseScale((float)1.8);
        GameWorld.worldObjects.add(prompt);
    }

    @Override
    public void triggerEffect() {
        this.prompt();
        if (MainWindow.eventHandler.isKeyReleased(Keyboard.Key.E)) {
            this.currentWorld.setTransitionWorld(this.transitionWorld);
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
         if(!test){
             super.draw(target, states);
         }
    }

    
    
}
