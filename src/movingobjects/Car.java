package movingobjects;

import helper.Animation;
import java.util.ArrayList;

import helper.Helper;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import mods.BackMod;
import mods.FrontMod;
import mods.TurretMod;
import mods.WheelMod;
import mods.ArmourMod;
import mods.BaseMod;
import mods.Bumper;
import mods.Engine;
import mods.ExhaustMod;
import mods.Skin;
import staticobjects.SolidEntity;
import world.GameWorld;
import world.Tile;
import world.TileType;

/**
 * A basic player car class with tank controls. A and D change direction, W and
 * S move forward and back respectively.
 *
 * @author DominicWild
 */
public class Car extends Moveable {
    
    protected float acceleration;         //The rate of change of speed per frame.
    protected float baseAcceleration;       //The base acceleration for this car.
    protected float handling;               //The rate of change of angle per frame.
    protected float maxHandling;          //The max handling for this car.
    protected float friction;             //The rate at which speed decreases.
    protected float maxSpeed;             //The max possible value speed could become.
    protected float baseMaxSpeed;
    protected double maxHealth;             //The max health of the car.
    protected double health;                 //The health of the car until it dies.
    protected boolean modsApplied;
    protected Skin carBody;      //Shape representing how the car looks.
    protected boolean applyFriction;
    protected RectangleShape[] hitBox;
    protected Vector2f[] chasePoints;           //Points used for AI chasing
    protected final float CHASE_DISTANCE = 6;   //Distance the case points are from the car.
    protected Tile tileOn;                      //The tile this car is on.
    //Mods
    protected BackMod backMod;
    protected FrontMod frontMod;
    protected Engine engine;
    protected ExhaustMod exhaust;
    protected TurretMod turret;
    protected WheelMod wheels;
    protected ArmourMod armour;
    protected ArrayList<BaseMod> mods;

    /**
     * Creates a basic car of specified width and height, mainly for testing
     * purposes.
     *
     * @param carBody Shape representing how the car looks.
     */
    public Car(Skin carBody) {
        super(0, 0, carBody);
        this.carBody = carBody;
        Vector2i size = this.carBody.getTexture().getSize();
        this.carBody.setOrigin(size.x / 2, size.y / 2);
        this.acceleration = (float) 0.41;
        this.baseAcceleration = this.acceleration;
        this.handling = (float) 0;
        this.maxHandling = 4;
        this.friction = (float) 0.1;
        this.maxSpeed = (float) 30;
        this.baseMaxSpeed = this.maxSpeed;
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.modsApplied = false;
        this.mods = new ArrayList<>();
        this.mods.add(this.carBody);
        this.applyFriction = false;
        this.tileOn = new Tile(0,0);
        this.tileOn.setType(TileType.NULL);
        this.chasePoints = new Vector2f[]{new Vector2f(0, -size.y * CHASE_DISTANCE), new Vector2f(0, size.y * CHASE_DISTANCE)};
        int padding = 35; //Since JSFML float rects aren't very accurate....
        RectangleShape hitBox1 = new RectangleShape(new Vector2f(size.x / 2 + padding, size.y + padding));
        RectangleShape hitBox2 = new RectangleShape(new Vector2f(size.x / 2 + padding, size.y + padding));
        this.hitBox = new RectangleShape[]{hitBox1, hitBox2};
        Vector2f sizeBox1 = hitBox[0].getSize();
        Vector2f sizeBox2 = hitBox[1].getSize();
        this.hitBox[0].setOrigin(sizeBox1.x, sizeBox1.y / 2);
        this.hitBox[1].setOrigin(0, sizeBox2.y / 2);
        this.hitBox[0].setFillColor(Color.RED);
        this.hitBox[1].setFillColor(Color.GREEN);
    }

    public void collide(SolidEntity entity) {
        if (entity instanceof Car) {
            this.triggerModEffects((Car) entity);
            this.carCollide((Car) entity);
        } else if (entity.getClass() == SolidEntity.class) {
            this.solidEntityCollide();
        }
    }

