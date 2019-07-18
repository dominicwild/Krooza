package world;

import camera.Camera;
import camera.HUD;
import handler.EventHandler;
import helper.Animation;
import helper.Animation.Mode;
import helper.Player;
import helper.WorldSorter;
import java.util.ArrayList;
import main.Debug;
import main.DebugType;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import physics.PhysicsEngine;
import staticobjects.SolidEntity;
import window.MainWindow;

/**
 * A class representing a game world. Where a game world is a player area that
 * houses all world objects and processes all of their behaviour.
 *
 * @author Will Fantom
 */
public abstract class GameWorld {

    //WINDOW
    public static MainWindow window;

    //CAMERA
    private boolean isTracking;
    private boolean isPanning;
    private HUD hud;

    //MAP
    private String mapFilePath;
    public static TileEngine tileEngine;
    private Vector2f mapSize;
    
    public static ArrayList<SolidEntity> worldObjects = new ArrayList<>();
    public static ArrayList<Animation> worldAnimations = new ArrayList<>();
    public static ArrayList<Animation> worldAnimationsLoop = new ArrayList<>();
    private PhysicsEngine physics = new PhysicsEngine();
    private GameWorld transitionWorld;
    private boolean toTransition;

    public GameWorld(MainWindow window, String map) {
        this.window = window;
        this.mapFilePath = map;
        this.hud = new HUD(this.window);
        this.transitionWorld = null;
        this.toTransition = false;
    }

    /**
     * loads a world
     */
    public void load() {
        //Tiles
        if (this.tileEngine == null) {
            this.tileEngine = new TileEngine(this.window, mapFilePath);
            this.mapSize = this.tileEngine.getMapSize();
            this.tileEngine.loadTiles();
        }
        //Camera
        this.isTracking = true;
        this.isPanning = false;
    }

    /**
     * Unloads a world
     */
    public void unload() {
//        this.stopCameraMove();
        this.worldObjects.removeAll(worldObjects);
        this.worldAnimations.removeAll(worldAnimations);
        this.worldAnimationsLoop.removeAll(worldAnimationsLoop);
        GameWorld.worldObjects.remove(Player.car);
        GameWorld.worldObjects.remove(Player.character);
        Player.car.reset();
    }

    public void unloadTiles() {
        if (tileEngine != null) {
            this.tileEngine.setRunning(false);
        }
        this.tileEngine = null;
    }

    public void draw() {
        if (this.tileEngine.isLoaded()) {
            worldObjects.sort(new WorldSorter());
            this.tileEngine.draw();
            
            for(Animation a : worldAnimationsLoop){
                a.play(window,Mode.LOOP);
            }
            
            for (SolidEntity e : worldObjects) {
                window.draw(e);
            }
            ArrayList<Animation> toRemove = new ArrayList<>();
            
            for (Animation a : worldAnimations) {
                if (!a.isFinished()) {
                    a.play(window);
                } else {
                    toRemove.add(a);
                }
            }
            for (Animation a : toRemove) {
                this.worldAnimations.remove(a);
            }
            this.hud.draw();
        }
    }

    /**
     * Handles Events for the world
     */
    public void handleEvents() {
        if (this.window.getCurrentMenu() == null) {
           this.triggerEvents();
        }
    }
    
    protected void triggerEvents() {
        this.physics.handlePhysics();
        this.handleCamera();
        if(this.toTransition){
            GameWorld.window.getWorldHandler().setWorld(this.transitionWorld);
        }
        if(!(GameWorld.worldObjects.contains(Player.car) || GameWorld.worldObjects.contains(Player.character))){
            this.playerDeath();
        }
    }

    private void handleCamera() {
        if (this.window.getEventHandler().isMouseWheelReleased() || this.window.getEventHandler().isKeyReleased(Keyboard.Key.M)) {
            boolean temp = this.isTracking;
            this.isTracking = this.isPanning;
            this.isPanning = temp;
        }

        if (this.isPanning || this.isTracking) {
            this.zoom();
            this.hud.position();
        }
        if (this.isPanning) {
            this.pan();
        } else if (isTracking) {

            if (GameWorld.worldObjects.contains(Player.car)) {
                this.window.getCamera().setCenter(Player.car.getPosition());
            } else if (GameWorld.worldObjects.contains(Player.character)) {
                this.window.getCamera().setCenter(Player.character.getPosition());
            }
        }

    }

    /**
     * Stops the camera from being able to be moved at all
     */
    private void stopCameraMove() {
        this.isPanning = false;
        this.isTracking = false;
    }

    private void zoom() {
        if (this.window.getEventHandler().isMouseWheelMoved()) {
            if (this.window.getEventHandler().isKeyHeldDown(Keyboard.Key.LCONTROL)) {
                Camera c = this.window.getCamera();
                if (c.getSize().x * (1 + c.ZOOM_DELTA) < this.mapSize.x/10 && c.getSize().y * (1 + c.ZOOM_DELTA) < this.mapSize.y/10) {
                    this.window.getCamera().zoomOut();
                } else {
                    Debug.print("Max zoom out reached", DebugType.ERROR);
                }

                if (c.getCenter().y - (c.getSize().y / 2) < 0) {
                    c.setCenter(c.getCenter().x, (0 + c.getSize().y / 2));
                }
                if (c.getCenter().y + (c.getSize().y / 2) > this.mapSize.y) {
                    c.setCenter(c.getCenter().x, (this.mapSize.y - c.getSize().y / 2));
                }
                if (c.getCenter().x - (c.getSize().x / 2) < 0) {
                    c.setCenter((0 + c.getSize().x / 2), c.getCenter().y);
                }
                if (c.getCenter().x + (c.getSize().x / 2) > this.mapSize.x) {
                    c.setCenter((this.mapSize.x - c.getSize().x / 2), c.getCenter().y);
                }
            } else {
                this.window.getCamera().zoomIn();
            }
        }
    }

    private void pan() {
        float panDist = (float) (this.window.getCamera().getSize().y / 70);
        EventHandler eH = this.window.getEventHandler();

        if (eH.isKeyHeldDown(Keyboard.Key.W) && (this.window.getCamera().getCenter().y - (this.window.getCamera().getSize().y / 2) - panDist > 0)) {
            this.window.getCamera().pan(new Vector2f(0, -panDist));
        }

        if (eH.isKeyHeldDown(Keyboard.Key.S) && (this.window.getCamera().getCenter().y + (this.window.getCamera().getSize().y / 2) + panDist < this.mapSize.y)) {
            this.window.getCamera().pan(new Vector2f(0, panDist));
        }

        if (eH.isKeyHeldDown(Keyboard.Key.A) && (this.window.getCamera().getCenter().x - (this.window.getCamera().getSize().x / 2) - panDist > 0)) {
            this.window.getCamera().pan(new Vector2f(-panDist, 0));
        }

        if (eH.isKeyHeldDown(Keyboard.Key.D) && (this.window.getCamera().getCenter().x + (this.window.getCamera().getSize().x / 2) + panDist < this.mapSize.x)) {
            this.window.getCamera().pan(new Vector2f(panDist, 0));
        }
    }

    public void setTransitionWorld(GameWorld transitionWorld) {
        if (transitionWorld != null) {
            this.transitionWorld = transitionWorld;
            this.toTransition = true;
        }
    }

    public abstract void playerDeath();

}
