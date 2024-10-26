package map;

import map.fields.Field;

import java.util.ArrayList;

/**
 * Represents a game map composed of fields.
 * The map maintains a list of fields and tracks the player's
 * current position on the map based on the outcome of a dice roll.
 * When the player reaches the end of the map, they will loop back
 * to the beginning of the map.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Map {
    private ArrayList<Field> map;
    private int currentFieldIndex;

    public Map(ArrayList<Field> map) {
        this.map = new ArrayList<>(map);
        this.currentFieldIndex = -1; // if someone rolls a 1 to start, they will begin at 0
    }

    /**
     * Moves the player to the next field based on the result of a dice roll.
     * This method updates the current field index by adding the dice roll value.
     * If the updated index exceeds the total number of fields on the map, it goes
     * around to the beginning of the map.
     *
     * @param diceRoll the result of the dice roll determining the number of fields to move
     * @return the next {@link Field} on the map after moving
     */
    public Field nextField(int diceRoll){
        currentFieldIndex += diceRoll;

        // if we reach the end of the map, we start over from the beginning
        if(currentFieldIndex >= map.size()) currentFieldIndex = currentFieldIndex % map.size();
        return map.get(currentFieldIndex);
    }
}