    private void solidEntityCollide() {
        //Reverse into previous frame state
        this.speed = -this.speed;
        this.moveForward();
        this.speed = -this.speed;
        this.speed = (float) (-this.speed * 0.3 - Math.signum(this.speed)); //Invert speed direction and reduce speed by 70%
    }

    private void triggerModEffects(Car other) {
        if (this.frontMod.getClass() == Bumper.class && Helper.collideFront(this, other)) {
            Bumper b = (Bumper) this.frontMod;
            b.collide(other);
        }
        if (other.getFrontMod().getClass() == Bumper.class && Helper.collideFront(other, this)) {
            Bumper b = (Bumper) other.getFrontMod();
            b.collide(this);
        }
    }

    private void carCollide(Car other) {

        this.speed = -this.speed;
        other.setSpeed(-other.getSpeed());
        this.moveForward();
        other.moveForward();
        this.speed = -this.speed;
        other.setSpeed(-other.getSpeed());

        float difference = Math.abs(this.speed - other.getSpeed());
        double massPercent = this.mass / (this.mass + other.getMass());
        double massPercentOther = 1 - massPercent;
        float newSpeed = (float) (difference * (1-massPercent)) + 2;
        float newSpeedOther = (float) (difference * (1-massPercentOther)) + 3;

        FloatRect[] hitBox = this.getHitBox();
        FloatRect[] hitBoxOther = other.getHitBox();
        boolean frontInterFront = Helper.intersects(hitBox[1], hitBoxOther[1]);
        boolean frontInterBack = Helper.intersects(hitBox[1], hitBoxOther[0]);
        boolean backInterBack = Helper.intersects(hitBox[0], hitBoxOther[0]);
        boolean backInterFront = Helper.intersects(hitBox[0], hitBoxOther[1]);
        int sign = 0;
        int signOther = 0;

        if (backInterBack && backInterFront) {
            //Intersection of back hit box, on both hit boxes.
            sign = 1;
            FloatRect intersectionBack = hitBox[0].intersection(hitBoxOther[0]);
            FloatRect intersectionFront = hitBox[0].intersection(hitBoxOther[1]);
            double areaBack = intersectionBack.height * intersectionBack.width;
            double areaFront = intersectionFront.height * intersectionBack.width;
            if (areaBack > areaFront) { //Treat as back collison on colliding body.
                signOther = 1;
            } else if (areaBack < areaFront) {//Treat as front collison on colliding body.
                signOther = -1;
            } else { //If equal //Treat as front collison on colliding body.
                signOther = -1;
            }
        } else if (frontInterFront && frontInterBack) {
            //Intersection of front hit box, on both hit boxes.
            sign = -1;
            FloatRect intersectionBack = hitBox[1].intersection(hitBoxOther[0]);
            FloatRect intersectionFront = hitBox[1].intersection(hitBoxOther[1]);
            double areaBack = intersectionBack.height * intersectionBack.width;
            double areaFront = intersectionFront.height * intersectionBack.width;
            if (areaBack > areaFront) { //Treat as back collison on colliding body.
                signOther = 1;
            } else if (areaBack < areaFront) {//Treat as front collison on colliding body.
                signOther = -1;
            } else { //If equal //Treat as front collison on colliding body.
                signOther = -1;
            }
        } else if (frontInterFront) {
            sign = -1;
            signOther = -1;
        } else if (frontInterBack) {
            sign = -1;
            signOther = 1;
        } else if (backInterBack) {
            sign = 1;
            signOther = 1;
        } else if (backInterFront) {
            sign = 1;
            signOther = -1;
        }

        this.setSpeed(newSpeed * sign);
        other.setSpeed(newSpeedOther * signOther);

        if (this.speed != 0) {
            while (Helper.isColliding(this, other)) {
                this.moveForward();
                other.moveForward();
            }
        }
    }

    /**
     * Makes the car react on current frame.
     */
    @Override
    public void behave() {
        this.tileBehaviour();
        for (BaseMod m : mods) {
            if (m != null) {
                m.behave();
            }
        }
        this.applyFriction();
        updateHandling();
        this.moveForward();
    }
    
