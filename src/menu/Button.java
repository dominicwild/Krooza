package menu;

import handler.Assets;
import org.jsfml.graphics.Sprite;

/**
 * A class that represents a button on a menu. - Dominic Wild
 * Created by Will Fantom on 21/02/2017.
 */
public class Button extends Sprite {

    protected String baseTexture, hoverTexture;

    private boolean hovering;

    public Button(String baseTexture, String hoverTexture) {
        this.baseTexture = baseTexture;
        this.hoverTexture = hoverTexture;
        this.hovering = false;
        this.setTex();
    }

    public void setHovering(boolean h) {
        this.hovering = h;
        this.setTex();
    }

    private void setTex() {
        if (!hovering) {
            this.setTexture(Assets.getTexture(this.baseTexture));
        } else {
            this.setTexture(Assets.getTexture(this.getHoverTexture()));
        }
        this.setOrigin((this.getLocalBounds().width / 2) + this.getLocalBounds().left,
                (this.getLocalBounds().height / 2) + this.getLocalBounds().top);
    }

    public void setBaseTexture(String baseTexture) {
        this.baseTexture = baseTexture;
    }
    
    public String getHoverTexture(){
        return this.hoverTexture;
    }

    public boolean isHovering() {
        return hovering;
    }
    

}
