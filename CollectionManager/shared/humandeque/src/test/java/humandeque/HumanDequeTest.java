package humandeque;

import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import models.Car;
import models.Coordinates;
import models.Human;

public class HumanDequeTest {
    @Test
    public void testNewConstructor() {
        HumanDeque humanDeque = new HumanDeque();
        humanDeque.add(new Human("TestName", new Coordinates(0.f, 0.f), "Civilian Defense", new Car("Tets car")));
    }

    @Test
    public void testNewConstructorWithTime() {
        HumanDeque humanDeque = new HumanDeque();
        humanDeque.add(new Human("TestName", new Coordinates(0.f, 0.f), "Civilian Defense", new Car("Tets car")));
        HumanDeque humanDeque2 = new HumanDeque(humanDeque.getCreateTime(), humanDeque);
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
