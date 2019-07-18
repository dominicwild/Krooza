package helper;


import handler.Assets;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * A class that manages the use of animations. It can play sprite sheets of any
 * size or layout.
 *
 * @author DominicWild
 */
public class Animation {

    /**
     * Enum used to define modes in play of the animation.
     *
     * In practice only used in testing right now, may remove in final version.
     */
    public enum Mode {

        LOOP
    };

    private int frames;                     //The amount of frames the animation has.
    private int currentFrame;               //The current frame of animation this animation is on.
    private int width;                      //The width of each frame.
    private int height;                     //The height of each frame.
    private Texture spriteSheet;            //The sprite sheet to be loaded into memory.
    private Sprite renderFrame;             //The current frame that is rendered or to be rendered.
    private boolean played;                 //Whether the animation has fully played out or not.
    private int columns;                    //The amount of columns on the sprite sheet.
    private Clock frameTime = new Clock();  //The personalized clock to time this animations change to the next frame.
    private int timePerFrame;               //The specified time to move onto the next frame.

    /**
     * Basic constructor to make a playable animation.
     *
     * @param width The width of each frame.
     * @param height The height of each frame.
     * @param columns The amount of columns on the sprite sheet.
     * @param frames The amount of frames the animation has.
     * @param timePerFrame The specified time to move onto the next frame.
     * @param sheetName The path to the used sprite sheet.
     */
    public Animation(int width, int height, int columns, int frames, int timePerFrame, String sheetName) {
        this.width = width;
        this.height = height;
        this.frames = frames;
        this.columns = columns;
        this.timePerFrame = timePerFrame;
        this.currentFrame = 0;
        this.played = false;
        this.spriteSheet = Assets.TEXTURE.get(sheetName);
        this.renderFrame = new Sprite(this.spriteSheet);
        this.renderFrame.setOrigin(width/2,height/2);
        renderFrame.setTextureRect(new IntRect(0, 0, width, height));
    }

    /**
     * Attempts to play a single frame of animation if the frame time
     * requirement is met. It does this by drawing the frame to the window, but
     * it does not display the frame. It will be displayed when display() is
     * called from the passed RenderWindow.
     *
     * @param window The window to draw the sprite to.
     */
    public void play(RenderWindow window) {
        if (currentFrame > frames) {
            currentFrame = 0;
            this.played = true;
        }
        if (!played && frameTime.getElapsedTime().asMilliseconds() >= this.timePerFrame) {
            int row = this.currentFrame / this.columns;
            int offset = this.currentFrame % this.columns;
            //Finds the correct frame in the sprite sheet to draw.
            this.renderFrame.setTextureRect(new IntRect(offset * width, row * height, width, height));
            currentFrame++;
            frameTime.restart(); //Restart timer to time next frame draw.
        }
        window.draw(renderFrame);  //Must draw frame on each play to ensure frame doesn't isn't instantly cleared.
    }

    /**
     * Attempts to play a single frame of animation if the frame time
     * requirement is met. It does this by drawing the frame to the window, but
     * it does not display the frame. It will be displayed when display() is
     * called from the passed RenderWindow.
     *
     * @param window The window to draw the sprite to.
     * @param mode Defines a mode of play for the animation.
     */
    public void play(RenderWindow window, Mode mode) {
        if (mode == Mode.LOOP) {
            if (currentFrame > frames - 1) {
                currentFrame = 0;
            }
        }
        this.play(window);
    }

    /**
     * Sets the position of where to play the animation.
     *
     * @param x X coordinate of where to play the animation.
     * @param y Y coordinate of where to play the animation.
     */
    public void setPosition(float x, float y) {
        this.renderFrame.setPosition(x, y);
    }

    /**
     * Resets internal state so the animation may be played again. Subsequent
     * calls to play() will trigger animation once more from the first frame of
     * animation.
     */
    public void replay() {
        this.played = false;
        this.currentFrame = 0;
    }

    /**
     * Scales the animation by a scale factor.
     *
     * @param scaleFactor Scale factor to increase/decrease size.
     */
    public void scale(float scaleFactor) {
        this.renderFrame.scale(scaleFactor, scaleFactor);
    }
    
    public boolean isFinished(){
        return this.played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
    
    public void setOrigin(float x, float y){
        this.renderFrame.setOrigin(x,y);
    }
    
    public void setOrigin(Vector2f origin){
        this.renderFrame.setOrigin(origin);
    }
    
    public void setPosition(Vector2f position){
        this.renderFrame.setPosition(position);
    }

    public void setAngle(float direction) {
        Helper.setAngle(direction, renderFrame);
    }
    
    

}
