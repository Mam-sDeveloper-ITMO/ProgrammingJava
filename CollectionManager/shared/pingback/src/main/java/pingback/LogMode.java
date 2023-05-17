package pingback;

/**
 * LogMode defines the mode of logging.
 */
public enum LogMode {
    /**
     * Console logging only (System.out.println)
     */
    CONSOLE,

    /**
     * PingBack logging only
     */
    PINGBACK,

    /**
     * Both console and PingBack logging
     */
    BOTH;
}
