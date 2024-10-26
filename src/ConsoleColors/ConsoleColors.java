package ConsoleColors;

/**
 * A utility class that provides ANSI escape codes for coloring console output.
 *
 * This class contains static final string constants that represent various
 * text colors and a reset code to revert the console text color back to
 * default. These codes can be used to enhance console output by making it
 * more visually appealing and easier to read.
 *
 * <p>Example usage:</p>
 * <pre>
 * System.out.println(ConsoleColors.RED + "This text will be red!" + ConsoleColors.RESET);
 * </pre>
 */
public class ConsoleColors {
    // ANSI escape codes
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
}
