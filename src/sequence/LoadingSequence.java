package sequence;

import handler.Assets;
import handler.SequenceHandler;
import handler.SpriteHandler;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Clock;
import window.MainWindow;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Will Fantom
 */
public class LoadingSequence
extends Sequence {

    //Content
    private Sprite loadingCircle;
    private Text loadMessage;
    private Font light;
    private static String  MESSAGE = "Don't Drink & Krooze!";

    //Clock
    private Clock animationClock;

    public LoadingSequence(MainWindow window){
        super(window, new Clock());
        this.setupContent();
        this.load();
    }

    @Override
    protected void setupContent() {
        this.animationClock = new Clock();
        this.loadingCircle = new Sprite(Assets.getTexture("LOAD_C"));
        this.light = new Font();
        try {
            this.light.loadFromFile(Paths.get("Content/Assets/Fonts/light.ttf"));
        } catch (IOException e) {
            System.out.println("<!> Error: Could not load in the light font");
        }
        this.loadMessage = new Text(MESSAGE, this.light, 12);
        SpriteHandler.centerOrigin(this.loadingCircle);
    }

    @Override
    protected void load() {
        SpriteHandler.scale(this.loadingCircle, (this.getWindow().getCamera().getSize().y/this.loadingCircle.getGlobalBounds().height)/8);
        float x = this.getWindow().getView().getCenter().x + (this.getWindow().getCamera().getSize().x/2) - (this.loadingCircle.getGlobalBounds().width);
        float y = this.getWindow().getView().getCenter().y + (this.getWindow().getCamera().getSize().y/2) - (this.loadingCircle.getGlobalBounds().height);
        this.loadingCircle.setPosition(x, y);
        this.loadMessage.setCharacterSize((int)(this.loadingCircle.getGlobalBounds().height/8));
        this.loadMessage.setPosition(this.loadingCircle.getGlobalBounds().left - (this.loadMessage.getGlobalBounds().width) - (this.loadingCircle.getGlobalBounds().width/5),
                this.loadingCircle.getGlobalBounds().top+(this.loadingCircle.getGlobalBounds().height/2));
    }

    @Override
    public void draw() {
        if(this.animationClock.getElapsedTime().asMilliseconds() > 10){
            SpriteHandler.rotate(this.loadingCircle, 2);
            this.animationClock.restart();
        }
        this.getWindow().draw(this.loadingCircle);
        this.getWindow().draw(this.loadMessage);
    }
}
