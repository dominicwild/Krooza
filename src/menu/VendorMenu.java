package menu;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

import helper.Player;
import main.Debug;
import main.DebugType;
import mods.*;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Vector2f;
import window.MainWindow;

/**
 * The menu that is displayed when a player goes to a vendor.
 *
 * @author Dominic Wild
 * @author Alex Bentley
 * @author Dom Potts
 */
public class VendorMenu extends Menu {

    private Engine vendorEngine, checkoutEngine;
    private ExhaustMod vendorExhaust, checkoutExhaust;
    private TurretMod vendorTurret, checkoutTurret;
    private FrontMod vendorFrontMod, checkoutFrontMod;
    private BackMod vendorBackMod, checkoutBackMod;
    private WheelMod vendorWheels, checkoutWheels;
    private ArmourMod vendorArmour, checkoutArmour;
    private Skin vendorSkin, checkoutSkin;
    private int engineTier, exhaustTier, turretTier, frontModTier, backModTier, armourTier;

    private VendorInventory vendorInventory;
    private Text checkoutCostText;
    private Font light;
    private final float BUTTON_SCALE = (float)0.125;
    private final float OTHER_BUTTON_SCALE = (float)0.1;
    private final int BASE_COST = 50;

    private int checkoutCost;

    public VendorMenu(MainWindow window) {
        super(window);
        
        this.light = new Font();
        try {
            this.light.loadFromFile(Paths.get("Content/Assets/Fonts/light.ttf"));
        } catch (IOException ioe) {
            Debug.print("Could not load in the light font", DebugType.ERROR);
        }
        this.checkoutCostText = new Text("0", this.light, 32);
        this.checkoutCostText.setStyle(TextStyle.BOLD);

        this.generateInventory();

        this.checkoutCost = 0;
        this.load();
        Debug.print("Vendor Menu loaded", DebugType.LOADED);
    }

    public void load() {
        this.setBackgroundTexture("VendorPanel", (float)0.95);

        checkoutEngine = null;
        checkoutExhaust = null;
        checkoutTurret = null;
        checkoutFrontMod = null;
        checkoutBackMod = null;
        checkoutWheels = null;
        checkoutArmour = null;
        checkoutSkin = null;

        try {
            vendorEngine = vendorInventory.getNextEngine(false);
            vendorExhaust = vendorInventory.getNextExhaust(false);
            vendorTurret = vendorInventory.getNextTurret(false);
            vendorFrontMod = vendorInventory.getNextFrontMod(false);
            vendorBackMod = vendorInventory.getNextBackMod(false);
            vendorWheels = vendorInventory.getNextWheels(false);
            vendorArmour = vendorInventory.getNextArmour(false);
            vendorSkin = vendorInventory.getNextSkin(false);
        } catch (Exception e) {
            System.out.println("Error loading vendor's inventory");
            e.printStackTrace();
        }

        this.setupButtons();
        this.scaleButtons();
    }

    @Override
    public void draw() {
        super.draw();
        checkoutCostText.draw(this.window, RenderStates.DEFAULT);
    }

