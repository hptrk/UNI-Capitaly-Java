package map.fields;

/**
 * Represents a generic field on the map in the game.
 * This class serves as the base class for different types of fields,
 * such as properties, services, and luck fields. It defines the
 * essential methods that any field type must implement.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public abstract class Field {
    public abstract String getType();
    public abstract int getValue();
}
