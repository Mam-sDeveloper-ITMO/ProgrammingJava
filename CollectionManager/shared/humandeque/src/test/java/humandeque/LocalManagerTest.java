package humandeque;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.local.LocalManager;
import lombok.SneakyThrows;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

public class LocalManagerTest {
    File resourcesDirectory = new File("src/test/resources/humandeque");

    LocalManager manager;

    Human testHuman = Human.builder()
        .name("test")
        .coordinates(new Coordinates(1.f, 1.f))
        .realHero(true)
        .hasToothpick(true)
        .impactSpeed(1.0)
        .minutesOfWaiting(1.f)
        .mood(Mood.FRENZY)
        .car(new Car("testCar"))
        .build();

    @Before
    @Test
    public void setUp() throws Exception {
        resourcesDirectory.mkdir();
        manager = new LocalManager(resourcesDirectory.getPath() + "/testLoad.csv");
        try {
            manager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(manager.getCollection());
    }

    @Test
    public void testAddWithException() {
        try {
            manager.add(testHuman);
            manager.add(testHuman);
            Assert.fail("ElementAlreadyExists exception should be thrown");
        } catch (ElementAlreadyExistsError e) {
        }
    }

    @Test
    @SneakyThrows
    public void testAdd() {
        manager.add(testHuman);
        assertNotEquals(manager.getCollection().size(), 0);
    }

    @Test
    public void testRemoveWithExceptions() {
        try {
            manager.remove(0);
            Assert.fail("ElementNotExists exception should be thrown");
        } catch (Exception e) {
        }
    }

    @Test
    @SneakyThrows
    public void testRemove() {
        manager.add(testHuman);
        manager.remove(testHuman.getId());
        assertNotEquals(manager.getCollection().size(), 1);
    }

    @Test
    public void testUpdateWithExceptions() {
        try {
            manager.update(testHuman);
            Assert.fail("ElementNotExists exception should be thrown");
        } catch (Exception e) {
        }
    }

    @Test
    @SneakyThrows
    public void testUpdate() {
        manager.add(testHuman);
        Human updatedHuman = Human.builder()
            .id(1)
            .name("test")
            .coordinates(new Coordinates(1.f, 1.f))
            .realHero(true)
            .hasToothpick(true)
            .impactSpeed(1.0)
            .minutesOfWaiting(1.f)
            .mood(Mood.FRENZY)
            .car(new Car("testCar"))
            .build();
        manager.update(updatedHuman);
    }

    @Test
    public void testRemoveFirst() {
        try {
            manager.add(testHuman);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        try {
            manager.removeFirst();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        manager.clear();
        try {
            manager.removeFirst();
            Assert.fail("EmptyCollectionError should be thrown");
        } catch (EmptyCollectionError e) {
        }
    }

    @Test
    public void testRemoveLast() {
        try {
            manager.add(testHuman);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        try {
            manager.removeLast();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        manager.clear();
        try {
            manager.removeLast();
            Assert.fail("EmptyCollectionError should be thrown");
        } catch (EmptyCollectionError e) {
        }
    }

    @Test
    @SneakyThrows
    public void testClear() {
        manager.add(testHuman);
        manager.clear();
        assertNotEquals(manager.getCollection().size(), 1);
    }

    @Test
    @SneakyThrows
    public void testSave() {
        manager.add(testHuman);
        try {
            manager.save();
        } catch (Exception e) {
            // TODO: handle exception
        }
        assertNotEquals(manager.getCollection().size(), 0);
    }

}
