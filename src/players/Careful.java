package players;

import map.fields.Field;
import map.fields.Luck;
import map.fields.Property;
import map.fields.Service;

import java.util.ArrayList;

/**
 * Represents a player that adopts a careful strategy in the game.
 *
 @author Horánszki Patrik Donát - CJJ14N
 */
public class Careful extends Player {
    public Careful(String name) {
        this.setName(name);
        this.setMoney(10000);
        this.setProperties(new ArrayList<Property>());
        this.setHasLost(false);
    }

    /**
     * Implements the player's actions when landing on a field as a "Careful" player.
     * The player decides to buy properties or houses only if they can afford them
     * without dropping below half of their total money. If unable to pay property taxes
     * or service fees, the player loses the game.
     *
     * @param field the field that the player has landed on, which can be of type property, service, or luck
     */
    @Override
    public void playField(Field field){
        switch (field.getType()){
            case "property":
                Property fieldP = (Property) field;
                Player fieldOwner = fieldP.getOwner();
                int fieldValue = fieldP.getValue();

                // ---- If the property has no owner ----
                if (fieldOwner == null){

                    // If they have enough money and won't go below half of their balance,
                    // they will buy it
                    if (canAfford(fieldP.PROPERTY_PRICE)
                            && getMoney()-fieldP.PROPERTY_PRICE >= getMoney()/2){
                        fieldP.buyProperty(this);
                        addProperty(fieldP);
                        payMoney(fieldP.PROPERTY_PRICE);

                        writeActionToConsole(this, "property/buy");
                        break;
                    }
                }

                // ---- If the property has an owner ----
                if (fieldOwner != null){

                    // If they are the owner and there is no house on it,
                    // and they won't go below half of their money, they will buy it.
                    if(fieldOwner.equals(this)
                            && !fieldP.getHasHouse()
                            && canAfford(fieldP.HOUSE_PRICE)
                            && getMoney()-fieldP.HOUSE_PRICE >= getMoney()/2){
                        fieldP.buyHouse(this);
                        payMoney(fieldP.HOUSE_PRICE);

                        writeActionToConsole(this, "property/buyHouse");
                        break;
                    }

                    // If they are not the owner
                    if (!fieldOwner.equals(this)){

                        // If they can pay the owner of the field, they will pay
                        if(canAfford(fieldValue)){
                            payMoney(fieldValue);
                            fieldOwner.receivePayment(fieldValue);

                            writeActionToConsole(this, "property/payTax");
                        }

                        // If they cannot pay, they lose the game
                        if(!canAfford(fieldValue)) lose();
                    }
                }

                break;
            case "service":
                Service fieldS = (Service) field;

                // If they can pay, they pay; if not, they lose the game
                if (canAfford(fieldS.getValue())){
                    payMoney(fieldS.getValue());
                    writeActionToConsole(this, "service/pay", fieldS.getValue());
                }
                else lose();

                break;

            case "luck":
                Luck fieldL = (Luck) field;
                receivePayment(fieldL.getValue());

                writeActionToConsole(this, "luck/receivePayment", fieldL.getValue());
                break;
        }
    }
}