    protected void tileBehaviour() {
        Tile tileOn = GameWorld.tileEngine.getTileAt(this.getPosition());
        if (this.tileOn.getType() != tileOn.getType()) { //If not on same tile type as last frame
            this.tileOn = tileOn;
            this.speed *= TileType.getFriction(this.tileOn.getType());
        }
        if (tileOn.getTextureString().equals("TILE_W_0")) {
            this.destroyCar();
        } else {
            this.acceleration = TileType.getFriction(tileOn.getType()) * this.baseAcceleration;
        }

    }

    @Override
    public void moveForward() {
        Vector2f directionVector = Helper.calculateDirectionVector(-direction, speed);
        for (BaseMod m : mods) {
            m.move(directionVector);
        }
        for (RectangleShape r : hitBox) {
            r.move(directionVector);
        }

        this.chasePoints[0] = Vector2f.add(this.chasePoints[0], directionVector);
        this.chasePoints[1] = Vector2f.add(this.chasePoints[1], directionVector);
    }

    @Override
    public void setDirection(float direction) {
        if (!Float.isNaN(direction)) {
            this.direction = direction;
            Helper.setAngle(direction, object);
            for (BaseMod m : mods) {
                if (m != null && m.getClass() != TurretMod.class) {
                    Helper.setAngle(direction, m);
                }
            }
            for (RectangleShape r : hitBox) {
                Helper.setAngle(direction, r);
            }
        }
    }

    protected void rotate(float amount) {
        carBody.rotate(amount);
        for (BaseMod m : mods) {
            if (m.getClass() != TurretMod.class) {
                m.rotate(amount);
            }
        }
    }

    /**
     * Applies all mod statistics changes to this car.
     */
    public void applyMods() {
        if (!modsApplied) {
            for (BaseMod m : mods) {
                if (!(m instanceof ExhaustMod)) {
                    m.apply(this);
                }
            }
            this.modsApplied = true;
        } else {
            throw new IllegalStateException("Attempting to apply applied mods.");
        }
        this.orientateAllMods();
    }
    
    public void orientateAllMods(){
        for(BaseMod m : this.mods){
            Helper.setAngle(this.direction,m);
        }
    }

    /**
     * Removes all mod statistics changes to this car.
     */
    public void unapplyMods() {
        if (modsApplied) {
            for (BaseMod m : mods) {
                if (m != null && m.getClass() != ExhaustMod.class) {
                    m.reverse(this);
                }
            }
            this.modsApplied = false;
        } else {
            throw new IllegalStateException("Attempting to unapply unapplied mods.");
        }
    }

    /**
     * Increments speed given not exceeding max speed.
     *
     * @param inc The amount to increment the speed by.
     */
    protected void incrementSpeed(float inc) {
        float maxSpeed = this.maxSpeed;
        if(Math.signum(this.speed) == -1 ){
            maxSpeed *= 0.5;
        }
        if (Math.abs(this.speed + inc) <= maxSpeed) {
            this.speed += inc;
        } else if (Math.abs(this.speed) <= maxSpeed) {
            this.speed = Math.signum(this.speed) * maxSpeed;
        } else {
            this.applyFriction = true;
        }
    }

    protected void updateHandling() {
        float speed = Math.abs(this.speed);
        if (speed / this.baseMaxSpeed < 1) {
            this.handling = (float) Math.pow(speed / this.baseMaxSpeed, 1.0) * this.maxHandling;
        } else {
            this.handling = this.maxHandling;
        }
    }

    /**
     * Apply Friction to the car to slow it down.
     */
    public void applyFriction() {
        float absoluteSpeed = Math.abs(this.speed);
        if (absoluteSpeed > 0.1) {
            float sign = Math.signum(this.speed) * -1;
            if (absoluteSpeed > this.maxSpeed) {
                float difference = absoluteSpeed - this.maxSpeed;
                this.speed = this.speed + (sign * (difference / 30 + this.friction));
            } else {
                //We only want speed to decrease to 0 and not beyond, to start moving car backwards.
                if (Math.signum(this.speed) == Math.signum(this.speed + (friction * sign))) {
                    this.speed = this.speed + (friction * sign);
                } else {
                    this.speed = 0;
                    this.handling = 0;
                }
            }
        } else {
            this.speed = 0;
            this.handling = 0;
        }
    }

