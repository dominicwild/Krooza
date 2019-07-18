package handler;

import main.Debug;
import main.DebugType;
import window.MainWindow;
import world.GameWorld;

/**
 * A class created to manage the switching of in-game worlds.
 *
 * @author Will Fantom, Dominic Wild
 */
public class WorldHandler {

    //WINDOW
    private MainWindow window;
    private GameWorld currentGameWorld;
    
    public WorldHandler(MainWindow window) {
        this.window = window;
        Debug.print("Main World", DebugType.LOADED);
    }

    

    public void setWorld(GameWorld worldToLoad) {
        if (this.currentGameWorld != null) {
            this.currentGameWorld.unloadTiles();
            this.currentGameWorld.unload();
        }
        this.currentGameWorld = worldToLoad;
        if(this.currentGameWorld != null){
        this.currentGameWorld.load();
        }
    }

    public void draw() {
        if (this.currentGameWorld != null) {
            this.currentGameWorld.draw();
        }
    }

    public void handleEvents() {
        if (this.currentGameWorld != null) {
            this.currentGameWorld.handleEvents();
        }
    }
}
