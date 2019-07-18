package menu;

import menu.GameOverMenu;
import window.MainWindow;
import world.GameWorld;
import world.OverWorld;

/**
 * @author Alex Bentley
 */
public class HordeGameOverMenu extends GameOverMenu {

    public HordeGameOverMenu(MainWindow window) {
        super(window);
    }

    @Override
    protected void handleClick(String btn) {
        this.window.setWorld(new OverWorld(GameWorld.window,"Content/Map/main.csv"));
        this.window.setMenu(null);
    }
}
