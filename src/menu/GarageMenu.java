package menu;

import java.util.Map;

import main.Debug;
import main.DebugType;
import mods.*;
import window.MainWindow;
import helper.Player;
import org.jsfml.system.Vector2f;
import static menu.GarageButtonType.*;

/**
 * A menu the player can use to customize the equiped mods on their car.
 * @author Alex Bentley, Dominic Wild
 */
public class GarageMenu extends Menu {

    private Engine equippedEngine, displayedEngine;
    private ExhaustMod equippedExhaust, displayedExhaust;
    private TurretMod equippedTurret, displayedTurret;
    private FrontMod equippedFrontMod, displayedFrontMod;
    private BackMod equippedBackMod, displayedBackMod;
    private WheelMod equippedWheels, displayedWheels;
    private ArmourMod equippedArmour, displayedArmour;
    private Skin equippedSkin, displayedSkin;

    private final float BUTTON_SCALE = (float) 0.15;

    public GarageMenu(MainWindow window) {
        super(window);
        this.load();
        Debug.print("Garage Menu loaded", DebugType.LOADED);
    }

    public void load() {
        this.setBackgroundTexture("GarageMenuPanel", (float) 0.9);

        equippedEngine = Player.car.getEngine();
        Player.inventory.setIndex(equippedEngine);
        displayedEngine = equippedEngine;

        equippedExhaust = Player.car.getExhaust();
        Player.inventory.setIndex(equippedExhaust);
        displayedExhaust = equippedExhaust;

        equippedTurret = Player.car.getTurret();
        Player.inventory.setIndex(equippedTurret);
        displayedTurret = equippedTurret;

        equippedFrontMod = Player.car.getFrontMod();
        Player.inventory.setIndex(equippedFrontMod);
        displayedFrontMod = equippedFrontMod;

        equippedBackMod = Player.car.getBackMod();
        Player.inventory.setIndex(equippedBackMod);
        displayedBackMod = equippedBackMod;

        equippedArmour = Player.car.getArmour();
        Player.inventory.setIndex(equippedArmour);
        displayedArmour = equippedArmour;

        equippedWheels = Player.car.getWheels();
        Player.inventory.setIndex(equippedWheels);
        displayedWheels = equippedWheels;

        equippedSkin = Player.car.getSkin();
        Player.inventory.setIndex(equippedSkin);
        displayedSkin = equippedSkin;

        this.setupButtons();
        this.scaleButtons();
    }

    private void setupButtons() {
        this.buttons.put("ENGINE", new GarageButton(equippedEngine.getDescriptor() + "_icon", "Icon_hover", ENGINE));
        this.buttons.put("EXHAUST", new GarageButton(equippedExhaust.getDescriptor() + "_icon", "Icon_hover", EXHAUST));
        this.buttons.put("TURRET", new GarageButton(equippedTurret.getDescriptor() + "_icon", "Icon_hover", TURRET));
        this.buttons.put("FRONTMOD", new GarageButton(equippedFrontMod.getDescriptor() + "_icon", "Icon_hover", FRONT_MOD));
        this.buttons.put("BACKMOD", new GarageButton(equippedBackMod.getDescriptor() + "_icon", "Icon_hover", BACK_MOD));
        this.buttons.put("WHEELS", new GarageButton(this.equippedWheels.getDescriptor() + "_icon", "Icon_hover", WHEELS));
        this.buttons.put("ARMOUR", new GarageButton(equippedArmour.getDescriptor() + "_icon", "Icon_hover", ARMOUR));
        this.buttons.put("SKIN", new GarageButton(equippedSkin.getDescriptor() + "_icon", "Icon_hover", SKIN));
        this.buttons.put("REPAIR", new Button("RepairBtn", "RepairBtn"));
        this.buttons.put("APPLY", new Button("SaveandQuitBtn", "SaveandQuitBtn"));
        this.buttons.put("REVERT", new Button("RevertBtn", "RevertBtn"));
    }

