package menu;

import handler.SpriteHandler;
import main.Debug;
import main.DebugType;
import org.jsfml.system.Vector2f;
import window.MainWindow;

import java.util.Map;

/**
 * A menu that can be displayed to pause the game. Works in any game mode by
 * pressing ESC. - Dominic Wild
 *
 * @author Will Fantom
 */
public class PauseMenu extends Menu {

    private static Vector2f BUTTON_SCALE = new Vector2f((float) 0.35, (float) 0.11);

    ;

    public PauseMenu(MainWindow window) {
        super(window);
        this.setupButtons();
        this.load();
        Debug.print("Pause Menu Loaded", DebugType.LOADED);
    }

    private void setupButtons(){
        this.buttons.put("SAVE", new Button("PAUSE_SB", "PAUSE_SBH"));
        this.buttons.put("MAINMENU", new Button("PAUSE_MB", "PAUSE_MBH"));
        this.buttons.put("BACK", new Button("PAUSE_BB", "PAUSE_BBH"));
    }

    private void scaleButtons(){
        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;
        //Setup Buttons
        for(Map.Entry<String, Button> b : buttons.entrySet())
            SpriteHandler.scale(b.getValue(), ((height*BUTTON_SCALE.y)/b.getValue().getGlobalBounds().height) );
        
        float btnHeight = this.buttons.get("SAVE").getGlobalBounds().height;
        float gap = (this.background.getGlobalBounds().height / 30);
        float centerY = this.window.getCamera().getCenter().y - (this.background.getGlobalBounds().height/8);

        this.buttons.get("SAVE").setPosition(this.window.getCamera().getCenter().x, centerY - btnHeight - gap);
        this.buttons.get("MAINMENU").setPosition(this.window.getCamera().getCenter().x, centerY);
        this.buttons.get("BACK").setPosition(this.window.getCamera().getCenter().x, centerY + btnHeight + gap);
    }

    @Override
    protected void load() {
        this.setBackgroundTexture("PAUSE_BG", (float)0.3);
        this.scaleButtons();
    }

    @Override
    protected void handleClick(String btn) {
        switch (btn) {
            case "SAVE":
                System.out.println("<!> Error: Can't save yet.");
                break;
            case "MAINMENU":
                this.window.setMenu(MenuType.MAIN);
                this.window.setWorld(null);
                break;
            case "BACK":
                this.window.setMenu(null);
                break;
        }
    }
}
