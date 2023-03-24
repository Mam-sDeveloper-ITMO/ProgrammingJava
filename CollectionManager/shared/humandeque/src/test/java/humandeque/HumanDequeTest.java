package humandeque;

import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class HumanDequeTest {
    Human testHuman1 = Human.builder()
            .name("test")
            .coordinates(new Coordinates(1.f, 1.f))
            .realHero(true)
            .hasToothpick(true)
            .impactSpeed(1.0)
            .minutesOfWaiting(1.f)
            .mood(Mood.FRENZY)
            .car(new Car("testCar"))
            .build();

    @Test
    public void testNewConstructor() {
        HumanDeque humanDeque = new HumanDeque();
        humanDeque.add(testHuman1);
    }

    @Test
    public void testExistedConstructor() {
        HumanDeque humanDeque = new HumanDeque();
        humanDeque.add(testHuman1);
        HumanDeque humanDeque2 = new HumanDeque(humanDeque);
        assertNotEquals(humanDeque, humanDeque2);
    }

    @Test
    public void testCreateTime() {
        HumanDeque humanDeque1 = new HumanDeque();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {

        }
        HumanDeque humanDeque2 = new HumanDeque();
        assertNotEquals(humanDeque1.getCreateTime(), humanDeque2.getCreateTime());
    }
}
