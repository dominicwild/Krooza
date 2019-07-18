
package movingobjects;

import mods.TurretMod;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import staticobjects.Chest;
import staticobjects.SolidEntity;
import staticobjects.WorldArea;

/**
 * The on-foot representation of the player.
 * @author DominicWild
 */
public class PlayerCharacter extends Person {

    private TurretMod character;
    private boolean movedUp;
    private boolean movedDown;
    private boolean movedLeft;
    private boolean movedRight;
    
    

    public PlayerCharacter(float direction, float speed, TurretMod object) {
        super(direction, speed, object);
        this.character = (TurretMod) this.object;
    }

    @Override
    public void moveForward() {
        super.moveForward();
    }

    @Override
    public void behave() {
        this.moveInput();
        this.character.behave();
        if(Mouse.isButtonPressed(Mouse.Button.LEFT)){
            this.character.fire(33, this);
        }
    }
    
    private void resetMoveDetection(){
        this.movedDown = false;
        this.movedUp = false;
        this.movedLeft = false;
        this.movedRight = false;
    }
    
    public void moveInput() {
        this.resetMoveDetection();
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            object.move(0, -this.speed);
            this.movedUp = true;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            object.move(-this.speed, 0);
            this.movedLeft = true;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            object.move(0, this.speed);
            this.movedDown = true;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            object.move(this.speed, 0);
            this.movedRight = true;
        }
    }

    @Override
    public void collide(SolidEntity entity) {
        if(entity.getClass() == SolidEntity.class){
            this.solidEntityCollide();
        } else if(entity.getClass() == WorldArea.class){
            this.worldAreaCollide((WorldArea) entity);
        } else if(entity.getClass() == Chest.class){
            this.chestCollide((Chest)entity);
        }
    }
    
    private void chestCollide(Chest chest){
        chest.lootChest();
    }
    
    private void worldAreaCollide(WorldArea area){
        area.triggerEffect();
    }
    
    /**
     * Upon collision, simply move back to previous position.
     */
    private void solidEntityCollide(){
        this.reverseMove();
    }
    
    private void reverseMove(){
        this.speed *= -1;
        if (this.movedUp) {
            object.move(0, -this.speed);
        }
        if (this.movedLeft) {
            object.move(-this.speed, 0);
        }
        if (this.movedDown) {
            object.move(0, this.speed);
        }
        if (this.movedRight) {
            object.move(this.speed, 0);
        }
        this.speed *= -1;
    }
    
    
    
    
}