    //Scaling and position
    private void scaleButtons() {

        float height = this.window.getCamera().getSize().y;

        for (Map.Entry<String, Button> b : buttons.entrySet()) {
            if (!b.getKey().equals("REPAIR") && !b.getKey().equals("APPLY") && !b.getKey().equals("REVERT")) {
                float bScale = (float) (height * BUTTON_SCALE / b.getValue().getGlobalBounds().height);
                b.getValue().setScale(bScale, bScale);
            } else if (b.getKey().equals("REPAIR")) {
                float bScale = (float) (height * 0.1 / b.getValue().getGlobalBounds().height);
                b.getValue().setScale(bScale, bScale);
            } else {
                float bScale = (float) (height * 0.09 / b.getValue().getGlobalBounds().height);
                b.getValue().setScale(bScale, bScale);
            }
        }

        float btnDimension = this.buttons.get("ENGINE").getGlobalBounds().height;
        float sep = (float) 38.4;

        float cameraCenterX = this.window.getCamera().getCenter().x;
        float cameraCenterY = this.window.getCamera().getCenter().y;
        Vector2f cameraSize = this.window.getCamera().getSize();
        this.buttons.get("ENGINE").setPosition((float) (cameraCenterX - (2 * sep) - (1.5 * btnDimension)), (float) (cameraCenterY - (1 * sep) - (0.5 * btnDimension)));
        this.buttons.get("EXHAUST").setPosition((float) (cameraCenterX - sep - (0.5 * btnDimension)), (float) (cameraCenterY - (1 * sep) - (0.5 * btnDimension)));
        this.buttons.get("TURRET").setPosition((float) (cameraCenterX + sep + (0.5 * btnDimension)), (float) (cameraCenterY - (1 * sep) - (0.5 * btnDimension)));
        this.buttons.get("FRONTMOD").setPosition((float) (cameraCenterX + (2 * sep) + (1.5 * btnDimension)), (float) (cameraCenterY - (1 * sep) - (0.5 * btnDimension)));

        this.buttons.get("BACKMOD").setPosition((float) (cameraCenterX - (2 * sep) - (1.5 * btnDimension)), (float) (cameraCenterY + (1 * sep) + (0.5 * btnDimension)));
        this.buttons.get("WHEELS").setPosition((float) (cameraCenterX - sep - (0.5 * btnDimension)), (float) (cameraCenterY + (1 * sep) + (0.5 * btnDimension)));
        this.buttons.get("ARMOUR").setPosition((float) ((cameraCenterX) + sep + (0.5 * btnDimension)), (float) (cameraCenterY + (1 * sep) + (0.5 * btnDimension)));
        this.buttons.get("SKIN").setPosition((float) (cameraCenterX + (2 * sep) + (1.5 * btnDimension)), (float) (cameraCenterY + (1 * sep) + (0.5 * btnDimension)));

        this.buttons.get("REPAIR").setPosition((float) (cameraCenterX - (0.65 * cameraSize.x / 2)), (float) (cameraCenterY + (0.65 * cameraSize.y / 2)));
        this.buttons.get("APPLY").setPosition((float) (cameraCenterX + (0.65 * cameraSize.x / 2)), (float) (cameraCenterY + (0.475 * cameraSize.y / 2)));
        this.buttons.get("REVERT").setPosition((float) (cameraCenterX + (0.65 * cameraSize.x / 2)), (float) (cameraCenterY + (0.675 * cameraSize.y / 2)));
    }

    protected void handleClick(String btn) {
        switch (btn) {
            case "ENGINE":
                try {
                    this.buttons.get("ENGINE").setBaseTexture(displayedEngine.getDescriptor() + "_icon");
                    displayedEngine = Player.inventory.getNextEngine(true);
                    GarageButton b = (GarageButton) this.buttons.get("ENGINE");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next Engine - there are no Engines in current inventory!");
                }
                break;

            case "EXHAUST":
                try {
                    displayedExhaust = Player.inventory.getNextExhaust(true);
                    this.buttons.get("EXHAUST").setBaseTexture(displayedExhaust.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("EXHAUST");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next Exhaust - there are no Exhausts in the current inventory!");
                }
                break;

            case "TURRET":
                try {
                    displayedTurret = Player.inventory.getNextTurret(true);
                    this.buttons.get("TURRET").setBaseTexture(displayedTurret.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("TURRET");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next Turret - there are no Turrets in current inventory!");
                }
                break;

            case "FRONTMOD":
                try {
                    displayedFrontMod = Player.inventory.getNextFrontMod(true);
                    this.buttons.get("FRONTMOD").setBaseTexture(displayedFrontMod.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("FRONTMOD");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next FrontMod - there are no FrontMods in current inventory!");
                }
                break;

            case "BACKMOD":
                try {
                    displayedBackMod = Player.inventory.getNextBackMod(true);
                    this.buttons.get("BACKMOD").setBaseTexture(displayedBackMod.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("BACKMOD");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next BackMod - there are no BackMods in current inventory!");
                }
                break;

            case "WHEELS":
                try {
                    displayedWheels = Player.inventory.getNextWheels(true);
                    this.buttons.get("WHEELS").setBaseTexture(displayedWheels.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("WHEELS");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next Wheel Set - there are no sets of wheels in current inventory!");
                }
                break;

            case "ARMOUR":
                try {
                    displayedArmour = Player.inventory.getNextArmour(true);
                    this.buttons.get("ARMOUR").setBaseTexture(displayedArmour.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("ARMOUR");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next set of Armour - there are no sets of Armour in current inventory!");
                }
                break;

            case "SKIN":
                try {
                    displayedSkin = Player.inventory.getNextSkin(true);
                    this.buttons.get("SKIN").setBaseTexture(displayedSkin.getDescriptor() + "_icon");
                    GarageButton b = (GarageButton) this.buttons.get("SKIN");
                    b.setClicked(true);
                } catch (Exception e) {
                    System.out.println("Can't get next Skin - there are no Skins in the current inventory");
                }
                break;

            case "REPAIR":
                if (Player.wallet.canAfford(50) && Player.car.getHealth() != Player.car.getMaxHealth()) {
                    Player.wallet.withdraw(50);
                    Player.car.setHealth(Player.car.getMaxHealth());
                } else {
                    System.out.println("Player can't afford to repair!");
                }
                break;

            case "APPLY":
                Player.car.unapplyMods();
                Player.car.setMods(displayedFrontMod, displayedBackMod, displayedArmour, displayedEngine, displayedExhaust, displayedTurret, displayedWheels, displayedSkin);
                Player.car.applyMods();
                this.window.setMenu(null);
                break;

            case "REVERT":
                this.window.setMenu(null);
                break;
        }
    }
}
