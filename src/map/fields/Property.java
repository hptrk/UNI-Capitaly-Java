package map.fields;

import players.Player;

/**
 * Represents a property field on the game map.
 * A property can be owned by a player, and may also have a house built on it.
 * Players can buy properties and houses, and the player who steps on another
 * player's property has to pay based on whether the property has a house or not.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Property extends Field {
    public final int PROPERTY_PRICE = 1000;
    public final int HOUSE_PRICE = 4000;

    private boolean hasHouse;
    private boolean hasOwner;
    private Player owner;

    public Property(){
        this.hasHouse = false;
        this.hasOwner = false;
    }

    public boolean getHasHouse(){ return hasHouse;}
    public Player getOwner(){ return owner;}

    public void buyProperty(Player player){hasOwner = true; owner = player;}
    public void buyHouse(Player player){ hasHouse = owner.equals(player); }

    public void resetProperty(){hasHouse = false; hasOwner = false; owner = null;}

    @Override
    public String getType(){
        return "property";
    }
    @Override
    public int getValue(){
        return hasOwner ? (hasHouse ? 2000 : 500) : 0;
    }

}
