package map.fields;

/**
 * Represents a "luck" field on the game map.
 * A luck field can only provide a player with a positive effect.
 * The value associated with the luck field indicates
 * the amount that will be added to a player's balance.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Luck extends Field{
    private final int value;

    public Luck(int value) {
        this.value = value;
    }
    @Override
    public String getType(){
        return "luck";
    }
    @Override
    public int getValue(){return value;}
}
