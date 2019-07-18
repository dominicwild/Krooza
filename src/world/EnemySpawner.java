
package world;

import helper.Player;
import java.util.Random;
import mods.ArmourMod;
import mods.Engine;
import mods.Skin;
import movingobjects.EnemyCar;
import movingobjects.EnemyHealer;
import movingobjects.EnemyRangedCar;
import org.jsfml.system.Vector2f;

/**
 * Spawns enemies into a GameWorld. Spawn positions based off of current player car position, programmed to spawn enemies out of camera view.
 * @author DominicWild
 */
public class EnemySpawner {
    
    private double globalProbability;       //Probability per frame a enemy is spawned.
    private Vector2f minSpawnRange;         //Size of the camera, minimum range for enemies to spawn away from player.
    public static int spawnCount;           //Count of how many enemies currently in the world.
    private final int NUM_ENEMIES = 3;      //Number of enemy types to spawn.
    private int spawnLimit = 20;      //Max number of enemies that can be spawned.
    private final int SPAWN_RANGE = 400;    //Spawn range from how far out of view the enemies will spawn from the camera.
    
    public EnemySpawner(double globalProbability, Vector2f spawnRange){
        this.globalProbability = globalProbability;
        this.minSpawnRange = spawnRange;
        this.spawnCount = 0;
    }
    
    public void spawnRoll(){
        if(this.spawnCount <= spawnLimit && Math.random() <= this.globalProbability){
            this.triggerSpawn();
        }
    }

    public void triggerSpawn() {
        this.spawnCount++;
        switch ((int) (Math.random() * NUM_ENEMIES)) {
            case 0:
                this.spawnRamCar();
                break;
            case 1:
                this.spawnRangeCar();
                break;
            case 2:
                this.spawnEnemyHealer();
                break;
        }
    }

    private void spawnRamCar(){
        Random rand = new Random();
        EnemyCar bashCar = new EnemyCar(new Skin("enemy_buggy","enemy_buggy_red"), Player.car);
        bashCar.setPosition(this.randSpawnLocation());
        bashCar.setEngine(new Engine(rand.nextInt(4)));
        bashCar.setArmour(new ArmourMod(rand.nextInt(4)));
        bashCar.setMaxSpeed(bashCar.getMaxSpeed() + rand.nextInt(20));
        if(Math.random() < 0.001){
            bashCar.setMass(bashCar.getMass() + 70);
        } else {
        bashCar.setMass(bashCar.getMass() + rand.nextInt(30));
        }
        GameWorld.worldObjects.add(bashCar);
    }
   
    private void spawnEnemyHealer(){
        Random rand = new Random();
        EnemyHealer healer = new EnemyHealer();
        healer.setPosition(this.randSpawnLocation());
        healer.setPosition(this.randSpawnLocation());
        healer.setEngine(new Engine(rand.nextInt(4)));
        healer.setArmour(new ArmourMod(rand.nextInt(4)));
        if(Math.random() < 0.001){
            healer.setAcceleration(healer.getAcceleration() + 3);
        }
        GameWorld.worldObjects.add(healer);
    }
    
    private void spawnRangeCar(){
        Random rand = new Random();
        EnemyCar rangeCar = new EnemyRangedCar(Player.car);
        rangeCar.setPosition(this.randSpawnLocation());
        rangeCar.setPosition(this.randSpawnLocation());
        rangeCar.setEngine(new Engine(rand.nextInt(4)));
        rangeCar.setArmour(new ArmourMod(rand.nextInt(4)));
        rangeCar.setAcceleration(rangeCar.getAcceleration() + rand.nextFloat()*2);
        GameWorld.worldObjects.add(rangeCar);
    }
    
    private Vector2f randSpawnLocation(){
        Vector2f spawnOffset;
        Vector2f randVector = this.randVectorOffset();
        Vector2f carPos = Player.car.getPosition();
        int sign = this.randSign();
        if(Math.random() <= 0.5){ //Calculate random displacement
            spawnOffset = new Vector2f(sign*this.minSpawnRange.x/2, (float) (Math.random()*randSign()*this.minSpawnRange.y/2));
            return new Vector2f(carPos.x + spawnOffset.x + sign*randVector.x,
                                carPos.y + spawnOffset.y + randVector.y);
        } else {
            spawnOffset = new Vector2f((float) (Math.random()*randSign()*this.minSpawnRange.x/2),sign*this.minSpawnRange.y/2);
            return new Vector2f(carPos.x + spawnOffset.x + randVector.x,
                                carPos.y + spawnOffset.y + sign*randVector.y);
        }
    }
    
    private int randSign(){
        if(Math.random() <= 0.5){
            return 1;
        } else {
            return -1;
        }
    }
    
    private Vector2f randVectorOffset(){
        return new Vector2f((float)Math.random()*this.SPAWN_RANGE,(float)Math.random()*this.SPAWN_RANGE);
    }

    public void setGlobalProbability(double globalProbability) {
        this.globalProbability = globalProbability;
    }

    public void setSpawnLimit(int spawnLimit) {
        this.spawnLimit = spawnLimit;
    }

    public static int getSpawnCount() {
        return spawnCount;
    }
    
    
    
}
