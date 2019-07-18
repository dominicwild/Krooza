package camera;

import handler.Assets;
import helper.Animation;
import helper.Helper;
import helper.Player;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Sprite;
import window.MainWindow;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * A heads up display, that displays on-screen character information. Currently
 * displays, amount of scrap, amount of speed, cool downs and player health.
 *
 * @author DominicWild.
 */
public class HUD {

    public static boolean hideHud = false;

    static {
        coolDownAnimations = new ArrayList<>();
    }

    //WINDOW
    private MainWindow window;
    public static ArrayList<Animation> coolDownAnimations;
    public static final int COOL_DOWN_WIDTH = 787;
    public static final int COOL_DOWN_HEIGHT = 761;
    public static final float COOL_DOWN_SCALE = (float) 0.1;
    //DISTANCE
    float margin;

    //CONTENT
    private Sprite hpFrame;
    private Sprite speedometer ;
    private Sprite scrapCounter;

    static {
        coolDownAnimations = new ArrayList<>();
    }

    public HUD(MainWindow window) {
        this.window = window;
        this.setContent();
    }

    private void setContent() {
        this.speedometer = new Sprite(Assets.getTexture("HUD_S"));
        Vector2i speedoSize = this.speedometer.getTexture().getSize();
        this.speedometer.setOrigin(speedoSize.x / 2, speedoSize.y / 2);
        float sScale = (float) ((this.window.getCamera().getSize().y / 1080) / 2);
        this.speedometer.setScale(sScale, sScale);

        this.hpFrame = new Sprite(Assets.getTexture("HUD_HP"));
        this.hpFrame.setOrigin((this.hpFrame.getLocalBounds().width / 2) + this.hpFrame.getLocalBounds().left,
                (this.hpFrame.getLocalBounds().height / 2) + this.hpFrame.getLocalBounds().top);
        float hScale = (float) ((this.window.getCamera().getSize().y / 1080) / 2);
        this.hpFrame.setScale(hScale, hScale);

        this.scrapCounter = new Sprite(Assets.getTexture("scrap_counter"));
        Vector2i scrapCSize = this.scrapCounter.getTexture().getSize();
        this.scrapCounter.setOrigin(scrapCSize.x/2,scrapCSize.y/2);
    }

    public void position() {
        float xRight = this.window.getCamera().getCenter().x + (this.window.getCamera().getSize().x / 2.2f);
        float y = (this.window.getCamera().getSize().y / 2.2f);
        this.hpFrame.setPosition(xRight, this.window.getCamera().getCenter().y - y);
        this.speedometer.setPosition(xRight, this.window.getCamera().getCenter().y + y);
    }

