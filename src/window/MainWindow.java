package window;

import camera.Camera;
import handler.*;
import helper.Player;
import main.Debug;
import main.DebugType;
import menu.Menu;
import menu.MenuType;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import sequence.SequenceType;
import world.GameWorld;

import helper.Radio;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import menu.MainMenu;
import menu.OptionsMenu;
import org.jsfml.graphics.Image;

/**
 * @author Will Fantom
 */
public class MainWindow extends RenderWindow {
    public static int FRAMERATE_TARGET = 60;

    //SETTINGS
    private Settings windowSettings;

    //HANDLERS
    private Assets assetHandler;
    public static EventHandler eventHandler;
    private MenuHandler menuHandler;
    private SequenceHandler sequenceHandler;
    private WorldHandler worldHandler;
    public static Radio radio;

    //CAMERA
    private Camera camera;

    /**
     * The main window the game is played in. Sets all window options and
     * handles main game play loop. - Dominic Wild
     */
    public MainWindow() {


        this.setFramerateLimit(60);
        Image topKrooze = new Image();
        try {
            topKrooze.loadFromFile(Paths.get("Content/Assets/Images/krooza.png"));
            this.setIcon(topKrooze);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Settings
        this.windowSettings = new Settings(this);
        this.windowSettings.applySettings();
        this.setView(this.camera);

        //Handlers
        this.assetHandler = new Assets();
        this.eventHandler = new EventHandler(this);
        this.menuHandler = new MenuHandler(this);
        this.sequenceHandler = new SequenceHandler(this);
        this.worldHandler = new WorldHandler(this);
        Player.init();
        //Set start state
        this.setSequence(SequenceType.INTRO);
        //Draw Loop
        this.setIcon(topKrooze);
        while(this.isOpen()){
            this.setView(this.camera);
            this.handleEvents();
            this.clear(Color.BLACK);

            if(this.sequenceHandler.getCurrentSequence() != null){
                this.sequenceHandler.draw(); //If there is a sequence
            } else {
                if(this.menuHandler.getCurrentMenu() != null && (this.menuHandler.getCurrentMenu().getClass() == MainMenu.class || this.menuHandler.getCurrentMenu().getClass() == OptionsMenu.class)){
                    this.menuHandler.draw();
                } else {
                    this.worldHandler.draw();
                    this.menuHandler.draw();
                }
            }
            this.display();
        }
    }

    /**
     * Applies the settings from windowSettings to this JSFML RenderWindow
     */
    protected void setupWindow(int f, boolean v, String title, int t, VideoMode vm){
        this.setFramerateLimit(f);
        this.setVerticalSyncEnabled(v);
        this.create(vm, title, t);
        this.camera = new Camera(new Vector2f(this.getSize().x, this.getSize().y));

    }

    /**
     * Polls the windows events and checks for window close events
     */
    private void handleEvents(){

        this.eventHandler.handleEvents();
        //Handle Window Events
        if(this.eventHandler.isWindowClose())
            this.close();
        
        if(this.eventHandler.isKeyHeldDown(Keyboard.Key.LSHIFT) && this.eventHandler.isKeyHeldDown(Keyboard.Key.LCONTROL)) {
            if (this.eventHandler.isKeyReleased(Keyboard.Key.DELETE))
                this.close();
            if(this.eventHandler.isKeyReleased(Keyboard.Key.M))
                Debug.print("Current Menu - " + this.getCurrentMenu(), DebugType.OTHER);
        }
        //Handle Menu Events
        this.menuHandler.handleEvents();
        //Handle Sequence Events
        this.sequenceHandler.handleEvents();
        //Handle World Events
        this.worldHandler.handleEvents();
    }

    //SET HANDLERS
    /**
     * Sets the current menu
     * type == null for no menu
     * @param type
     */
    public void setMenu(MenuType type){
        this.menuHandler.setMenu(type);
    }
    /**
     * Sets the current sequence
     * type == null for no sequence
     * @param type
     */
    public void setSequence(SequenceType type){
        this.sequenceHandler.setSequence(type);
    }
    
    public void setWorld(GameWorld world){
        this.worldHandler.setWorld(world);
    }

    //GETTERS
    public Camera getCamera() {
        return this.camera;
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public Settings getWindowSettings() {
        return this.windowSettings;
    }

    public Menu getCurrentMenu() {
        return this.menuHandler.getCurrentMenu();
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }
    
}
