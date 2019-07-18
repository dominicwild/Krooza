
package world;

import camera.HUD;
import helper.Player;
import menu.MenuType;
import org.jsfml.system.Vector2f;
import window.MainWindow;

/**
 * A world that supports Horde Mode. Horde Mode is a mode where the player must
 * see how long they survive, the spawn rate ticks up at a constant rate.
 *
 * @author DominicWild
 */
public class HordeWorld extends GameWorld {

    private EnemySpawner spawner;               //Enemy spawner to spawn enemies.
    private double spawnRate;                   //The spawn rate of the enemy spawner.
    private int spawnIncrementFrames;           //The clock that times spawn rate incrememnts.
    private int spawnFrameCount;                //Counts the amount of frames til spawn rate increase.
    private double rateIncrement;               //The increment size per unit rateIncreaseTime.

    public HordeWorld(MainWindow window, String map) {
        super(window, map);
        this.spawnRate = 1.0 / (MainWindow.FRAMERATE_TARGET*10);
        this.rateIncrement = 1.0/(MainWindow.FRAMERATE_TARGET*20);
        this.spawnIncrementFrames = 5*MainWindow.FRAMERATE_TARGET;
        this.spawnFrameCount = this.spawnIncrementFrames;
        this.spawner = new EnemySpawner(this.spawnRate - this.spawnRate,this.window.getCamera().getSize());
        this.spawner.setSpawnLimit(100);
    }

    @Override
    protected void triggerEvents() {
        super.triggerEvents(); 
        this.spawner.spawnRoll();
        if(this.spawnFrameCount <= 0){
            this.spawnRate += this.rateIncrement;
            this.spawner.setGlobalProbability(this.spawnRate);
            this.spawnFrameCount = this.spawnIncrementFrames;
        } else {
            this.spawnFrameCount--;
        }
        if(this.spawner.getSpawnCount() == 0){
            this.spawner.triggerSpawn();
        }
    }

    @Override
    public void load() {
        super.load();
        Vector2f mapSize = GameWorld.tileEngine.getMapSize();
        Player.car.setPosition(mapSize.x/2,mapSize.y/2);
        GameWorld.worldObjects.add(Player.car);
        HUD.hideHud = false;
        for(int i=0;i<5;i++){
            this.spawner.triggerSpawn();
        }
    }

    @Override
    public void playerDeath() {
        this.setTransitionWorld(new OverWorld(GameWorld.window,"Content/Map/main.csv"));
        this.window.setMenu(MenuType.HORDE_GAME_OVER);
    }
    
    
    
}