    public void draw() {
        if (!hideHud) {
            Vector2f resolution = this.window.getWindowSettings().getResolution();
            Vector2f padding = new Vector2f(25, 25);
            View hudView = new View(new Vector2f(-9000, -9000), resolution);
            Vector2f topLeft = Vector2f.sub(hudView.getCenter(), Vector2f.div(hudView.getSize(), 2));
            Vector2f topRight = new Vector2f(topLeft.x + resolution.x, topLeft.y);
            Vector2f bottomRight = Vector2f.add(hudView.getCenter(), Vector2f.div(hudView.getSize(), 2));
            //Scrap counter placement
            float counterScale = (float) 0.15;
            this.scrapCounter.setScale(new Vector2f(counterScale, counterScale));
            Vector2f scrapCSize = Vector2f.mul(new Vector2f(this.scrapCounter.getTexture().getSize()), counterScale);
            this.scrapCounter.setPosition(topLeft.x + (scrapCSize.x / 2 + padding.x), topLeft.y + (scrapCSize.y / 2 + padding.y));
            //Input scrap amount
            Font font = new Font();
            try {
                font.loadFromFile(Paths.get("Content/Assets/Fonts/Pixeled.ttf"));
            } catch (IOException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            }
            Text scrapAmount = new Text("" + Player.wallet.getScrap(), font);
            scrapAmount.setColor(Color.BLACK);
            float scrapAmountScale = (float) 0.7;
            scrapAmount.setScale(scrapAmountScale, scrapAmountScale);
            Vector2f scrapPosition = this.scrapCounter.getPosition();
            FloatRect scrapTextBounds = scrapAmount.getLocalBounds();
            scrapAmount.setOrigin(scrapTextBounds.width / 2, scrapTextBounds.height / 2);
            scrapAmount.setPosition(scrapPosition.x + 150 * counterScale, scrapPosition.y + 5);
            //Set hp frame scale and position
            float hpScale = (float) 0.2;
            this.hpFrame.setScale(new Vector2f(hpScale, hpScale));
            Vector2f hpSize = Vector2f.mul(new Vector2f(this.hpFrame.getTexture().getSize()), hpScale);
            this.hpFrame.setPosition(topRight.x - (hpSize.x / 2 + padding.x), topRight.y + (hpSize.y / 2 + padding.y));
            //Set speedometer scale and position
            float speedScale = (float) 0.2;
            this.speedometer.setScale(speedScale, speedScale);
            Vector2f speedSize = Vector2f.mul(new Vector2f(this.speedometer.getTexture().getSize()), speedScale);
            this.speedometer.setPosition(bottomRight.x - (speedSize.x / 2 + padding.x), bottomRight.y - (speedSize.y / 2 + padding.y));
            //Fill hp frame with health bar rectangle that is size dependent on the cars health.
            RectangleShape healthBar = new RectangleShape(new Vector2f(776 * hpScale, 192 * hpScale));
            healthBar.setFillColor(Color.GREEN);
            Vector2f offset = new Vector2f(486 * hpScale, 97 * hpScale);
            Vector2f hpCenter = this.hpFrame.getPosition();
            healthBar.setPosition(hpCenter.x - offset.x, hpCenter.y - offset.y);
            float healthPercent = Player.car.getHealthPercent();
            healthBar.setScale(healthPercent, 1);
            //Create speed needle required for displaying speed on speedometer
            RectangleShape speedNeedle = new RectangleShape(new Vector2f(192 * speedScale, 17 * speedScale));
            speedNeedle.setPosition(this.speedometer.getPosition());
            speedNeedle.setFillColor(Color.RED);
            Vector2f needleSize = speedNeedle.getSize();
            float speedPercent = Math.abs(Player.car.getSpeedPercent());
            float maxAngle = 225;
            speedNeedle.setOrigin(needleSize.x - needleSize.y / 2, needleSize.y / 2);
            Helper.setAngle(-maxAngle * speedPercent, speedNeedle);
            //Switch views, draw all elements on HUD and switch back to main view.
            ConstView original = window.getView();
            window.setView(hudView);
            //Draw Misc hud elements
            window.draw(this.speedometer);
            window.draw(speedNeedle);
            window.draw(this.hpFrame);
            window.draw(healthBar);
            window.draw(this.scrapCounter);
            window.draw(scrapAmount);
            //Draw animations on the hud
            Vector2f hudCenter = hudView.getCenter();
            Vector2f hudSize = hudView.getSize();
            Vector2f bottomLeft = new Vector2f(hudCenter.x - hudSize.x / 2, hudCenter.y + hudSize.y / 2);
            float scale = COOL_DOWN_SCALE; //For cool down animations
            Vector2f startPoint = new Vector2f(bottomLeft.x + (scale * this.COOL_DOWN_WIDTH) / 2, bottomLeft.y - (scale * this.COOL_DOWN_HEIGHT) / 2);
            int count = 0;

            for (Animation a : this.coolDownAnimations) {
                if (!a.isFinished()) {
                    a.setPosition(startPoint.x + count * scale * this.COOL_DOWN_WIDTH, startPoint.y);
                    count++;
                    a.play(window);
                }
            }

            window.setView(original);
            //Switch back to original view to draw everything as normal.
        }
    }



}
