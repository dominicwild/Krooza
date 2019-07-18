package menu;

import handler.SpriteHandler;
import main.Debug;
import main.DebugType;
import org.jsfml.system.Vector2f;
import window.MainWindow;

import java.util.Map;

/**
 * A Menu that is displayed when the player dies. - Dominic Wild
 * @author Alex Bentley
 */
public class GameOverMenu extends Menu {

    private static Vector2f BUTTON_SCALE = new Vector2f((float)0.35, (float)0.11);;

    public GameOverMenu(MainWindow window) {
        super(window);
        this.setupButtons();
        this.load();
        Debug.print("Game Over Menu Loaded", DebugType.LOADED);
    }

    private void setupButtons(){
        this.buttons.put("BACK", new Button("PAUSE_BB", "PAUSE_BBH"));
    }

    private void scaleButtons(){
        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;
        //Setup Buttons
        for(Map.Entry<String, Button> b : buttons.entrySet())
            SpriteHandler.scale(b.getValue(), ((height*BUTTON_SCALE.y)/b.getValue().getGlobalBounds().height) );

        float btnHeight = this.buttons.get("BACK").getGlobalBounds().height;
        float gap = (this.background.getGlobalBounds().height / 30);
        float centerY = this.window.getCamera().getCenter().y - (this.background.getGlobalBounds().height/8);

        this.buttons.get("BACK").setPosition((float)(this.window.getCamera().getCenter().x - (0.8 * this.background.getGlobalBounds().width/2)), centerY);
    }

    @Override
    protected void load() {
        this.setBackgroundTexture("GAME_OVER_BG", (float)0.8);
        this.scaleButtons();
    }

    @Override
    protected void handleClick(String btn) {
        this.window.setMenu(MenuType.MAIN);
        this.window.setWorld(null);
    }
}
