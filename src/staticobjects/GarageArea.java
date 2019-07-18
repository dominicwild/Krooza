
package staticobjects;

import handler.Assets;
import menu.MenuType;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Keyboard;
import window.MainWindow;
import world.GameWorld;

/**
 * An area that can trigger transition to GarageMenu.
 * @author DominicWild
 */
public class GarageArea extends TriggerArea{

    public GarageArea() {
        super(new Sprite(Assets.getTexture("garage")), new Sprite(Assets.getTexture("eKey")));
        this.layer = -1;
        this.prompt.setBaseScale((float)1.8);
        GameWorld.worldObjects.add(prompt);
    }

    @Override
    public void triggerEffect() {
        this.prompt();
        if (GameWorld.window.getCurrentMenu() == null && (MainWindow.eventHandler.isKeyReleased(Keyboard.Key.E))) {
            GameWorld.window.setMenu(MenuType.GARAGE);
        } 
    }
    
}
