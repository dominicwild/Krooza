package sequence;

import handler.Assets;
import handler.SequenceHandler;
import handler.SpriteHandler;
import menu.MenuType;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import window.MainWindow;

/**
 * @author Will Fantom
 */
public class IntroSequence
extends Sequence {

    //Content
    private Sprite splashLogo,
                    background,
                    foreground;

    //Timings
    private static int  LOGO_S = 1,
                        MAIN_S = 2;
    private Clock animationClock;

    public IntroSequence(MainWindow window){
        super(window, new Clock());
        this.setupContent();
        this.load();
    }

    protected void setupContent(){

        this.splashLogo = new Sprite(Assets.getTexture("MENU_LOGO"));
        this.background = new Sprite(Assets.getTexture("MENU_BG"));
        this.foreground = new Sprite(Assets.getTexture("MENU_FG"));

        SpriteHandler.centerOrigin(this.splashLogo);
        SpriteHandler.centerOrigin(this.background);
        SpriteHandler.centerOrigin(this.foreground);

        this.animationClock = new Clock();

    }

    protected void load(){

        this.splashLogo.scale( (float)(this.getWindow().getCamera().getSize().x/this.splashLogo.getGlobalBounds().width) ,
                (float)(this.getWindow().getCamera().getSize().y/this.splashLogo.getGlobalBounds().height));
        this.splashLogo.setPosition(this.getWindow().getCamera().getCenter());

        this.background.scale( (float)(this.getWindow().getCamera().getSize().x/this.background.getGlobalBounds().width) ,
                (float)(this.getWindow().getCamera().getSize().y/this.background.getGlobalBounds().height));
        this.background.setPosition(this.getWindow().getCamera().getCenter());

        this.foreground.scale( (float)(this.getWindow().getCamera().getSize().x/this.foreground.getGlobalBounds().width) ,
                (float)(this.getWindow().getCamera().getSize().y/this.foreground.getGlobalBounds().height));
        this.foreground.setPosition(this.getWindow().getCamera().getCenter());

    }

    public void draw(){

        //PART 1
        if(this.getSeconds() < LOGO_S){
            this.getWindow().draw(this.splashLogo);
        }

        //PART 2
        if(this.getSeconds() >= LOGO_S ){

            this.getWindow().draw(this.background);

            float dist = (this.getWindow().getView().getSize().y/((MAIN_S/2)*10));

            if(this.getSeconds() > (MAIN_S/2)+LOGO_S){
                if(this.animationClock.getElapsedTime().asMilliseconds() > 10) {
                    this.foreground.setPosition(this.foreground.getPosition().x, this.foreground.getPosition().y - dist);
                    this.animationClock.restart();
                }
            }

            this.getWindow().draw(this.foreground);

        }

        //END SEQUENCE
        if(this.getSeconds() > (MAIN_S+LOGO_S))
            this.endSequence();

    }

    public void endSequence(){
        Assets.dumpTexture("MENU_LOGO");
        Assets.dumpTexture("MENU_FG");
        this.getWindow().setSequence(null);
        this.getWindow().setMenu(MenuType.MAIN);
    }

}
