package commands;

/**
 * Used for passing messages from command toe external source.
 * 
 */
public abstract class OutputChannel {
    public abstract void putString(String message);
}
