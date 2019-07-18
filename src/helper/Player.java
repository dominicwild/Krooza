package helper;

import mods.*;
import movingobjects.PlayerCar;
import movingobjects.PlayerCharacter;
import org.jsfml.system.Vector2f;
import world.GameWorld;

/**
 * A class representing player variables, statistics and values. Anything that
 * is persistently held by the player that cannot fit squarely into any one
 * object representation of the player.
 *
 * @author DominicWild
 */
public class Player {

    public static Inventory inventory;
    public static Wallet wallet;
    public static PlayerCar car;
    public static PlayerCharacter character;
    
    public static void init(){
        Player.wallet = new Wallet(0);
        Player.inventory = new Inventory();
        Player.car = new PlayerCar(new ContextSkin("car_red", "car_red_damage50", "car_red_damage25"));
        Player.car.setMods(new Bumper(1), new SpikeDeployer(1, Player.car), new ArmourMod(2), new Engine(3),
                new ExhaustMod(3), new MachineGunTurret(1), new WheelMod(1, "wheel_1"), null);
        Player.car.applyMods();
        Player.car.addModsToInventory();
        Player.character = new PlayerCharacter(0,10,new CoolDownTurret(1,"max","bullet3",0.1));
        Player.inventory.add(new RocketLauncher(1));
        Player.populateInventory();
    }
    
    /**
     * Populates inventory for testing purposes.
     */
    public static void populateInventory(){
        Player.inventory.add(new ArmourMod(1));
        Player.inventory.add(new ArmourMod(3));
        Player.inventory.add(new Engine(1));
        Player.inventory.add(new Engine(2));
        Player.inventory.add(new ExhaustMod(1));
        Player.inventory.add(new ExhaustMod(2));
        Player.inventory.add(new Skin("car_blue"));
        Player.inventory.add(new Skin("car_striped"));
    }
    
    public static Vector2f getPosition(){
        if(GameWorld.worldObjects.contains(Player.car)){
            return Player.car.getPosition();
        } else if(GameWorld.worldObjects.contains(Player.character)) {
            return Player.character.getPosition();
        } else {
            return null;
        }
    }
    
}
