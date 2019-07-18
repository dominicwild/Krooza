package sequence;

import handler.SequenceHandler;
import org.jsfml.system.Clock;
import window.MainWindow;

/**
 * Created by Will Fantom
 */
public abstract class Sequence {

    //Window
    private MainWindow window;

    //Sequence
    private SequenceHandler handler;
    private Clock clock;

    public Sequence(MainWindow window, Clock clock){

        this.window = window;
        this.clock = clock;
        this.clock.restart();

    }

    protected MainWindow getWindow(){
        return this.window;
    }

    protected int getSeconds(){
        return (int)this.clock.getElapsedTime().asSeconds();
    }
    protected int getMSeconds(){
        return (int)this.clock.getElapsedTime().asMilliseconds();
    }

    protected void restartClock(){
        this.clock.restart();
    }


    protected abstract void setupContent();

    protected abstract void load();

    protected abstract void draw();

}