    /**
     * Sets the acceleration for this car.
     *
     * @param acceleration A strictly greater than 0 acceleration.
     */
    public void setAcceleration(float acceleration) {
        if (acceleration > 0) {
            this.acceleration = acceleration;
            this.baseAcceleration = this.acceleration;
        } else {
            throw new IllegalArgumentException("Invalid Car accleration. Must be strictly greater than 0.");
        }
    }

    /**
     * Sets the handling for this car.
     *
     * @param handling The strictly greater than 0 value for the rate at which
     * this car changes direction.
     */
    public void setHandling(float handling) {
        if (handling > 0) {
            this.handling = handling;
        } else {
            throw new IllegalArgumentException("Invalid Car handling. Must be strictly greater than 0.");
        }
    }

    /**
     * Sets the friction this car experiences.
     *
     * @param friction The strictly greater than 0 value for the rate at which
     * this car slows down.
     */
    public void setFriction(float friction) {
        if (friction > 0) {
            this.friction = friction;
        } else {
            throw new IllegalArgumentException("Invalid Car friction. Must be strictly greater than 0.");
        }
    }

    /**
     * Sets the maximum possible speed this car can go.
     *
     * @param maxSpeed A strictly greater than 0 value representing the cars max
     * speed.
     */
    public void setMaxSpeed(float maxSpeed) {
        if (maxSpeed > 0) {
            this.maxSpeed = maxSpeed;
        } else {
            throw new IllegalArgumentException("Invalid Car max speed. Must be strictly greater than 0.");
        }
    }

    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Sets main attributes of a Car.
     *
     * @param acceleration A strictly greater than 0 acceleration.
     * @param friction The strictly greater than 0 value for the rate at which
     * this car slows down.
     * @param maxSpeed A strictly greater than 0 value representing the cars max
     * speed.
     * @param handling The strictly greater than 0 value for the rate at which
     * this car changes direction.
     */
    public void setCarAttributes(float acceleration, float friction, float maxSpeed, float handling) {
        this.setAcceleration(acceleration);
        this.setFriction(friction);
        this.setMaxSpeed(maxSpeed);
        this.setHandling(handling);
    }

    public void draw(RenderTarget target, RenderStates states) {
        //Must draw car base, then mods ontop to overlay car.
        target.draw((Drawable) this.object);
//        target.draw(this.carBody);
        target.draw(this.armour);
        target.draw(this.exhaust);
        target.draw(this.backMod);
        target.draw(this.wheels);
        target.draw(this.frontMod);
        target.draw(this.engine);
        target.draw(this.turret);
//        for(RectangleShape r : hitBox){
//            target.draw(r);
//        }
    }

    public void setPosition(Vector2f point){
        this.setPosition(point.x,point.y);
    }
    
    public void setPosition(float x, float y) {
        this.carBody.setPosition(x, y);
        for (BaseMod m : mods) {
            m.setPosition(x, y);
        }
        for (RectangleShape r : hitBox) {
            r.setPosition(x, y);
        }
        Sprite s = (Sprite) this.getObject();
        Vector2i size = s.getTexture().getSize();
        this.chasePoints = new Vector2f[]{new Vector2f(x, y - size.y * CHASE_DISTANCE), new Vector2f(x, y + size.y * CHASE_DISTANCE)};
    }

    public float getAcceleration() {
        return this.baseAcceleration;
    }

    public float getHandling() {
        return handling;
    }

    public float getMaxHandling() {
        return maxHandling;
    }

