package staticobjects;

import handler.Assets;
import helper.Inventory;
import helper.Player;
import java.util.Random;
import mods.ArmourMod;
import mods.BaseMod;
import mods.Engine;
import mods.ExhaustMod;
import mods.MachineGunTurret;
import mods.RocketLauncher;
import mods.Skin;
import mods.SpikeDeployer;
import mods.WheelMod;
import org.jsfml.graphics.Sprite;
import physics.PhysicsEngine;

/**
 * A chest within an on-foot settlement. Upon colliding with the chest, the player will receive an item.
 * @author PeterKeves, DominicWild
 */
public class Chest extends SolidEntity {
    
    Random random = new Random();
    private boolean opened = false;  //if chest was opened or not

    public Chest() {
        super(new Sprite(Assets.TEXTURE.get("chest")));
        this.layer = 0;
    }

    public void lootChest() {
        //Loot scrap
        Player.wallet.add(random.nextInt(50));
        //Loot item
        getLootItem();
    }

    public boolean getOpened() {
        return opened;
    }

    public void setOpened(boolean value) {
        opened = value;
    }

    public void getLootItem() {
        boolean isMod = random.nextBoolean(); //chooses if its mode or skin
        BaseMod lootItem = null;
        if (isMod == true) {
            int randomTier = random.nextInt(4);
            switch (random.nextInt(7)) {
                case 0:
                    lootItem = new ArmourMod(randomTier);
                    break;
                case 1:
                    lootItem = new Engine(randomTier);
                    break;
                case 2:
                    lootItem = new ExhaustMod(randomTier);
                    break;
                case 3:
                    lootItem = new SpikeDeployer(randomTier,Player.car);
                    break;
                case 4:
                    lootItem = new RocketLauncher(randomTier);
                    break;
                case 5:
                    lootItem = new MachineGunTurret(randomTier);
                    break;
                case 6:
                    lootItem = new WheelMod(randomTier, "wheel_1");
                    break;
            }
        } else {
            switch (random.nextInt(2)) {
                case 0:
                    lootItem = new Skin("car_blue");
                case 1:
                    lootItem = new Skin("car_striped");
            }
        }
        Player.inventory.add(lootItem);
        PhysicsEngine.objectsDelete.add(this);
    }
}
