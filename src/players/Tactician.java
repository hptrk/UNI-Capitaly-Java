package players;

import map.fields.Field;
import map.fields.Luck;
import map.fields.Property;
import map.fields.Service;

import java.util.ArrayList;

/**
 * Represents a player that adopts a tactical  strategy in the game.
 *
 @author Horánszki Patrik Donát - CJJ14N
 */
public class Tactician extends Player{
    private boolean willBuy;
    public Tactician(String name) {
        this.setName(name);
        this.setMoney(10000);
        this.setProperties(new ArrayList<Property>());
        this.setHasLost(false);
        willBuy = false;
    }

    /**
     * Implements the player's actions when landing on a field as a "Tactician" player.
     * The player uses a toggle mechanism to determine whether to buy properties or houses.
     * The player will use every other opportunity to buy properties or houses.
     * If the player cannot afford to pay property taxes or service fees, they lose the game.
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

                // Ha van lehetősége vásárolni, kihasználja, de a következő lehetőségnél nem vásárol
                // boolean toggle

                // Lehetőség jelentheti azt, hogy:
                //// a) nincs tulajdonosa, meg tudja venni az ingatlant
                if(fieldOwner == null && canAfford(fieldP.PROPERTY_PRICE)) willBuy = !willBuy;
                //// b) ő a tulajdonosa, nincs még rajta ház, meg tudja venni a házat
                if(fieldOwner == this && !fieldP.getHasHouse() && canAfford(fieldP.HOUSE_PRICE)) willBuy = !willBuy;


                // ---- If the property has no owner ----
                if (fieldOwner == null){

                    // If they have enough money and are at an odd opportunity, they will buy it
                    if(canAfford(fieldP.PROPERTY_PRICE)
                            && willBuy){
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
                    // and they are on an odd opportunity, they will buy it
                    if(fieldOwner.equals(this)
                            && !fieldP.getHasHouse()
                            && canAfford(fieldP.HOUSE_PRICE)
                            && willBuy){
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
