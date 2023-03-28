package cliapp.utils;

/**
 * Used for coloring text in console.
 */
public class TextColor {
    /**
     * ANSI code for resetting text color.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * ANSI code for black text.
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * ANSI code for red text.
     */
    public static final String RED = "\u001B[31m";

    /**
     * ANSI code for green text.
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * ANSI code for yellow text.
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * ANSI code for blue text.
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * ANSI code for purple text.
     */
    public static final String PURPLE = "\u001B[35m";

    /**
     * ANSI code for cyan text.
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * ANSI code for white text.
     */
    public static final String WHITE = "\u001B[37m";

    /**
     * Returns the given text wrapped in the given color.
     *
     * @param text  the text to color.
     * @param color the color to use.
     * @return the colored text.
     */
    public static String getColoredString(String text, String color) {
        return color + text + ANSI_RESET;
    }
}
