package world;

import java.util.HashMap;

/**
 * An enum that represents a tile type. Each tile type has a associated friction
 * value that is used when determining how easy it is for the car to move
 * through a specific tile.
 *
 * @author Dominic Wild
 */
public enum TileType {

    SAND(0.9), WATER(0.5), TAR(0.25), ROAD(1), NULL(0);
    private float frictionValue;
    private static final HashMap<TileType, Float> frictionTable = new HashMap<>();

    static {
        for (TileType t : TileType.values()) {
            frictionTable.put(t, t.frictionValue);
        }
    }

    private TileType(double frictionValue) {
        this.frictionValue = (float) frictionValue;
    }

    public static float getFriction(TileType t) {
        return frictionTable.get(t);
    }
}
