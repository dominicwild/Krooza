package menu;

import handler.Assets;
import handler.SpriteHandler;
import main.Debug;
import main.DebugType;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import window.MainWindow;
//import world.WorldType;

import java.util.Map;

/**
 * The main menu that is displayed as the first thing to the player. A central
 * hub to select all game options and modes. - Dominic Wild
 *
 * @author Will Fantom
 */
public class MainMenu extends Menu {

    //Credits
    protected static boolean isCredits;
    //Scale
    protected static Vector2f CREDITS_SCALE = new Vector2f((float) 0.5, (float) 0.8),
            BUTTON_SCALE = new Vector2f((float) 0.35, (float) 0.15);
    private static final int FRAMERATE_TARGET = 60;

    public MainMenu(MainWindow window) {
        super(window);
        this.isCredits = false;

        this.setupButtons();
        this.setupSound();

        this.load();
        Debug.print("Main Menu Loaded", DebugType.LOADED);
    }

    private void setupSound() {
        this.sound.add("MS_1");
    }

    private void setupButtons() {
        this.buttons.put("PLAY", new Button("MENU_PB", "MENU_PBH"));
        this.buttons.put("OPT", new Button("MENU_OB", "MENU_OBH"));
        //this.buttons.put("CREDITS", new Button("MENU_CB", "MENU_CBH"));
        this.buttons.put("QUIT", new Button("MENU_QB", "MENU_QBH"));
    }

    private void scaleButtons() {
        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;
        //Setup Buttons
        for (Map.Entry<String, Button> b : buttons.entrySet()) {
            SpriteHandler.scale(b.getValue(), ((width * BUTTON_SCALE.x) / b.getValue().getGlobalBounds().width), ((height * BUTTON_SCALE.y) / b.getValue().getGlobalBounds().height));
        }
        float btnHeight = this.buttons.get("PLAY").getGlobalBounds().height;

        this.buttons.get("PLAY").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y - (1.5 * btnHeight)));
        this.buttons.get("OPT").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y - (0.5 * btnHeight)));
//        this.buttons.get("CREDITS").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y + (0.5 * btnHeight)));
        this.buttons.get("QUIT").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y + (1.5 * btnHeight)));
    }

    @Override
    protected void load() {
        Assets.MUSIC.get(this.sound.get(0)).play();
        Assets.MUSIC.get(this.sound.get(0)).setPlayingOffset(Time.getMilliseconds(4000));
        Assets.MUSIC.get(this.sound.get(0)).setVolume(40);
        this.fitBackgroundTexture("MENU_BG");
        this.scaleButtons();
    }

    private void toggleCredits() {
        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;

        this.isCredits = !this.isCredits;

        if (this.isCredits) {
                  } else {
            this.buttons.remove("CIMG");
            this.setupButtons();
            this.scaleButtons();
        }
    }

    @Override
    protected void handleClick(String btn) {
        switch (btn) {
            case "PLAY":
                this.window.setMenu(MenuType.MODE);
                break;
            case "OPT":
                this.window.setMenu(MenuType.OPTIONS);
                break;
            case "CREDITS":
                this.toggleCredits();
                break;
            case "CIMG":
                this.toggleCredits();
                break;
            case "QUIT":
                Assets.MUSIC.get(this.sound.get(0)).stop();
                this.window.close();
                break;
        }
    }
}
