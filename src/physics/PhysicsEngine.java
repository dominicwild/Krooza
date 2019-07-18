package physics;

import java.util.ArrayList;
import world.GameWorld;
import helper.Helper;
import movingobjects.Moveable;
import staticobjects.SolidEntity;

/**
 * Class responsible for updating the state of objects in the world. Handling
 * their collisions and movement values. It is also used to safely add and
 * remove objects from the world, while iterating over all objects currently
 * within the world.
 *
 * @author DominicWild
 */
public class PhysicsEngine {

    public static ArrayList<SolidEntity> objectsDelete;
    public static ArrayList<SolidEntity> objectsAdd;

    public PhysicsEngine() {
        this.objectsDelete = new ArrayList<>();
        this.objectsAdd = new ArrayList<>();
    }

    public void handlePhysics() {
        this.handleCollisions();
        this.addObjects();
        this.deleteObjects();
    }

    private void handleCollisions() {
        /**
         * Every object does its unique frame behaviour, then every object
         * compared for collisions where, if one is detected, that objects
         * specific collision behaviour is triggered.
         */
        for (SolidEntity currentObject : GameWorld.worldObjects) {
            currentObject.behave();
            if (currentObject instanceof Moveable && !objectsDelete.contains(currentObject)) {
                for (SolidEntity otherObject : GameWorld.worldObjects) {
                    Moveable actingObject = (Moveable) currentObject;
                    if (actingObject != otherObject && Helper.isColliding(currentObject, otherObject)) {
                        actingObject.collide(otherObject);
                    }
                }
            }
        }
    }
 
    private void deleteObjects() {
        if (!objectsDelete.isEmpty()) {
            for (SolidEntity e : objectsDelete) {
                GameWorld.worldObjects.remove(e);
            }
            objectsDelete = new ArrayList<>();
        }
    }

    private void addObjects() {
        if (!objectsAdd.isEmpty()) {
            for (SolidEntity e : objectsAdd) {
                GameWorld.worldObjects.add(e);
            }
            objectsAdd = new ArrayList<>();
        }
    }

}
