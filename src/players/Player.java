package players;

import ConsoleColors.ConsoleColors;
import map.fields.Field;
import map.fields.Property;

import java.util.ArrayList;

/**
 * The {@code Player} class represents a player in the game.
 * Each player has a name, money, properties, and a status indicating if they have lost the game.
 * The class defines the behavior common to all player types, including methods for managing money, properties,
 * and game status.
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public abstract class Player {
    /** The player's name. */
    private String name;
    /** The player's current money balance. */
    private int money;
    /** The list of properties owned by the player. */
    private ArrayList<Property> properties;
    /** Whether the player has lost the game. */
    private boolean hasLost;

    // ---- Name ----
    public String getName(){return name;}
    void setName(String name){this.name=name;}

    // ---- Money ----
    public int getMoney() { return money;}
    void setMoney(int money){this.money=money;}
    void payMoney(int money){this.money-=money;}
    boolean canAfford(int price){return money>=price;}
    public void receivePayment(int amount){
        this.money += amount;
    }

    // ---- Property ----
    void setProperties(ArrayList<Property> properties){this.properties=properties;}
    void addProperty(Property property){this.properties.add(property);}


    // ---- Losing ----
    public boolean hasLost(){
        return this.hasLost;
    }
    public void setHasLost(boolean hasLost){this.hasLost=hasLost;}

    void lose(){
        this.hasLost=true;
        for(Property property : properties){
            property.resetProperty();
        }
        System.out.println(ConsoleColors.RED + name + " lost the game!" + ConsoleColors.RESET);
    }

    // ---- Playstyle ----
    /**
     * Abstract method for player actions when landing on a specific field.
     * The method defines how the player interacts with different types of fields
     * such as property, service, or luck. Each concrete player class must implement
     * this method to define its specific behavior on each type of field.
     *
     * @param field the field that the player has landed on, which can be of various types
     * (property, service, luck)
     */
    public abstract void playField(Field field);

    // ---- Writing to console ----
    /**
     * Writes a message to the console indicating an action performed by a player.
     * The message varies based on the action related to property transactions.
     *
     * @param player the player who performed the action
     * @param action a string representing the type of action
     */
    void writeActionToConsole(Player player, String action){
        switch (action){
            case "property/buy":
                System.out.println(
                        ConsoleColors.CYAN + player.getName() + " bought a property!" + ConsoleColors.RESET
                        + " Balance: "
                        + ConsoleColors.RED + player.getMoney() + ConsoleColors.RESET);
                break;
            case "property/buyHouse":
                System.out.println(
                         ConsoleColors.CYAN + player.getName() + " bought a house!" + ConsoleColors.RESET
                        + " Balance: "
                        + ConsoleColors.RED + player.getMoney() + ConsoleColors.RESET);
                break;
            case "property/payTax":
                System.out.println(player.getName() + " payed the property tax to the owner!"
                        + " Balance: " + ConsoleColors.RED + player.getMoney() + ConsoleColors.RESET);
                break;
        }
    }
    /**
     * Writes a message to the console indicating an action performed by a player.
     * The message varies based on the action related to service or luck fields.
     *
     * @param player the player who performed the action
     * @param action a string representing the type of action
     * @param value the amount involved in the action, such as the cost of a service or the value of a lucky payment
     */
    void writeActionToConsole(Player player, String action, int value){
        switch (action){
            case "service/pay":
                System.out.println(player.getName() + " payed for a service (" + value + ")! Balance: "
                        + ConsoleColors.RED + player.getMoney() + ConsoleColors.RESET);
                break;
            case "luck/receivePayment":
                System.out.println(player.getName() + " received a lucky payment (" + value + ")! Balance: "
                        + ConsoleColors.GREEN + player.getMoney() + ConsoleColors.RESET);
                break;
        }
    }
}
