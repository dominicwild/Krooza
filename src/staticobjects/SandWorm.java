package staticobjects;

import helper.Animation;
import helper.Helper;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;


import handler.Assets;
import movingobjects.EnemyTurret;
import org.jsfml.graphics.Texture;
import physics.PhysicsEngine;
import window.MainWindow;
import world.OverWorld;

/**
 * A sand worm that acts as a stationary turret in the over-world.
 *
 * @author DominicWild
 */
public class SandWorm extends SolidEntity {

    private EnemyTurret head;
    private boolean appeared;
    private int SPAWN_DISTANCE = 500;
    private Animation wormAppearance;
    private int health;
    private Texture damagedTex;
    private Texture regularTex;
    private final int DAMAGE_FRAMES = (int) (MainWindow.FRAMERATE_TARGET*0.1);
    private int damagedFrames;

    public SandWorm(SolidEntity target, Vector2f spawnLocation) {
        super(new Sprite(Assets.TEXTURE.get("worm_base")));
        this.head = new EnemyTurret("worm_top", "worm_bolt", this, target);
        this.appeared = false;
        this.head.setPosition(spawnLocation);
        this.object.setPosition(spawnLocation);
        this.regularTex = (Texture) this.head.getTexture();
        this.damagedTex = Assets.getTexture("worm_top_red");
        this.layer = 0;
        this.health = 50;
        this.wormAppearance = new Animation(200,200,3,9,100,"worm_animation");
        this.wormAppearance.setPosition(spawnLocation);
    }

    public void behave() {
        if (!appeared) {
            if (this.wormAppearance.isFinished()) {
                OverWorld.worldAnimations.remove(this.wormAppearance);
                this.appeared = true;
            } else if (Helper.getDistanceToPoint(head.getPosition(), head.getTarget().getPosition()) < SPAWN_DISTANCE) {
                OverWorld.worldAnimations.add(this.wormAppearance);
            }
        } else {
            if (!this.isRegularTex() && this.damagedFrames <= 0) {
                this.head.setTexture(this.regularTex);
            } else {
                this.damagedFrames--;
            }
            this.head.behave();
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        if (appeared) {
            target.draw((Drawable)this.object);
            target.draw(this.head);
        }
    }
    
    public void inflictDamage(int damage){
        this.health -= damage;
        if(this.health <= 0){
            PhysicsEngine.objectsDelete.add(this);
            PhysicsEngine.objectsAdd.add(new WormCorpse(this.getPosition()));
        }
        this.damagedFrames = this.DAMAGE_FRAMES;
        this.head.setTexture(damagedTex);
        this.head.setAngle(this.head.getDirection());
    }
    
    private boolean isRegularTex(){
        return this.head.getTexture().equals(this.regularTex);
    }

}
