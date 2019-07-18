package helper;

import handler.Assets;
import java.util.ArrayList;

import org.jsfml.audio.SoundSource.Status;
import org.jsfml.window.Keyboard;
import window.MainWindow;

public class Radio implements Runnable {

    //WINDOW
    private MainWindow window;

    //CONTENT
    private ArrayList<String> song;
    private int currentTrack;
    private int currentVolume;

    //CONTROL
    private boolean isActive;
    private Thread radioThread;

    public Radio(MainWindow window) {
        //Add Tracks
        this.addTracks();
        //Setup
        this.window = window;
        this.currentVolume = 90;
        this.currentTrack = 0;
        this.isActive = false;
        //Control Setup
        this.radioThread = new Thread(this);
        this.radioThread.start();
    }

    /**
     * Adds tracks that the Radio can play
     */
    private void addTracks() {
        this.song = new ArrayList<String>();
        this.song.add("Sandstorm_Darude_8bit");
        this.song.add("StayinAlive_BeeGees_8bit");
    }

    private void playTrack() {
        if (Assets.getSound(song.get(this.currentTrack)).getStatus() != Status.PLAYING) {
            Assets.getSound(song.get(this.currentTrack)).play();
        }
    }

    private void pauseTrack() {
        if (Assets.getSound(song.get(this.currentTrack)).getStatus() == Status.PLAYING) {
            Assets.getSound(song.get(this.currentTrack)).pause();
        }
    }

    /**
     * Sets the volume the radio plays at (default 90)
     *
     * @param v - the volume (between 0 and 100)
     */
    public void setVolume(int v) {
        if (v >= 0 || v <= 100) {
            this.currentVolume = v;
        }
    }

    private void radioCtrl() {
        if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.R)) {
            this.isActive = !this.isActive;

            if (this.window.getEventHandler().isKeyHeldDown(Keyboard.Key.TAB)) {
                if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.UP)) {
                    this.setVolume(this.currentVolume + 5);
                }
                if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.DOWN)) {
                    this.setVolume(this.currentVolume - 5);
                }
                if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.M)) {
                    this.setVolume(0);
                }
                if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.RIGHT)) {
                    this.currentTrack++;
                    if (this.currentTrack >= song.size()) {
                        this.currentTrack = 0;
                    }
                }
                if (this.window.getEventHandler().isKeyReleased(Keyboard.Key.LEFT)) {
                    this.currentTrack--;
                    if (this.currentTrack < 0) {
                        this.currentTrack = song.size() - 1;
                    }
                }
            }
        }
    }

    public void run() {
        while (this.window.isOpen()) {
            this.radioCtrl();
            if (this.isActive) {
                Assets.getSound(song.get(this.currentTrack)).setVolume(this.currentVolume);
                this.playTrack();
                if (Assets.getSound(song.get(this.currentTrack)).getStatus() == Status.STOPPED) {
                    this.currentTrack++;
                    if (this.currentTrack <= song.size()) {
                        this.currentTrack = 0;
                    }
                }
            } else {
                this.pauseTrack();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
