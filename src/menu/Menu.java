package menu;

import handler.Assets;
import handler.SpriteHandler;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Mouse;
import window.MainWindow;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jsfml.system.Vector2f;

/**
 * A base class for a Menu. Holds general functionality all Menus have such as
 * having buttons. - Dominic Wild
 *
 * @author Will Fantom, DominicWild
 */
public abstract class Menu {

    //Window
    protected MainWindow window;

    //Content
    protected Sprite background;
    protected ArrayList<String> sound;
    public ConcurrentHashMap<String, Button> buttons;

    public Menu(MainWindow window) {
        //Window
        this.window = window;
        //Setup
        this.setupContent();
    }

    private void setupContent() {
        this.background = new Sprite();
        this.sound = new ArrayList<String>();
        this.buttons = new ConcurrentHashMap<String, Button>();
    }

    protected void setBackgroundTexture(String texture, float scale) {
        this.background.setTexture(Assets.getTexture(texture));
        SpriteHandler.centerOrigin(this.background);
        SpriteHandler.scale(this.background, (this.window.getCamera().getSize().x / this.background.getGlobalBounds().width) * scale);
        this.background.setPosition(this.window.getCamera().getCenter().x, this.window.getCamera().getCenter().y);
    }
    
    protected void fitBackgroundTexture(String texture){
        this.background.setTexture(Assets.getTexture(texture));
        Vector2f windowSize = this.window.getCamera().getSize();
        Vector2f backgroundSize = new Vector2f(this.background.getTexture().getSize());
        float scaleX = windowSize.x/backgroundSize.x;
        float scaleY = windowSize.y/backgroundSize.y;
        this.background.setScale(scaleX,scaleY);
        SpriteHandler.centerOrigin(this.background);
        this.background.setPosition(this.window.getCamera().getCenter().x, this.window.getCamera().getCenter().y);
    }

    abstract protected void load();

    public void draw() {
        this.window.draw(this.background);
        for (Map.Entry<String, Button> b : buttons.entrySet()) {
            FloatRect bounds = b.getValue().getGlobalBounds();
            if (bounds.contains(this.window.mapPixelToCoords(Mouse.getPosition(this.window)))) {
                b.getValue().setHovering(true);
                if (this.window.getEventHandler().isMouseReleasedL()) {
                    this.handleClick(b.getKey());
                }
            } else {
                b.getValue().setHovering(false);
            }
            this.window.draw(b.getValue());
        }
        if (MainMenu.isCredits) {
            float height = this.window.getCamera().getSize().y;
            float width = this.window.getCamera().getSize().x;
            this.buttons.clear();
            this.buttons.put("CIMG", new Button("MENU_CREDITS", "MENU_CREDITS"));
            SpriteHandler.scale(this.buttons.get("CIMG"), ((width * MainMenu.CREDITS_SCALE.x) / this.buttons.get("CIMG").getGlobalBounds().width), ((height * MainMenu.CREDITS_SCALE.y) / this.buttons.get("CIMG").getGlobalBounds().height));
            this.buttons.get("CIMG").setPosition(this.window.getCamera().getCenter().x, this.window.getCamera().getCenter().y);
        }
    }

    abstract protected void handleClick(String btn);

}
