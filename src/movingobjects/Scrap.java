
package movingobjects;

import helper.Player;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import handler.Assets;
import physics.PhysicsEngine;
import staticobjects.SolidEntity;

/**
 * Scrap dropped by enemies upon death. Adds to the players total scrap value.
 * @author DominicWild
 */
public class Scrap extends DecayingEntity{
    
    private final static int MAX_SPEED = 20;

    public Scrap(float direction, float speed, Texture scrapTex, Vector2f spawnPos) {
        super(direction, speed, new Sprite(scrapTex),15);
        this.object.setPosition(spawnPos);
        this.layer = -1;
    }
    
    @Override
    public void behave(){
        super.behave();
        this.moveForward();
        this.regressSpeed();
    }
    
    private void regressSpeed(){
        this.speed -= this.speed*0.1;
        if(this.speed < 0.1){
            this.speed = 0;
        }
    }
    
    @Override
    public void collide(SolidEntity entity){
        if(entity.getClass() == PlayerCar.class){
            Player.wallet.add((int) (Math.random()*10));
            PhysicsEngine.objectsDelete.add(this);
        } else if(entity.getClass() == SolidEntity.class){
            this.speed = -this.speed;
            this.moveForward();
            this.speed = 0;
        }
    }
    
    public static void placeScrap(Vector2f spawnLocation){
        float randSpeed = (float) (Math.random()*MAX_SPEED);
        float randDirection = (float) (Math.random()*360);
        String scrapTexString = "scrap_" + (int)(6*Math.random());
        Texture scrapTex = Assets.TEXTURE.get(scrapTexString);
        PhysicsEngine.objectsAdd.add(new Scrap(randDirection,randSpeed,scrapTex,spawnLocation));
    }

    
}
