package menu;

import helper.Inventory;
import mods.ArmourMod;
import mods.BackMod;
import mods.Engine;
import mods.ExhaustMod;
import mods.FrontMod;
import mods.Skin;
import mods.TurretMod;
import mods.WheelMod;

/**
 * The inventory that a vendor has for sale to a player. - Dominic Wild
 *
 * @author Alex Bently
 */
public class VendorInventory extends Inventory {

    @Override
    public Engine getNextEngine(boolean inc) {
        if (engines.size() < 1) {
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
                return engines.get(currentEngine + 1);
            }
        }
    }

    @Override
    public ExhaustMod getNextExhaust(boolean inc) {
        if (exhausts.size() < 1) {
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
                return exhausts.get(currentExhaust + 1);
            }
        }
    }

    @Override
    public TurretMod getNextTurret(boolean inc) {
        if (turrets.size() < 1) {
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
                return turrets.get(currentTurret + 1);
            }
        }
    }

    @Override
    public FrontMod getNextFrontMod(boolean inc) {
        if (frontMods.size() < 1) {
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
                return frontMods.get(currentFrontMod + 1);
            }
        }
    }

    @Override
    public BackMod getNextBackMod(boolean inc) {
        if (backMods.size() < 1) {
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
                return backMods.get(currentBackMod + 1);
            }
        }
    }

    @Override
    public WheelMod getNextWheels(boolean inc) {
        if (wheels.size() < 1) {
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
                return wheels.get(currentWheels + 1);
            }
        }
    }

    @Override
    public ArmourMod getNextArmour(boolean inc) {
        if (armour.size() < 1) {
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
                return armour.get(currentArmour + 1);
            }
        }
    }

    @Override
    public Skin getNextSkin(boolean inc) {
        if (skins.size() < 1) {
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
                return skins.get(currentSkin + 1);
            }
        }
    }
}
