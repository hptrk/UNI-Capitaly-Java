package map.fields;

/**
 * Represents a "service" field on the game map.
 * A service field requires the player to pay a certain amount when landed on.
 * The value associated with the service field indicates the cost that must be
 * paid by the player.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Service extends Field{
    private final int value;
    public Service(int value){
        this.value = value;
    }

    @Override
    public String getType(){
        return "service";
    }
    @Override
    public int getValue(){ return this.value; }
}
