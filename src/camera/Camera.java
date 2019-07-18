package camera;

import main.Debug;
import main.DebugType;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A camera used to pan around the map and follow the player. - Dominic Wild
 * @author Will Fantom
 */
public class Camera extends View {

    //Window
    private Vector2f windowSize;

    //View
    private static final int STD_Y = 1080;
    public static float ZOOM_DELTA = (float)0.1;
    private float       scale;
    private Vector2f    size,
                        center;

    /**
     *
     */
    public Camera(Vector2f windowSize){
        this.windowSize = windowSize;
        this.setup();
    }

    private void setup(){

        this.scale = this.windowSize.y / STD_Y;
        this.scale = 1;         //for my windowed set up, for testing only
        this.size = new Vector2f( (this.windowSize.x/scale), (this.windowSize.y/scale) );
        this.center = new Vector2f( (this.size.x/2), (this.size.y/2) );
        Debug.print(("Camera setup with dimensions: " + (int)this.size.x + "x" + (int)this.size.y), DebugType.LOADED);

        this.setSize(this.size);
        this.setCenter(this.center);

    }

    /**
     * Moves the view's center by a given delta in x and y
     * @param delta
     */
    public void pan(Vector2f delta){
        this.setCenter(this.getCenter().x + delta.x, this.getCenter().y + delta.y);
    }

    /**
     * Zooms the camera in by a set delta
     */
    public void zoomIn(){
        this.zoom(1-ZOOM_DELTA);
    }

    /**
     * Zooms the camera out by a set delta
     */
    public void zoomOut(){
        this.zoom(1+ZOOM_DELTA);
    }


}
