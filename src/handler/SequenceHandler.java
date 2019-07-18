package handler;

import main.Debug;
import main.DebugType;
import sequence.*;
import org.jsfml.window.Keyboard;
import window.MainWindow;

/**
 * A class responsible for handling transitions between sequences. Sequences are
 * a series a pictures. - Dominic Wild
 *
 * @author Will Fantom
 */
public class SequenceHandler {

    //WINDOW
    private MainWindow window;

    //CURRENT
    private SequenceType currentSequence;

    //SEQUENCES
    private LoadingSequence loadingSequence;
    private IntroSequence introSequence;
    
    public SequenceHandler(MainWindow window) {
        this.window = window;
        this.currentSequence = null;
    }

    /**
     * Sets the current sequence
     *
     * @param type
     */
    public void setSequence(SequenceType type) {
        this.currentSequence = type;
        this.loadingSequence = null;
        this.introSequence = null;

        if (this.currentSequence != null) {
            switch (this.currentSequence) {
                case INTRO:
                    this.introSequence = new IntroSequence(this.window);
                    break;
                case LOADING:
                    this.loadingSequence = new LoadingSequence(this.window);
                    break;
            }
        }
        Debug.print("Active sequence " + this.currentSequence, DebugType.LOADED);
    }

    /**
     * Draws the current sequence to a window
     */
    public void draw() {
        if (this.currentSequence != null) {
            switch (this.currentSequence) {
                case INTRO:
                    this.introSequence.draw();
                    break;
                case LOADING:
                    if (this.loadingSequence != null) {
                        this.loadingSequence.draw();
                    }
                    break;
            }
        }
    }

    /**
     * Handles sequence toggling
     */
    public void handleEvents() {
        EventHandler eH = this.window.getEventHandler();
        if (this.currentSequence == SequenceType.INTRO && (eH.isKeyReleased(Keyboard.Key.SPACE) || eH.isKeyReleased(Keyboard.Key.ESCAPE))) {
            this.introSequence.endSequence();
        }
    }

    /**
     * Gets the current sequence
     *
     * @return
     */
    public SequenceType getCurrentSequence() {
        return this.currentSequence;
    }

}
