package menu;

import camera.Camera;
import handler.Assets;
import java.util.Map;

import window.MainWindow;
import world.HordeWorld;
import world.OverWorld;

/**
 * A menu that displays the modes the player can play in.
 * @author DominicWild, Will Fantom
 */
public class ModeMenu extends Menu {

    //Scale
    private static float BUTTON_SCALE = (float) 0.15;

    public ModeMenu(MainWindow mainWindow) {
        super(mainWindow);
        this.setupButtons();
        this.load();
    }

    private void setupButtons() {
        this.buttons.put("HORDE", new Button("MODE_HRD", "MODE_HRD_H"));
        this.buttons.put("SURVIVAL", new Button("MODE_SVL", "MODE_SVL_H"));
//        this.buttons.put("TIMETRIAL", new Button("MODE_TT", "MODE_TT_H"));
    }

    private void scaleButtons() {
        float height = this.window.getCamera().getSize().y;
        Camera camera = this.window.getCamera();
        //Setup Buttons
        for (Map.Entry<String, Button> b : buttons.entrySet()) {
            float bScale = (float) (height * BUTTON_SCALE / b.getValue().getLocalBounds().height);
            b.getValue().setScale(bScale, bScale);
        }
        float btnHeight = this.buttons.get("HORDE").getGlobalBounds().height;
        this.buttons.get("HORDE").setPosition(camera.getCenter().x, (float) (camera.getCenter().y - (1 * btnHeight)));
        this.buttons.get("SURVIVAL").setPosition(camera.getCenter().x, (float) (camera.getCenter().y));
//        this.buttons.get("TIMETRIAL").setPosition(camera.getCenter().x, (float) (camera.getCenter().y + (1 * btnHeight)));
    }

    @Override
    public void load() {
        this.fitBackgroundTexture("MENU_BG");
        this.scaleButtons();
    }

    @Override
    protected void handleClick(String btn) {
        switch (btn) {
            case "HORDE": 
                this.window.setMenu(null);
                this.window.setWorld(new HordeWorld(window, "Content/Map/horde.csv"));
                Assets.getSound("MS_1").stop();
                break;
            case "SURVIVAL":	//SET SURVIVAL MODE;
                this.window.setMenu(null);
                this.window.setWorld(new OverWorld(window, "Content/Map/main.csv"));
                Assets.getSound("MS_1").stop();
                break;
            case "TIMETRIAL": 	//SET TIME TRIAL MODE;
                break;
        }

    }

}
