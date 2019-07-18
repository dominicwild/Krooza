package helper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;
import movingobjects.Car;
import staticobjects.SolidEntity;

/**
 * A collection of functions that have found to be useful in numerous classes.
 * Often relating to general world logic.
 *
 * @author DominicWild
 */
public class Helper {

    public static void setAngle(float angle, Transformable s) {
        if (!Float.isNaN(angle)) {
            float difference = -angle + Math.abs(s.getRotation());
            s.rotate(difference);
        }
    }

    public static Vector2f calculateDirectionVector(float direction, float speed) {
        double angle = Math.toRadians(direction);
        //Find changes in opposite and adjacent according to trigonometry.
        float opposite = (float) (speed * Math.sin(angle));
        float adjacent = (float) (speed * Math.cos(angle));
        //Determine which change correlates to which coordinate axis, x or y.
        if (angle <= 90 || angle >= 270) {
            return new Vector2f(adjacent, opposite);
        } else {
            return new Vector2f(opposite, adjacent);
        }
    }

    /**
     * helper.Helper function to get a texture from a file path only.
     *
     * @param path Path to the file.
     * @return A texture from the specified path.
     * @throws IOException If the path does not lead to a valid picture.
     */
    public static Texture texFromFile(String path) {
        Texture t = new Texture();
        try {
            t.loadFromFile(Paths.get(path));
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, "Cannot load file: " + path + " to load.", ex);
        }
        return t;
    }

    public static boolean isColliding(SolidEntity s1, SolidEntity s2) {
        return intersects(s1.getObjectBounds(), s2.getObjectBounds());
    }

    public static boolean intersects(FloatRect r1, FloatRect r2) {
        return r1.intersection(r2) != null;
    }

    /**
     * Finds the relative direction point 2 is from point 1.
     *
     * . <---Point 2 / <--- Angle .------- <--- Point 1
     * @par
     *
     * am point1 The point to find relative to point 2.
     * @param point2 The point in which to find the direction
     * @return The direction in degrees from point 1.
     */
    public static float determineDirection(Vector2f point1, Vector2f point2) {
        float x = point2.x - point1.x;
        float y = point2.y - point1.y;
        int quadrant;

        if (x > 0) { //If x is positive
            if (y < 0) {//If y is negative
                quadrant = 0;
            } else {
                quadrant = 3;
            }
        } else {
            if (y < 0) {
                quadrant = 1;
            } else {
                quadrant = 2;
            }
        }

        float angle = 0;

        if (quadrant == 0 || quadrant == 2) {
            angle = (float) Math.atan(y / x);
        } else if (quadrant == 1 || quadrant == 3) {
            angle = (float) Math.atan(x / y);
        }

        angle = (float) Math.toDegrees(angle);
        return Math.abs(angle) + 90 * quadrant;
    }

    public static float boundDegrees(float degree) {
        if (degree > 0) { //If positive
            return degree % 360;
        } else {
            return 360 - (Math.abs(degree) % 360);
        }
    }

    public static float getMean(float[] data) {
        float sum = 0;
        for (float num : data) {
            sum += num;
        }
        return sum / data.length;
    }

    public static boolean collideFront(Car toTest, Car other) {
        FloatRect[] frontHitBox = toTest.getHitBox();
        FloatRect[] otherHitBox = other.getHitBox();
        
        return Helper.intersects(frontHitBox[1], otherHitBox[0])
                != Helper.intersects(frontHitBox[1], otherHitBox[1]);
    }

    /**
     * Finds most optimum direction to turn an angle to face the players
     * direction.
     *
     * @return The direction to turn to reach the target direction.
     */
    public static float findBestDirection(float targetDirection, float direction) {
        float difference = targetDirection - direction;
        float sign = Math.signum(difference); //Signals direction to turn
        if (Math.abs(difference) > 180) { //If greater than 180 distance,
            sign *= -1; //quicker to go other direction.
        }
        return sign;
    }

    public static double getDistanceToPoint(Vector2f fromPoint, Vector2f toPoint) {
        Vector2f delta = Vector2f.sub(fromPoint, toPoint);
        return Math.hypot(delta.x, delta.y);
    }

}
