/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticobjects;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2i;
import movingobjects.Car;
import window.MainWindow;

/**
 * A class representing an area that, when over, will do damage to any entity
 * that can suffer damage.
 *
 * @author DominicWild
 */
public class DamageArea extends SolidEntity{
    private double damage;              //Damage to be done per frame.
    
    public DamageArea(Transformable object, double damagePerSecond) {
        super(object);
        Sprite s = (Sprite)this.object;
        Vector2i size = s.getTexture().getSize();
        this.object.setOrigin(size.x/2,size.y/2);
        this.damage = damagePerSecond/MainWindow.FRAMERATE_TARGET;
        this.layer = 0;
    }
    
    public void collide(SolidEntity ent){
        if(ent instanceof Car){
            Car c = (Car)ent;
            c.inflictDamage(damage);
        }
    }
    
}
