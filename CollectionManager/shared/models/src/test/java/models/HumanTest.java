package models;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HumanTest {
    @Rule public final ExpectedException exception = ExpectedException.none();


    @Test
    public void testHumanBuilderAllField() {
        Human human = Human.builder()
            .name("TestName")
            .coordinates(new Coordinates(1.0f, 2.0f))
            .realHero(true)
            .hasToothpick(false)
            .impactSpeed(1.0)
            .soundtrackName("Civilian Defense")
            .minutesOfWaiting(1.0f)
            .mood(Mood.SADNESS)
            .car(new Car("TestCar"))
            .build();
    }

    @Test
    public void testHumanWrongName() {
        exception.expect(IllegalArgumentException.class);
        Human human = Human.builder()
            .name("")
            .coordinates(new Coordinates(1.0f, 2.0f))
            .realHero(true)
            .hasToothpick(false)
            .impactSpeed(1.0)
            .soundtrackName("Civilian Defense")
            .minutesOfWaiting(1.0f)
            .mood(Mood.SADNESS)
            .car(new Car("TestCar"))
            .build();
    }

    @Test
    public void testHumanToString() {
        Human human = Human.builder()
            .name("TestName")
            .coordinates(new Coordinates(1.0f, 2.0f))
            .realHero(true)
            .hasToothpick(false)
            .impactSpeed(1.0)
            .soundtrackName("Civilian Defense")
            .minutesOfWaiting(1.0f)
            .mood(Mood.SADNESS)
            .car(new Car("TestCar"))
            .build();
        System.out.println(human);
    }
}
