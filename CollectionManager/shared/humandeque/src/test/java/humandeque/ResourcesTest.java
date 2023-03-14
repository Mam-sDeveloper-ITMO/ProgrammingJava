package humandeque;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static humandeque.manager.Messages.Manager.ELEMENT_NOT_EXISTS;

public class ResourcesTest {
    @Test
    public void testResources() {
        assertEquals(ELEMENT_NOT_EXISTS.formatted(10), "Element with id 10 not found in collection");
    }
}
