package helper;

import java.util.ArrayList;
import mods.ArmourMod;
import mods.BackMod;
import mods.BaseMod;
import mods.Engine;
import mods.ExhaustMod;
import mods.FrontMod;
import mods.Skin;
import mods.TurretMod;
import mods.WheelMod;

/**
 * Represents an inventory of in-game items.
 * @author DominicWild
 */
public class Inventory {

    protected ArrayList<BaseMod> allMods;

    protected ArrayList<Engine> engines;
    protected int currentEngine;

    protected ArrayList<ExhaustMod> exhausts;
    protected int currentExhaust;

    protected ArrayList<TurretMod> turrets;
    protected int currentTurret;

    protected ArrayList<FrontMod> frontMods;
    protected int currentFrontMod;

    protected ArrayList<BackMod> backMods;
    protected int currentBackMod;

    protected ArrayList<WheelMod> wheels;
    protected int currentWheels;

    protected ArrayList<ArmourMod> armour;
    protected int currentArmour;

    protected ArrayList<Skin> skins;
    protected int currentSkin;

    public Inventory() {
        engines = new ArrayList<>();
        currentEngine = -1;
        exhausts = new ArrayList<>();
        currentExhaust = -1;
        turrets = new ArrayList<>();
        currentTurret = -1;
        frontMods = new ArrayList<>();
        currentFrontMod = -1;
        backMods = new ArrayList<>();
        currentBackMod = -1;
        wheels = new ArrayList<>();
        currentWheels = -1;
        armour = new ArrayList<>();
        currentArmour = -1;
        skins = new ArrayList<>();
        currentSkin = -1;

        allMods = new ArrayList<>();
    }

    public void add(BaseMod m){
        if(m instanceof Engine){
            this.addEngine((Engine) m);
        } else if(m instanceof ArmourMod){
            this.addArmour((ArmourMod) m);
        } else if(m instanceof FrontMod){
            this.addFrontMod((FrontMod) m);
        } else if(m instanceof BackMod){
            this.addBackMod((BackMod) m);
        } else if(m instanceof Skin){
            this.addSkin((Skin) m);
        } else if(m instanceof WheelMod){
            this.addWheels((WheelMod) m);
        } else if(m instanceof TurretMod){
            this.addTurret((TurretMod) m);
        } else if(m instanceof ExhaustMod){
            this.addExhaust((ExhaustMod) m);
        }
    }

