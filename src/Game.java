import ConsoleColors.ConsoleColors;
import exceptions.InvalidDataException;
import map.Map;
import map.fields.Field;
import map.fields.Luck;
import map.fields.Property;
import map.fields.Service;
import players.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents the core of the Capitaly game simulation.
 * <p>
 * It handles the reading of the game configuration from a file, simulating the game,
 * and determining which player finishes second to last.
 * </p>
 *
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Game {
    private Map map;
    private ArrayList<Player> players;
    private ArrayList<Player> playersLost;
    private int currentPlayerIndex = 0;
    private int[] preDefinedDiceRolls;

    /**
     * Constructs a new Game object and initializes it based on the input file.
     *
     * @param fileName the name of the file containing the game configuration
     * @throws FileNotFoundException if the input file cannot be found
     * @throws InvalidDataException if the data in the file is invalid
     * @throws InputMismatchException if the first line does not contain the number of fields
     * @throws NumberFormatException if the file contains an invalid number format
     */
    public Game(String fileName){
        try{
            readFromFile(fileName);


        } catch (FileNotFoundException e){
            System.err.println("File not found: " + fileName);
            System.exit(-1);
        } catch (InvalidDataException e) {
            System.err.println("Invalid data in " + fileName + ": " + e.getMessage());
            System.exit(-1);
        } catch (InputMismatchException e){
            System.err.println("The first line must be the number of fields in the map!");
            System.exit(-1);
        } catch (NumberFormatException e){
            System.err.println(e.getMessage() + " is not a valid number!");
            System.exit(-1);
        }
    }


    /**
     * Simulates the game by allowing players to take turns rolling dice and moving
     * on the map. The game continues until only one player remains.
     */
    public void simulatePlay() {
        Random random = new Random();
        int currentRoundIndex = 0;
        int playersStillInGame = players.size();
        playersLost = new ArrayList<>();

        while(playersStillInGame > 1){
            Player currentPlayer = nextPlayer();

            // If there is no predefined sequence of rolls, it should be random
            int diceRoll = preDefinedDiceRolls == null ? random.nextInt(6) + 1 : preDefinedDiceRolls[currentRoundIndex];

            currentPlayer.playField(map.nextField(diceRoll));
            if (currentPlayer.hasLost()){
                playersStillInGame--;
                playersLost.add(currentPlayer);
            }

            currentRoundIndex++;
        }
    }
    /**
     * Gives the next player in the game who hasn't lost yet.
     *
     * @return the next player who is still in the game
     */
    private Player nextPlayer(){
        Player returnValue = players.get(currentPlayerIndex);

        // Finding the next player who hasn't lost the game
        do {
            currentPlayerIndex = (currentPlayerIndex+1) % players.size();
        }while (players.get(currentPlayerIndex).hasLost());

        return returnValue;
    }

    /**
     * Writes the name of the player who finished second to last in the game.
     */
    public void writeSecondToLastPlayer(){
        System.out.println();
        System.out.println(ConsoleColors.YELLOW
                + playersLost.get(1).getName()
                + " finished second to last!"
                + ConsoleColors.RESET);
    }

    /**
     * Reads the game configuration from the given file, including the map,
     * players, and optionally predefined dice rolls.
     *
     * @param fileName the name of the file to read from
     * @throws FileNotFoundException if the file cannot be found
     * @throws InvalidDataException if the file contains invalid data
     * @throws NumberFormatException if the file contains invalid number formats
     */
    private void readFromFile(String fileName) throws FileNotFoundException, InvalidDataException, NumberFormatException {
            try(Scanner scanner = new Scanner(new File(fileName))) {
                // ---- READING MAP ----
                if(!scanner.hasNext()) throw new InvalidDataException("The file must not be empty!");

                int fields = scanner.nextInt();
                scanner.nextLine();

                ArrayList<Field> fieldsList = new ArrayList<>();

                // Testing input data
                if (fields < 1) throw new InvalidDataException("The number of fields must be at least 1!");
                for (int i = 0; i < fields; i++) {
                    String[] line = scanner.nextLine().split(" ");

                    if (line.length != 2 && (line[0].equals("service") || line[0].equals("luck"))) {
                        throw new InvalidDataException(line[0] + " must have a value!");
                    }
                    if (line.length == 2 && line[0].equals("property")) {
                        throw new InvalidDataException("Property must not have a value!");
                    }

                    // Map populating
                    switch (line[0]){
                        case "property":
                            fieldsList.add(new Property());
                            break;
                        case "service":
                            fieldsList.add(new Service(Integer.parseInt(line[1])));
                            break;
                        case "luck":
                            fieldsList.add(new Luck(Integer.parseInt(line[1])));
                            break;
                        default:
                            throw new InvalidDataException(line[0] + " is not a valid field! (property/service/value)");
                    }
                }
                map = new Map(fieldsList);

                // ---- READING PLAYERS ----
                int playersNum = scanner.nextInt();
                scanner.nextLine();

                ArrayList<Player> playersList = new ArrayList<>();

                // Testing input data
                if (playersNum < 3) throw new InvalidDataException("The number of players must be at least 3!");
                for (int i = 0; i < playersNum; i++) {
                    String[] line = scanner.nextLine().split(" ");
                    if (line.length != 2) throw new InvalidDataException("The player must have a name and a playstyle: " +
                            "Name (greedy/careful/tactician)");

                    // Players populating
                    switch (line[1]){
                        case "greedy":
                            playersList.add(new Greedy(line[0]));
                            break;
                        case "careful":
                            playersList.add(new Careful(line[0]));
                            break;
                        case "tactician":
                            playersList.add(new Tactician(line[0]));
                            break;
                        default:
                            throw new InvalidDataException(line[1] + " is not a valid playstyle! (greedy, careful, tactician)");
                    }
                }
                players = playersList;

                // If the results of the dice rolls are predefined
                if (scanner.hasNextLine()){
                    String[] rollsLine = scanner.nextLine().split(" ");
                    preDefinedDiceRolls = new int[rollsLine.length];
                    for (int i = 0; i < rollsLine.length; i++) {
                        int roll = Integer.parseInt(rollsLine[i]);
                        if (roll < 1 || roll > 6) throw  new InvalidDataException(rollsLine[i] + " is not a valid dice roll!");
                        preDefinedDiceRolls[i] = roll;
                    }
                }
            }
        }
}
