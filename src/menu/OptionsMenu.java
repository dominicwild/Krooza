package menu;

import handler.SpriteHandler;
import main.Debug;
import main.DebugType;
import org.jsfml.system.Vector2f;
import window.MainWindow;
import window.WindowType;

import java.awt.*;
import java.util.Map;

/**
 * A menu allowing the play to change several in-game options. Such as VSync or
 * resolution. - Dominic Wild 
 * 
 * Created by Will Fantom on 21/02/2017.
 */
public class OptionsMenu
        extends Menu {

    //Scale
    private static Vector2f BUTTON_SCALE = new Vector2f((float) 0.35, (float) 0.15);

    //COUNT
    private static int	RES_OPTIONS = 6,
                        TYPE_OPTIONS = 3,
                        VSYNC_OPTIONS = 2;

    //OPTIONS
    private static Vector2f     RESOLUTION[] = new Vector2f[RES_OPTIONS];
    private static WindowType   WINDOWTYPE[] = new WindowType[TYPE_OPTIONS];
    private static boolean		VSYNC[]	= new boolean[VSYNC_OPTIONS];
    private static Vector2f     NATIVE_RESOLUTION;



    //CURRENT SELECTION
    private int resolution,
                windowType,
                vsync;

    public OptionsMenu(MainWindow window) {
        super(window);

        this.setupResolutions();
        this.setupWindowType();
        this.setupVsync();

        this.setupButtons();

        this.load();
        Debug.print("Options Menu Loaded", DebugType.LOADED);

    }

    private void setupResolutions(){

        RESOLUTION[1] = new Vector2f( 3440 , 1440 );
        RESOLUTION[2] = new Vector2f( 1280 , 720 );
        RESOLUTION[3] = new Vector2f( 1920 , 1080 );
        RESOLUTION[4] = new Vector2f( 2560 , 1440 );
        RESOLUTION[5] = new Vector2f( 3840 , 2160 );

        Dimension r = Toolkit.getDefaultToolkit().getScreenSize();
        NATIVE_RESOLUTION = new Vector2f( (float)r.getWidth(), (float)r.getHeight() );

        RESOLUTION[0] = NATIVE_RESOLUTION;

        this.resolution = 0;

    }

    private void setupWindowType(){

        WINDOWTYPE[2] = WindowType.FULLSCREEN;
        WINDOWTYPE[1] = WindowType.WINDOWED;
        WINDOWTYPE[0] = WindowType.BORDERLESS;

        this.windowType = 2;

    }

    private void setupVsync(){

        VSYNC[0] = true;
        VSYNC[1] = false;

        this.vsync = 0;

    }

    private void setupButtons(){
        this.buttons.put("RES", new Button("OPT_R0", "OPT_R0"));
        this.buttons.put("TYPE", new Button("OPT_T3", "OPT_T3"));
        this.buttons.put("VSYNC", new Button("OPT_V1", "OPT_V1"));
        this.buttons.put("CONFIRM", new Button("OPT_CB", "OPT_CBH"));
    }

    private void scaleButtons(){

        float height = this.window.getCamera().getSize().y;
        float width = this.window.getCamera().getSize().x;

        //Setup Buttons
        for(Map.Entry<String, Button> b : buttons.entrySet()){
            SpriteHandler.scale(b.getValue(), ( (width*BUTTON_SCALE.x)/b.getValue().getGlobalBounds().width ), ( (height*BUTTON_SCALE.y)/b.getValue().getGlobalBounds().height ));
        }

        float btnHeight = this.buttons.get("RES").getGlobalBounds().height;
        this.buttons.get("RES").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y - (1.5*btnHeight)));
        this.buttons.get("TYPE").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y - (0.5*btnHeight)));
        this.buttons.get("VSYNC").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y + (0.5*btnHeight)));
        this.buttons.get("CONFIRM").setPosition(this.window.getCamera().getCenter().x, (float) (this.window.getCamera().getCenter().y + (1.5*btnHeight)));

    }

    @Override
    protected void load() {
        this.fitBackgroundTexture("MENU_BG");
        this.scaleButtons();
    }

    private void nextRes() {
        if (this.windowType != 2) {
            this.resolution++;
            if (this.resolution > RES_OPTIONS - 1) {
                this.resolution = 0;
            }
            this.buttons.remove("RES");
            String b = "OPT_R" + String.valueOf(this.resolution);
            this.buttons.put("RES", new Button(b, b));
            this.scaleButtons();
        }
    }

    private void nextType(){
        this.windowType++;
        if(this.windowType > TYPE_OPTIONS-1)
            this.windowType = 0;
        this.buttons.remove("TYPE");
        String b = "OPT_T" + String.valueOf(this.windowType+1);
        this.buttons.put("TYPE", new Button(b, b) );

        if(this.windowType == 2){
            this.resolution = 0;
            this.buttons.put("RES",new Button("OPT_R0", "OPT_R0"));
        }
        this.scaleButtons();
    }

    private void nextVsync(){

        this.vsync++;
        if(this.vsync > VSYNC_OPTIONS-1)
            this.vsync = 0;
        this.buttons.remove("VSYNC");
        String b = "OPT_V" + String.valueOf(this.vsync+1);
        this.buttons.put("VSYNC", new Button(b, b) );
        this.scaleButtons();

    }

    private void confirmSettings(){

        this.window.getWindowSettings().setResolution((int)RESOLUTION[this.resolution].x, (int)RESOLUTION[this.resolution].y);
        this.window.getWindowSettings().setVsync((VSYNC[this.vsync]));
        this.window.getWindowSettings().setWindowType(WINDOWTYPE[this.windowType]);
        this.window.getWindowSettings().applySettings();
        this.window.setMenu(MenuType.MAIN);

    }

    public Vector2f getResolution(){
        return RESOLUTION[this.resolution];
    }

    public WindowType getType(){
        return WINDOWTYPE[this.windowType];
    }

    public boolean getVsync(){
        return VSYNC[this.vsync];
    }

    @Override
    protected void handleClick(String btn) {

        switch (btn) {
            case "RES":
                this.nextRes();
                break;

            case "TYPE":
                this.nextType();
                break;

            case "VSYNC":
                this.nextVsync();
                break;

            case "CONFIRM":
                this.confirmSettings();
                break;
        }
    }
}