    public boolean contains(BaseMod m) {
        if(m instanceof Engine){
            for(BaseMod e : engines){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof ArmourMod){
            for(BaseMod e : armour){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof FrontMod){
            for(BaseMod e : frontMods){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof BackMod){
            for(BaseMod e : backMods){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof Skin){
            for(BaseMod e : skins){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof WheelMod){
            for(BaseMod e : wheels){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof TurretMod){
            for(BaseMod e : turrets){
                if(e.equals(m))
                    return true;
            }
        } else if(m instanceof ExhaustMod){
            for(BaseMod e : exhausts) {
                if(e.equals(m))
                    return true;
            }
        }

        return false;
    }

    private void addEngine(Engine engine) {
        engines.add(engine);
        allMods.add(engine);
    }

    public Engine getNextEngine(boolean inc) {
        if (engines.size() <= 1) {
            return null;
        } else {
            if (currentEngine + 1 >= engines.size()) {
                if (inc) {
                    currentEngine = 0;
                    return engines.get(currentEngine);
                }
                return engines.get(0);
            } else {
                if (inc) {
                    currentEngine++;
                    return engines.get(currentEngine);
                }
                return engines.get(currentEngine+1);
            }
        }
    }

    private void addExhaust(ExhaustMod exhaust) {
        exhausts.add(exhaust);
        allMods.add(exhaust);
    }

    public ExhaustMod getNextExhaust(boolean inc) {
        if (exhausts.size() <= 1) {
            return null;
        } else {
            if (currentExhaust + 1 >= exhausts.size()) {
                if (inc) {
                    currentExhaust = 0;
                    return exhausts.get(currentExhaust);
                }
                return exhausts.get(0);
            } else {
                if (inc) {
                    currentExhaust++;
                    return exhausts.get(currentExhaust);
                }
                return exhausts.get(currentExhaust+1);
            }
        }
    }

    private void addTurret(TurretMod turret) {
        turrets.add(turret);
        allMods.add(turret);
    }

    public TurretMod getNextTurret(boolean inc) {
        if (turrets.size() <= 1) {
            return null;
        } else {
            if (currentTurret + 1 >= turrets.size()) {
                if (inc) {
                    currentTurret = 0;
                    return turrets.get(currentTurret);
                }
                return turrets.get(0);
            } else {
                if (inc) {
                    currentTurret++;
                    return turrets.get(currentTurret);
                }
                return turrets.get(currentTurret+1);
            }
        }
    }

    private void addFrontMod(FrontMod frontMod) {
        frontMods.add(frontMod);
        allMods.add(frontMod);
    }

    public FrontMod getNextFrontMod(boolean inc) {
        if (frontMods.size() <= 1) {
            return null;
        } else {
            if (currentFrontMod + 1 >= frontMods.size()) {
                if (inc) {
                    currentFrontMod = 0;
                    return frontMods.get(currentFrontMod);
                }
                return frontMods.get(0);
            } else {
                if (inc) {
                    currentFrontMod++;
                    return frontMods.get(currentFrontMod);
                }
                return frontMods.get(currentFrontMod+1);
            }
        }
    }


    private void addBackMod(BackMod backMod) {
        backMods.add(backMod);
        allMods.add(backMod);
    }

    public BackMod getNextBackMod(boolean inc) {
        if (backMods.size() <= 1) {
            return null;
        } else {
            if (currentBackMod + 1 >= backMods.size()) {
                if (inc) {
                    currentBackMod = 0;
                    return backMods.get(currentBackMod);
                }
                return backMods.get(0);
            } else {
                if (inc) {
                    currentBackMod++;
                    return backMods.get(currentBackMod);
                }
                return backMods.get(currentBackMod+1);
            }
        }
    }

    private void addWheels(WheelMod wheelSet) {
        wheels.add(wheelSet);
        allMods.add(wheelSet);
    }

    public WheelMod getNextWheels(boolean inc) {
        if (wheels.size() <= 1) {
            return null;
        } else {
            if (currentWheels + 1 >= wheels.size()) {
                if (inc) {
                    currentWheels = 0;
                    return wheels.get(currentWheels);
                }
                return wheels.get(0);
            } else {
                if (inc) {
                    currentWheels++;
                    return wheels.get(currentWheels);
                }
                return wheels.get(currentWheels+1);
            }
        }
    }

    private void addArmour(ArmourMod armourSet) {
        armour.add(armourSet);
        allMods.add(armourSet);
    }

    public ArmourMod getNextArmour(boolean inc) {
        if (armour.size() <= 1) {
            return null;
        } else {
            if (currentArmour + 1 >= armour.size()) {
                if (inc) {
                    currentArmour = 0;
                    return armour.get(currentArmour);
                }
                return armour.get(0);
            } else {
                if (inc) {
                    currentArmour++;
                    return armour.get(currentArmour);
                }
                return armour.get(currentArmour+1);
            }
        }
    }

    private void addSkin(Skin skin) {
        skins.add(skin);
        allMods.add(skin);
    }

    public Skin getNextSkin(boolean inc) {
        if (skins.size() <= 1) {
            return null;
        } else {
            if (currentSkin + 1 >= skins.size()) {
                if (inc) {
                    currentSkin = 0;
                    return skins.get(currentSkin);
                }
                return skins.get(0);
            } else {
                if (inc) {
                    currentSkin++;
                    return skins.get(currentSkin);
                }
                return skins.get(currentSkin+1);
            }
        }
    }
    
    public void setIndex(BaseMod m){
        int i = 0;
        if(m instanceof Engine){
            for(BaseMod e : engines){
                if(e == m){
                    this.currentEngine = i;
                }
                i++;
            }
        } else if(m instanceof ArmourMod){
            for(BaseMod e : armour){
                if(e == m){
                    this.currentArmour = i;
                }
                i++;
            }
        } else if(m instanceof FrontMod){
            for(BaseMod e : frontMods){
                if(e == m){
                    this.currentFrontMod = i;
                }
                i++;
            }
        } else if(m instanceof BackMod){
            for(BaseMod e : backMods){
                if(e == m){
                    this.currentBackMod = i;
                }
                i++;
            }
        } else if(m instanceof Skin){
            for(BaseMod e : skins){
                if(e == m){
                    this.currentSkin = i;
                }
                i++;
            }
        } else if(m instanceof WheelMod){
            for(BaseMod e : wheels){
                if(e == m){
                    this.currentWheels = i;
                }
                i++;
            }
        } else if(m instanceof TurretMod){
            for(BaseMod e : turrets){
                if(e == m){
                    this.currentTurret = i;
                }
                i++;
            }
        } else if(m instanceof ExhaustMod){
            for(BaseMod e : exhausts){
                if(e == m){
                    this.currentExhaust = i;
                }
                i++;
            }
        }
    }
    
    
}
