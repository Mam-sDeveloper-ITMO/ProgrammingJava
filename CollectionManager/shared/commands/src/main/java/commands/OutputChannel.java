package commands;

/**
 * Used for passing messages from command to an external source.
 * 
 * For example, it can be used to print messages to the console or to send them
 * to a popup window.
 */
@FunctionalInterface
public interface OutputChannel {
    /**
     * Puts a string message to the output channel.
     * 
     * @param message The message to be output.
     */
    void putString(String message);
}