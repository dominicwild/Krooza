
package staticobjects;

import handler.Assets;
import menu.MenuType;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Keyboard;
import window.MainWindow;
import world.GameWorld;

/**
 * An area in which upon key press, opens the vendor menu,
 * @author DominicWild
 */
public class VendorArea extends TriggerArea {

    public VendorArea() {
        super(new Sprite(Assets.getTexture("vendor_area")), new Sprite(Assets.getTexture("eKey")));
        this.layer = 0;
        this.prompt.setBaseScale((float)1.8);
        GameWorld.worldObjects.add(prompt);
    }

    @Override
    public void triggerEffect() {
        this.prompt();
        if (GameWorld.window.getCurrentMenu() == null && (MainWindow.eventHandler.isKeyReleased(Keyboard.Key.E))) {
            GameWorld.window.setMenu(MenuType.VENDOR);
        } 
    }
    
}
