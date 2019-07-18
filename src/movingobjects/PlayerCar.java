
package movingobjects;

import helper.Player;
import mods.ExhaustMod;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;

import mods.BaseMod;
import mods.Skin;
import org.jsfml.window.Mouse;
import physics.PhysicsEngine;
import staticobjects.SolidEntity;
import staticobjects.TriggerArea;
import window.MainWindow;

/**
 * The car that is controlled by the player.
 * @author DominicWild
 */
public class PlayerCar extends Car{

    private boolean boosting;
    private Clock boostTimer;
    
    public PlayerCar(Skin carBody) {
        super(carBody);
        this.boosting = false;
        this.boostTimer = new Clock();
    }

    public void behave() {
        this.tileBehaviour();
        this.inputReact();
        for(BaseMod m : mods){
            m.behave();
        }
        this.applyFriction();
        if(boosting && this.speed < this.exhaust.getAddedSpeed()){
            this.speed = this.speed + this.exhaust.getAddedSpeed();
        }
        updateHandling();
        this.moveForward();
        if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            this.turret.fire(Math.abs(this.speed) + 33,this);
        }
    }

    private void inputReact() {
        this.applyFriction = true;
        if (Keyboard.isKeyPressed(Keyboard.Key.D) && this.speed != 0) {
            float angle = (direction - this.handling)%360;
            if(angle <= 0){
                angle = angle + 359;
            }
            this.setDirection(angle);
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.A) && this.speed != 0) {
            this.setDirection(Math.abs((direction + this.handling)%360));
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            this.applyFriction = false;
            incrementSpeed(acceleration);
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            this.applyFriction = false;
            incrementSpeed(-acceleration);
        }
        if (!this.exhaust.equals(ExhaustMod.emptyMod())) {
            if(!boosting && MainWindow.eventHandler.isKeyDown(Keyboard.Key.LSHIFT) && !this.exhaust.onCoolDown()){
                this.applyBoost();
            } else if(boosting && this.boostTimer.getElapsedTime().asMilliseconds() >= this.exhaust.getExhaustDuration()){
                this.deactivateBoost();
            }
        }
    }
    
    public void applyBoost(){
        this.boosting = true;
        this.exhaust.apply(this);
        this.boostTimer.restart();
    }
    
    public void addModsToInventory(){
        for(BaseMod m : mods){
            Player.inventory.add(m);
        }
        Player.inventory.add(this.carBody);
    }
    
    public void deactivateBoost(){
        this.boosting = false;
        this.exhaust.reverse(this);
        this.exhaust.activateCoolDown();
    }
    
    public void applyMods(){
        super.applyMods();
    }

    @Override
    public void collide(SolidEntity entity) {
        super.collide(entity); 
        if(entity instanceof TriggerArea){
            TriggerArea area = (TriggerArea)entity;
            area.triggerEffect();
        }
    }

    @Override
    public void destroyCar() {
        PhysicsEngine.objectsDelete.add(this);
    }

    public void reset(){
        this.speed = 0;
    }
    
}
