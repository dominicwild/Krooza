package window;

import handler.CSVHandler;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;

import java.awt.*;
import java.util.ArrayList;

/**
 * Keeps track of settings that are currently incident on the main window and
 * enforces them.
 *
 * @author Will Fantom
 */
public class Settings {

    //File Location
    private static final String FILE_PATH = "Content/Settings/Settings.csv",
            DEFAULT_PATH = "Content/Settings/DefaultSettings.csv",
            SPL = ",";

    //Window
    private static final String TITLE = "KROOZA";
    private final MainWindow window;

    //Settings
    private boolean fullscreen,
            windowed,
            borderless,
            vsync;
    private int framerate,
            width,
            height;

    public Settings(MainWindow window) {

        this.window = window;
        this.setDefault();

    }

    private void setDefault(){
        CSVHandler.writeCSV(FILE_PATH, CSVHandler.readCSV(DEFAULT_PATH));
        this.readSettings();
    }

    private void readSettings() {

        //Get Settings
        ArrayList<String> settings = CSVHandler.readCSV(FILE_PATH);

        //Save Settings
        for (String s : settings) {

            switch (s.split(SPL)[0].toLowerCase()) {

                case "fullscreen":
                    this.fullscreen = Boolean.parseBoolean(s.split(SPL)[1]);
                    break;
                case "windowed":
                    this.windowed = Boolean.parseBoolean(s.split(SPL)[1]);
                    break;
                case "borderless":
                    this.borderless = Boolean.parseBoolean(s.split(SPL)[1]);
                    break;
                case "vsync":
                    this.vsync = Boolean.parseBoolean(s.split(SPL)[1]);
                    break;
                case "framerate":
                    this.framerate = Integer.parseInt(s.split(SPL)[1]);
                    break;
                case "width":
                    this.setWidth(s.split(SPL)[1]);
                    break;
                case "height":
                    this.setHeight(s.split(SPL)[1]);
                    break;

            }

        }

    }

    /**
     * Applies the settings found in Settings.csv to the window
     */
    public void applySettings(){

        //Resolution
        VideoMode v = new VideoMode(this.width, this.height);

        //Window Type
        int type = 0;
        if(fullscreen)
            type = MainWindow.FULLSCREEN;
        else if(windowed)
            type = (MainWindow.TITLEBAR | MainWindow.CLOSE);
        else if(borderless)
            type = MainWindow.NONE;

        //Apply
        this.window.setupWindow(this.framerate, this.vsync, TITLE, type, v);

    }

    private Vector2f getNativeResolution(){
        Dimension r = Toolkit.getDefaultToolkit().getScreenSize();
        return new Vector2f((float)r.getWidth(), (float)r.getHeight());
    }
    private void setWidth(String w){
        if(w.toLowerCase().equals("native")){
            this.width = (int)this.getNativeResolution().x;
        } else {
            this.width = Integer.parseInt(w);
        }
    }
    private void setHeight(String h){
        if(h.toLowerCase().equals("native")){
            this.height = (int)this.getNativeResolution().y;
        } else {
            this.height = Integer.parseInt(h);
        }
    }


    public void setResolution(int x, int y){
        this.width = x;
        this.height = y;
    }
    
    public Vector2f getResolution(){
        return new Vector2f(this.width,this.height);
    }

    public void setWindowType(WindowType type){
        this.fullscreen = false;
        this.borderless = false;
        this.windowed = false;
        switch(type){
            case FULLSCREEN:    this.fullscreen = true; break;
            case WINDOWED:      this.windowed = true; break;
            case BORDERLESS:    this.borderless = true; break;
        }
    }

    public void setVsync(boolean v){
        this.vsync = v;
    }
    
    


}
