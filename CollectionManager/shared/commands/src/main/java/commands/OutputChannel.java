package commands;

/**
 * Used for passing messages from command toe external source.
 * 
 * For example, it can be used to print messages to console or to send them to popup window.
 */
@FunctionalInterface
public interface OutputChannel {
    void putString(String message);
}
