
package mods;


import org.jsfml.graphics.Sprite;
import org.jsfml.window.Keyboard;
import handler.Assets;
import movingobjects.Car;
import physics.PhysicsEngine;
import staticobjects.DamageArea;
import window.MainWindow;

/**
 * A spike deployer mod. Deploys spikes when the space bar is pressed.
 *
 * @author DominicWild
 */
public class SpikeDeployer extends BackMod{
    
    private DamageArea spikes;              //The spikes that are deployed.
    private boolean deployed;               //Determines if spikes are deployed or not.
    private Car moddedCar;                  //The car this mod is attached to.
    private int lastingFrames;              //How many frames the spikes last for.
    
    public SpikeDeployer(int tier, Car moddedCar) {
        super(tier,"spike_deployer_armed", 1000, "SpikeDeployerCoolDownAnimation");
        this.spikes = new DamageArea(new Sprite(Assets.TEXTURE.get("spikes")), 2 + 0.5*tier);
        this.deployed = false;
        this.moddedCar = moddedCar;
        this.lastingFrames = 0;
    }
    
    @Override
    public void behave(){
        if(MainWindow.eventHandler.isKeyDown(Keyboard.Key.SPACE) && this.coolDownAnimation.isFinished()){
            this.openDeployer();
            this.deploySpikes();
        } else if(this.coolDownAnimation.isFinished()){
            this.closeDeployer();
        }
        if(this.lastingFrames <= 0){
            PhysicsEngine.objectsDelete.add(this.spikes);
        } else {
            this.lastingFrames--;
        }
    }
    
    public void deploySpikes(){
        spikes.getObject().setPosition(moddedCar.getObject().getPosition());
        PhysicsEngine.objectsAdd.add(spikes);
        this.coolDownAnimation.replay();
        this.lastingFrames = (int) (MainWindow.FRAMERATE_TARGET*5);
    }

    /**
     * Updates the mod image to display the opened SpikeDeployer.
     */
    public void openDeployer() {
        if (!this.deployed) {
            this.setTexture(Assets.TEXTURE.get("spike_deployer_unarmed"));
            this.deployed = true;
        }
    }
    
    /**
     * Updates the mod image to display the closed SpikeDeployer.
     */
    public void closeDeployer() {
        if (this.deployed) {
            this.setTexture(Assets.TEXTURE.get("spike_deployer_armed"));
            this.deployed = false;
        }
    }

}
