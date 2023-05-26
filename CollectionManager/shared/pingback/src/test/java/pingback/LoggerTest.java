package pingback;


import org.junit.Test;

public class LoggerTest {
    @Test
    public void liveLoggerTest() {
        Logger logger = Logger.getLogger("", "", "", "test");
        logger.setLogMode(LogMode.CONSOLE);
        logger.log("default", "title1", "description1", Level.INFO);
        logger.log("default", "title2", "description2", Level.ERROR);
        logger.log("default", "title3", "description3", Level.WARNING);
        logger.log("default", "title4", "description4", Level.DEBUG);
        logger.log("default", "title5", "description5", Level.CRITICAL);
    }
}
