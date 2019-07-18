
package staticobjects;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * A class representing an object that doesn't move.
 * @author DominicWild
 */
 public class SolidEntity implements Drawable, FrameBehaviour {

    protected Transformable object;         //The reprsentation of the SolidEntity (A Sprite or Shape)
    protected int layer = 1;                //The layer to be drawn at. Higher layers are drawn on top, lower layers on the bottom.

    /**
     * Constructs a basic SolidEntity.
     * @param object A Sprite or Shape that represents the appearance of this SolidEntity.
     */
    public SolidEntity(Transformable object) {
        if (object instanceof Shape || object instanceof Sprite) {
            this.object = object;
            this.setCenter();
        } else {
            throw new IllegalArgumentException("Argument must be of type Shape or Sprite.");
        }
    }
    
    /**
     * Gets the global bounds for this object.
     * @return The global bounds as a FloatRect for this object.
     */
    public FloatRect getObjectBounds() {
        if (this.object instanceof Sprite) {
            Sprite s1 = (Sprite) this.object;
            return s1.getGlobalBounds();
        } else {
            Shape s1 = (Shape) this.object;
            return s1.getGlobalBounds();
        }
    }

    //No direct need to call this, window.draw(object) works just as well.
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        target.draw((Drawable) this.object);
    }

    public Transformable getObject() {
        return object;
    }
    
    @Override
    public void behave() {

    }

    public int getLayer() {
        return layer;
    }

     public Vector2f getPosition() {
         return this.object.getPosition();
     }
     
     public void setPosition(float x, float y){
         this.object.setPosition(x,y);
     }
     
     public void setPosition(Vector2f point){
         this.object.setPosition(point.x,point.y);
     }

     private void setCenter() {
         if (this.object instanceof Sprite) {
             Sprite s = (Sprite) this.object;
             Vector2i size = s.getTexture().getSize();
             s.setOrigin(size.x / 2, size.y / 2);
         } else {
             Shape s = (Shape) this.object;
             Vector2i size = s.getTexture().getSize();
             s.setOrigin(size.x / 2, size.y / 2);
         }
     }
    
    
}
