package mods;

/**
 * Represents a default setting for a mod. Primarily used to avoid null pointer
 * exceptions with mod slots that are empty on any given car.
 *
 * @author DominicWild
 */
public enum ModState {

    EMPTY, OCCUPIED;
}
