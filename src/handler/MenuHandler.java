package handler;

import menu.*;
import menu.MenuType;
import org.jsfml.window.Keyboard;
import window.MainWindow;

/**
 * A class responsible for managing the transition to different game menus.
 * @author Will Fantom, Dominic Wild
 */
public class MenuHandler {
    //WINDOW
    private MainWindow window;
    //CURRENT
    private Menu currentMenu;

    
    public MenuHandler(MainWindow window) {
        this.window = window;
        this.currentMenu = null;
    }

    /**
     * Sets the current menu
     *
     * @param type
     */
    public void setMenu(MenuType type) {

        if (type != null) {
            switch (type) {
                case MAIN:
                    this.currentMenu = new MainMenu(this.window);
                    break;
                case OPTIONS:
                    this.currentMenu = new OptionsMenu(this.window);
                    break;
                case PAUSE:
                    this.currentMenu = new PauseMenu(this.window);
                    break;
                case GARAGE:
                    this.currentMenu = new GarageMenu(this.window);
                    break;
                case VENDOR:
                    this.currentMenu = new VendorMenu(this.window);
                    break;
                case MODE:
                    this.currentMenu = new ModeMenu(this.window);
                    break;
                case GAME_OVER:
                    this.currentMenu = new GameOverMenu(this.window);
                    break;
                case HORDE_GAME_OVER:
                    this.currentMenu = new HordeGameOverMenu(this.window);
                    break;
            }
        } else {
            this.currentMenu = null;
        }
    }

    public void draw() {
        if (this.currentMenu != null) {
            this.currentMenu.draw();
        }
    }

    /**
     * Handles menu toggling
     */
    public void handleEvents() {
        EventHandler eH = this.window.getEventHandler();
        if (this.currentMenu == null) {
            if ((eH.isKeyReleased(Keyboard.Key.P) || eH.isKeyReleased(Keyboard.Key.ESCAPE) || eH.isWindowOutFocus())) {
                this.setMenu(MenuType.PAUSE);
            }
        } else {
            if (this.currentMenu.getClass() == PauseMenu.class && eH.isKeyReleased(Keyboard.Key.ESCAPE)) {
                this.setMenu(null);
            } else if (this.currentMenu.getClass() == MainMenu.class && eH.isKeyReleased(Keyboard.Key.O)) {
                this.setMenu(MenuType.OPTIONS);
            } else if (this.currentMenu.getClass() == OptionsMenu.class && (eH.isKeyReleased(Keyboard.Key.M) || eH.isKeyReleased(Keyboard.Key.ESCAPE))) {
                this.setMenu(MenuType.OPTIONS);
            }

        }
    }


    /**
     * Returns the current menu
     * @return
     */
    public Menu getCurrentMenu() {
        return this.currentMenu;
    }

}
