package pingback;


import org.apache.http.HttpResponse;
import org.junit.Test;

public class LoggerTest {
    @Test
    public void liveLoggerTest() {
        Logger logger = new Logger("", "", "default", "test");
        HttpResponse result = logger.log("test", "title1", "description1", Level.INFO);

        System.out.println(result);
    }
}