    public float getFriction() {
        return friction;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public double getHealth() {
        return health;
    }

    public BackMod getBackMod() {
        return backMod;
    }

    public void setBackMod(BackMod backMod) {
        if (!this.modsApplied) {
            if (backMod != null) {
                this.mods.remove(this.backMod);
                this.backMod = backMod;
                this.mods.add(this.backMod);
                this.backMod.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public FrontMod getFrontMod() {
        return frontMod;
    }

    public void setFrontMod(FrontMod frontMod) {
        if (!this.modsApplied) {
            if (frontMod != null) {
                this.mods.remove(this.frontMod);
                this.frontMod = frontMod;
                this.mods.add(this.frontMod);
                this.frontMod.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        if (!this.modsApplied) {
            if (engine != null) {
                this.mods.remove(this.engine);
                this.engine = engine;
                this.mods.add(this.engine);
                this.engine.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public ExhaustMod getExhaust() {
        return exhaust;
    }

    public void setExhaust(ExhaustMod exhaust) {
        if (!this.modsApplied) {
            if (exhaust != null) {
                this.mods.remove(this.exhaust);
                this.exhaust = exhaust;
                this.mods.add(this.exhaust);
                this.exhaust.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public TurretMod getTurret() {
        return turret;
    }

    public void setTurret(TurretMod turret) {
        if (!this.modsApplied) {
            if (turret != null) {
                this.mods.remove(this.turret);
                this.turret = turret;
                this.mods.add(this.turret);
                this.turret.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public WheelMod getWheels() {
        return wheels;
    }

    public void setWheels(WheelMod wheels) {
        if (!this.modsApplied) {
            if (wheels != null) {
                this.mods.remove(this.wheels);
                this.wheels = wheels;
                this.mods.add(this.wheels);
                this.wheels.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public ArmourMod getArmour() {
        return armour;
    }

    public void setArmour(ArmourMod armour) {
        if (!this.modsApplied) {
            if (armour != null) {
                this.mods.remove(this.armour);
                this.armour = armour;
                this.mods.add(this.armour);
                this.armour.setPosition(this.carBody.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public void setSkin(Skin skin) {
        if (!this.modsApplied) {
            if (carBody != null) {
                this.mods.remove(this.carBody);
                this.carBody = skin;
                this.object = skin;
                this.mods.add(this.carBody);
                this.carBody.setPosition(this.frontMod.getPosition());
            }
        } else {
            throw new IllegalStateException("Attempting to remove applied mods.");
        }
    }

    public Skin getSkin() {
        return carBody;
    }

    public void setMods(FrontMod front, BackMod back, ArmourMod armour, Engine engine, ExhaustMod exhaust, TurretMod turret, WheelMod wheels, Skin skin) {
        this.setFrontMod(front);
        this.setBackMod(back);
        this.setArmour(armour);
        this.setEngine(engine);
        this.setExhaust(exhaust);
        this.setTurret(turret);
        this.setWheels(wheels);
        if (skin != null) {
            this.setSkin(skin);
        }
    }

    public FloatRect[] getHitBox() {
        return new FloatRect[]{hitBox[0].getGlobalBounds(), hitBox[1].getGlobalBounds()};
    }

    public void inflictDamage(double amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.destroyCar();
        } else if (amount > 0){
            this.carBody.apply(this);
        }
    }

    public void destroyCar() {
       this.setPosition(GameWorld.tileEngine.getMapSize().x/4, GameWorld.tileEngine.getMapSize().y/4);
       Animation death = Car.getCarDeathAnimation();
       death.setPosition(this.getPosition());
       death.setAngle(direction);
       GameWorld.worldAnimations.add(death);
    }

    public Vector2f[] getChasePoints() {
        return chasePoints;
    }

    public Vector2i getSize() {
        Sprite s = (Sprite) this.object;
        return s.getTexture().getSize();
    }

    public Vector2f getPosition() {
        return this.object.getPosition();
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public float getHealthPercent() {
        float healthPercent = (float) (this.health / this.maxHealth);
        if (healthPercent >= 0) {
            return healthPercent;
        } else {
            return 0;
        }
    }

    public float getSpeedPercent() {
        float speedPercent = (float) Math.abs(this.speed / this.maxSpeed);
        if (speedPercent > 1) {
            return 1;
        } else if (speedPercent >= 0) {
            return speedPercent;
        } else {
            return 0;
        }
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setToMaxHP() {
        this.health = this.maxHealth;
    }

    public void setMaxHandling(float maxHandling) {
        this.maxHandling = maxHandling;
    }
    
    public static Animation getCarDeathAnimation(){
        return new Animation(140,75,13,13,100, "car_death");
    }
    
}
