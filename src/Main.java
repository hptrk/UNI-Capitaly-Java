import java.util.Scanner;

/**
 * The main entry point of the Capitaly game simulation.
 * <p>
 * Propmts the user for a file name, reads the game configuration from the file,
 * simulates the game, and outputs the name of the player who finished second to last
 * </p>
 * @author Horánszki Patrik Donát - CJJ14N
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Game configuration file: ");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();

        Game game = new Game(fileName);
        game.simulatePlay();
        game.writeSecondToLastPlayer();
    }
}