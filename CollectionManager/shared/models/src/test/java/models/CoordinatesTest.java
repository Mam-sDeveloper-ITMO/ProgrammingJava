package models;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CoordinatesTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCoordinatesConstructor() {
        Coordinates coordinates = new Coordinates(
                1.0f,
                2.0f);
    }

    @Test
    public void testCoordinatesBuilder() {
        Coordinates coordinates = Coordinates.builder()
                .x(1.0f)
                .y(2.0f)
                .build();
    }

    @Test()
    public void testCoordinatesMinX() {
        exception.expect(IllegalArgumentException.class);
        Coordinates coordinates = new Coordinates(-1000.0f, 2.0f);
    }

    @Test()
    public void testCoordinatesMinY() {
        exception.expect(IllegalArgumentException.class);
        Coordinates coordinates = new Coordinates(2.0f, -1000.0f);
    }

    @Test
    public void testCoordinatesNullX() {
        exception.expect(NullPointerException.class);
        Coordinates coordinates = new Coordinates(null, 2.0f);
    }

    @Test
    public void testCoordinatesNullY() {
        exception.expect(NullPointerException.class);
        Coordinates coordinates = new Coordinates(2.0f, null);
    }
}
