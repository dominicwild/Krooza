
package staticobjects;

import helper.Player;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

/**
 * A prompt that appears over the players head. More specifically, triggers a
 * pulsating sprite to appear above their head.
 *
 * @author DominicWild
 */
public class Prompt extends SolidEntity implements Drawable {

    private boolean toPrompt;
    private float pulsateRange = (float) 0.25;
    private float baseScale = 1;
    private boolean pulsingDown = true;
    private float scaleStep = (float) 0.01;
    
    public Prompt(Transformable object) {
        super(object);
        this.toPrompt = false;
        this.layer = 2;
    }
    
    public void prompt(){
        this.toPrompt = true;
    }
    
    public void stopPrompt(){
        this.toPrompt = false;
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        if (toPrompt) {
            if (!this.pulsingDown) {
                float newScale = (float) (this.object.getScale().x + scaleStep);
                this.setScale(newScale);
                if (this.baseScale + this.pulsateRange < this.object.getScale().x) {
                    this.pulsingDown = true;
                }
            } else {
                float newScale = (float) (this.object.getScale().x - scaleStep);
                this.setScale(newScale);
                if (this.baseScale - this.pulsateRange > this.object.getScale().x) {
                    this.pulsingDown = false;
                }
            }
            Vector2f offset = new Vector2f(0,-50);
            this.object.setPosition(Vector2f.add(Player.getPosition(),offset));
            target.draw((Sprite)this.getObject());
        }
        this.stopPrompt();
    }
    
    public void setScale(float scale){
        this.getObject().setScale(scale,scale);
    }
    
    public void setBaseScale(float scale){
        this.baseScale = scale;
        this.setScale(scale);
    }
    
    
    
}