    private void setupButtons() {
        this.buttons.put("CHECKOUT", new Button("CheckoutBtn", "CheckoutBtn"));
        this.buttons.put("QUIT", new Button("QuitBtn", "QuitBtn"));

        this.buttons.put("ENGINE", new Button(vendorEngine.getDescriptor() + "_icon", vendorEngine.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_ENGINE", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("EXHAUST", new Button(vendorExhaust.getDescriptor() + "_icon", vendorExhaust.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_EXHAUST", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("TURRET", new Button(vendorTurret.getDescriptor() + "_icon", vendorTurret.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_TURRET", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("FRONTMOD", new Button(vendorFrontMod.getDescriptor() + "_icon", vendorFrontMod.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_FRONTMOD", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("BACKMOD", new Button(vendorBackMod.getDescriptor() + "_icon", vendorBackMod.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_BACKMOD", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("WHEELS", new Button(vendorWheels.getDescriptor() + "_icon", vendorWheels.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_WHEELS", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("ARMOUR", new Button(vendorArmour.getDescriptor() + "_icon", vendorArmour.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_ARMOUR", new Button("blank_Icon", "Icon_hover"));
        this.buttons.put("SKIN", new Button(vendorSkin.getDescriptor() + "_icon", vendorSkin.getDescriptor() + "_icon"));
        this.buttons.put("CHECKOUT_SKIN", new Button("blank_Icon", "Icon_hover"));
        this.updateCostDisplay();
    }//End setup method

    private void scaleButtons() {
        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;

        for (Map.Entry<String, Button> b : buttons.entrySet()) {
            if (!b.getKey().equals("CHECKOUT") && !b.getKey().equals("QUIT")) {
                float bScale = height * BUTTON_SCALE / b.getValue().getLocalBounds().height;
                b.getValue().setScale(bScale, bScale);
            } else {
                float bScale = height * OTHER_BUTTON_SCALE / b.getValue().getLocalBounds().height;
                b.getValue().setScale(bScale, bScale);
            }

        }

        float btn = this.buttons.get("ENGINE").getGlobalBounds().height;
        float sep = (float)(0.17 * width) * BUTTON_SCALE;
        float xOffset = (float)(0.13 * width);
        float yOffset = (float)(0.22 * height);
        Vector2f cameraCent = this.window.getCamera().getCenter();

        //LEFT HAND PANEL: VENDOR'S STUFF
        this.buttons.get("ENGINE").setPosition(xOffset, yOffset);
        this.buttons.get("EXHAUST").setPosition(xOffset + btn + sep, yOffset);
        this.buttons.get("TURRET").setPosition(xOffset,yOffset + btn + sep);
        this.buttons.get("FRONTMOD").setPosition( xOffset + btn + sep, yOffset + btn + sep);
        this.buttons.get("BACKMOD").setPosition(xOffset, yOffset + 2*btn + 2*sep);
        this.buttons.get("WHEELS").setPosition(xOffset + btn + sep, yOffset + 2*btn + 2*sep);
        this.buttons.get("ARMOUR").setPosition(xOffset, yOffset + 3*btn + 3*sep);
        this.buttons.get("SKIN").setPosition(xOffset + btn + sep, yOffset + 3*btn + 3*sep);

        //RIGHT HAND PANEL: CHECKOUT STUFF
        this.buttons.get("CHECKOUT_ENGINE").setPosition(width - xOffset - btn - sep, yOffset);
        this.buttons.get("CHECKOUT_EXHAUST").setPosition(width - xOffset, yOffset);
        this.buttons.get("CHECKOUT_TURRET").setPosition(width - xOffset - btn - sep, yOffset + btn + sep);
        this.buttons.get("CHECKOUT_FRONTMOD").setPosition(width - xOffset, yOffset + btn + sep);
        this.buttons.get("CHECKOUT_BACKMOD").setPosition(width - xOffset - btn - sep, yOffset + 2*btn + 2*sep);
        this.buttons.get("CHECKOUT_WHEELS").setPosition(width - xOffset, yOffset + 2*btn + 2*sep);
        this.buttons.get("CHECKOUT_ARMOUR").setPosition(width - xOffset - btn - sep, yOffset + 3*btn + 3*sep);
        this.buttons.get("CHECKOUT_SKIN").setPosition(width - xOffset, yOffset + 3*btn + 3*sep);

        //OTHER BUTTONS AND TEXT
        this.buttons.get("CHECKOUT").setPosition(width - xOffset - sep, height - sep);
        this.buttons.get("QUIT").setPosition(xOffset + sep, height - 2*sep);
        this.checkoutCostText.setPosition(width - xOffset - sep, (float)(height - 3.5*sep));

        for(Button b : this.buttons.values()){
            b.setPosition(Vector2f.add(b.getPosition(), Vector2f.add(cameraCent, new Vector2f(-width/2,-height/2))));
        }
        this.checkoutCostText.setPosition(Vector2f.add(checkoutCostText.getPosition(), Vector2f.add(cameraCent, new Vector2f(-width/2,-height/2))));
        Button checkout = this.buttons.get("CHECKOUT");
        Button quit = this.buttons.get("QUIT");
        Vector2f offset = new Vector2f(0,-55);
        checkout.setPosition(Vector2f.add(checkout.getPosition(), offset));
        quit.setPosition(Vector2f.add(quit.getPosition(), offset));
        this.checkoutCostText.setPosition(Vector2f.add(checkoutCostText.getPosition(), Vector2f.add(offset,new Vector2f(0,-5))));

    }//End scale method

    protected void handleClick(String btn) {
        switch(btn) {
            case "ENGINE":
                if (!Player.inventory.contains(vendorEngine)) {
                    if (Player.wallet.canAfford(checkoutCost + BASE_COST * engineTier) && checkoutEngine == null) {
                        checkoutEngine = vendorEngine;
                        this.buttons.get("CHECKOUT_ENGINE").setBaseTexture(vendorEngine.getDescriptor() + "_icon");
                        checkoutCost += BASE_COST * engineTier;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("ENGINE").setBaseTexture("Icon_hover");
                break;

            case "EXHAUST":
                if (!Player.inventory.contains(vendorExhaust)) {
                    if (Player.wallet.canAfford(checkoutCost + BASE_COST * exhaustTier) && checkoutExhaust == null) {
                        checkoutExhaust = vendorExhaust;
                        this.buttons.get("CHECKOUT_EXHAUST").setBaseTexture(vendorExhaust.getDescriptor() + "_icon");
                        checkoutCost += BASE_COST * exhaustTier;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("EXHAUST").setBaseTexture("Icon_hover");
                break;

            case "TURRET":
                if (!Player.inventory.contains(vendorTurret)) {
                    if (Player.wallet.canAfford(checkoutCost + (BASE_COST / 5) * turretTier) && checkoutTurret == null) {
                        checkoutTurret = vendorTurret;
                        this.buttons.get("CHECKOUT_TURRET").setBaseTexture(vendorTurret.getDescriptor() + "_icon");
                        checkoutCost += (BASE_COST / 5) * turretTier;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("TURRET").setBaseTexture("Icon_hover");
                break;

            case "FRONTMOD":
                if (!Player.inventory.contains(vendorFrontMod)) {
                    if (Player.wallet.canAfford(checkoutCost + 100) && checkoutFrontMod == null) {
                        checkoutFrontMod = vendorFrontMod;
                        this.buttons.get("CHECKOUT_FRONTMOD").setBaseTexture(vendorFrontMod.getDescriptor() + "_icon");
                        checkoutCost += 100;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("FRONTMOD").setBaseTexture("Icon_hover");
                break;

            case "BACKMOD":
                if (!Player.inventory.contains(vendorBackMod)) {
                    if (Player.wallet.canAfford(checkoutCost + 100) && checkoutBackMod == null) {
                        checkoutBackMod = vendorBackMod;
                        this.buttons.get("CHECKOUT_BACKMOD").setBaseTexture(vendorBackMod.getDescriptor() + "_icon");
                        checkoutCost += 100;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("BACKMOD").setBaseTexture("Icon_hover");

                break;

            case "WHEELS":
                if (!Player.inventory.contains(vendorWheels)) {
                    if (Player.wallet.canAfford(checkoutCost + 50) && checkoutWheels == null) {
                        checkoutWheels = vendorWheels;
                        this.buttons.get("CHECKOUT_WHEELS").setBaseTexture(vendorWheels.getDescriptor() + "_icon");
                        checkoutCost += 50;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("WHEELS").setBaseTexture("Icon_hover");
                break;

            case "ARMOUR":
                if (!Player.inventory.contains(vendorArmour)) {
                    if (Player.wallet.canAfford(checkoutCost + BASE_COST * armourTier) && checkoutArmour == null) {
                        checkoutArmour = vendorArmour;
                        this.buttons.get("CHECKOUT_ARMOUR").setBaseTexture(vendorArmour.getDescriptor() + "_icon");
                        checkoutCost += BASE_COST * armourTier;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("ARMOUR").setBaseTexture("Icon_hover");
                break;

            case "SKIN":
                if (!Player.inventory.contains(vendorSkin)) {
                    if (Player.wallet.canAfford(checkoutCost + 50) && checkoutSkin == null) {
                        checkoutSkin = vendorSkin;
                        this.buttons.get("CHECKOUT_SKIN").setBaseTexture(vendorSkin.getDescriptor() + "_icon");
                        checkoutCost += 50;
                        this.updateCostDisplay();
                    }
                } else
                    this.buttons.get("SKIN").setBaseTexture("Icon_hover");
                break;


            case "CHECKOUT_ENGINE":
                if (checkoutEngine != null) {
                    this.buttons.get("ENGINE").setBaseTexture(checkoutEngine.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_ENGINE").setBaseTexture("blank_Icon");
                    checkoutEngine = null;
                    checkoutCost -= BASE_COST * engineTier;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_EXHAUST":
                if (checkoutExhaust != null) {
                    this.buttons.get("EXHAUST").setBaseTexture(checkoutExhaust.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_EXHAUST").setBaseTexture("blank_Icon");
                    checkoutExhaust = null;
                    checkoutCost -= BASE_COST * exhaustTier;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_TURRET":
                if (checkoutTurret != null) {
                    this.buttons.get("TURRET").setBaseTexture(checkoutTurret.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_TURRET").setBaseTexture("blank_Icon");
                    checkoutTurret = null;
                    checkoutCost -= (BASE_COST / 5) * turretTier;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_FRONTMOD":
                if (checkoutFrontMod != null) {
                    this.buttons.get("FRONTMOD").setBaseTexture(checkoutFrontMod.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_FRONTMOD").setBaseTexture("blank_Icon");
                    checkoutFrontMod = null;
                    checkoutCost -= 100;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_BACKMOD":
                if (checkoutBackMod != null) {
                    this.buttons.get("BACKMOD").setBaseTexture(checkoutBackMod.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_BACKMOD").setBaseTexture("blank_Icon");
                    checkoutBackMod = null;
                    checkoutCost -= 100;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_WHEELS":
                if (checkoutWheels != null) {
                    this.buttons.get("WHEELS").setBaseTexture(checkoutWheels.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_WHEELS").setBaseTexture("blank_Icon");
                    checkoutWheels = null;
                    checkoutCost -= 50;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_ARMOUR":
                if (checkoutArmour != null) {
                    this.buttons.get("ARMOUR").setBaseTexture(checkoutArmour.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_ARMOUR").setBaseTexture("blank_Icon");
                    checkoutArmour = null;
                    checkoutCost -= BASE_COST * armourTier;
                    this.updateCostDisplay();
                }
                break;

            case "CHECKOUT_SKIN":
                if (checkoutSkin != null) {
                    this.buttons.get("SKIN").setBaseTexture(checkoutSkin.getDescriptor() + "_icon");
                    this.buttons.get("CHECKOUT_SKIN").setBaseTexture("blank_Icon");
                    checkoutSkin = null;
                    checkoutCost -= 50;
                    this.updateCostDisplay();
                }
                break;


            case "CHECKOUT":
                if (Player.wallet.canAfford(checkoutCost)) {
                    Player.wallet.withdraw(checkoutCost);
                    this.addCartToInventory();
                    this.window.setMenu(null);
                } else
                    Debug.print("Can't afford vendor checkout", DebugType.ERROR);
                break;

            case "QUIT":
                this.window.setMenu(null);
                break;

        }
    }

    private void updateCostDisplay() {
        checkoutCostText.setString(Integer.toString(checkoutCost));
    }

    private void generateInventory() {
        this.vendorInventory = new VendorInventory();

        //GENERATE VENDOR INVENTORY:
        Random random = new Random();

        //Engine
        engineTier = random.nextInt(3) + 1;
        vendorInventory.add(new Engine(engineTier));

        //Exhaust
        exhaustTier = random.nextInt(3) + 1;
        vendorInventory.add(new ExhaustMod(exhaustTier));

        //Turret
        turretTier = random.nextInt(10);
        if (random.nextBoolean())
            vendorInventory.add(new MachineGunTurret(turretTier));
        else
            vendorInventory.add(new RocketLauncher(turretTier));

        //FrontMod
        frontModTier = random.nextInt(10);
        vendorInventory.add(new Bumper(frontModTier));

        //BackMod
        backModTier = random.nextInt(10);
        vendorInventory.add(new SpikeDeployer(backModTier, Player.car));

        //Armour
        armourTier = random.nextInt(3) + 1;
        vendorInventory.add(new ArmourMod(armourTier));

        //Wheels
        vendorInventory.add(new WheelMod(1, "wheel_1"));//Only one type of wheels...

        //Skin
        if (random.nextBoolean()) {
            if (random.nextBoolean())
                vendorInventory.add(new Skin("car_blue"));
            else
                vendorInventory.add(new Skin("car_red"));
        } else {
            if (random.nextBoolean())
                vendorInventory.add(new Skin("car_green"));
            else
                vendorInventory.add(new Skin("rusted_car_v3"));
        }
    }

    private void addCartToInventory() {
        if (checkoutEngine != null)
            Player.inventory.add(checkoutEngine);
        if (checkoutExhaust != null)
            Player.inventory.add(checkoutExhaust);
        if (checkoutTurret != null)
            Player.inventory.add(checkoutTurret);
        if (checkoutFrontMod != null)
            Player.inventory.add(checkoutFrontMod);
        if (checkoutBackMod != null)
            Player.inventory.add(checkoutBackMod);
        if (checkoutArmour != null)
            Player.inventory.add(checkoutArmour);
        if (checkoutWheels != null)
            Player.inventory.add(checkoutWheels);
        if (checkoutSkin != null)
            Player.inventory.add(checkoutSkin);
    }
}