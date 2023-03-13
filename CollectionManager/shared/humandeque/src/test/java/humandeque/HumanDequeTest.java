package humandeque;

import org.junit.Test;

import models.Car;
import models.Coordinates;
import models.Human;

public class HumanDequeTest {
    @Test
    public void test() {
        HumanDeque humanDeque = new HumanDeque();
        humanDeque.add(new Human("TestName", new Coordinates(0.f, 0.f), "Civilian Defense", new Car("Tets car")));
    }
}
