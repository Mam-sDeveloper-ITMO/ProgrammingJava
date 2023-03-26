package humandeque;

import static humandeque.Messages.Manager.ELEMENT_NOT_EXISTS;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResourcesTest {
    @Test
    public void testResources() {
        assertEquals(ELEMENT_NOT_EXISTS.formatted(10),
            "Element with id 10 not found in collection");
    }
}
