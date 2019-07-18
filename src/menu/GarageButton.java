package menu;

import helper.Player;
import mods.*;

/**
 * A button unique to the garage. Has special hovering behaviour to deal with
 * the limitations of the implemented base button class.
 *
 * @author DominicWild
 */
public class GarageButton extends Button {

    private GarageButtonType type;          //Determines what hover behaviour will be used.
    private boolean clicked = false;

    public GarageButton(String baseTexture, String hoverTexture, GarageButtonType type) {
        super(baseTexture, hoverTexture);
        this.type = type;
    }

    @Override
    public String getHoverTexture() {
        if (this.isHovering() && !clicked) {
            switch (type) {
                case ENGINE:
                    return this.hoverTexString(Player.inventory.getNextEngine(false));
                case FRONT_MOD:
                    return this.hoverTexString(Player.inventory.getNextFrontMod(false));
                case BACK_MOD:
                    return this.hoverTexString(Player.inventory.getNextBackMod(false));
                case WHEELS:
                    return this.hoverTexString(Player.inventory.getNextWheels(false));
                case TURRET:
                    return this.hoverTexString(Player.inventory.getNextTurret(false));
                case EXHAUST:
                    return this.hoverTexString(Player.inventory.getNextExhaust(false));
                case SKIN:
                    return this.hoverTexString(Player.inventory.getNextSkin(false));
                case ARMOUR:
                    return this.hoverTexString(Player.inventory.getNextArmour(false));
                default:
                    return this.hoverTexString(null);
            }
        }
        this.hoverTexture = this.baseTexture;
        return this.baseTexture;
    }

    private String hoverTexString(BaseMod m) {
        if (m == null) {
            return "Icon_hover";
        } else {
            return m.getDescriptor() + "_icon";
        }
    }

    @Override
    public void setHovering(boolean h) {
        super.setHovering(h);
        if (!this.isHovering()) {
            this.clicked = false;
        }
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

}
