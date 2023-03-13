package humandeque.manager.local;

import humandeque.manager.CollectionManager;
import models.Human;

/**
 * That manager execute all manipulations on client and store collection in
 * local csv file
 */
public class LocalManager extends CollectionManager {
    private String filePath;

    public LocalManager(String filePath) {
        this.filePath = filePath;
        storage = new CsvStorage(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // TODO: add custom exceptions
    @Override
    public void add(Human element) {
        for (Human human : collection) {
            if (human.getId() == element.getId()) {
                throw new IllegalArgumentException();
            }
        }
        collection.add(element);
    }

    @Override
    public void update(Human element) {
        for (Human human : collection) {
            if (human.getId() == element.getId()) {
                human.setName(element.getName());
                human.setCoordinates(element.getCoordinates());
                human.setCreationDate(element.getCreationDate());
                human.setRealHero(element.isRealHero());
                human.setHasToothpick(element.isHasToothpick());
                human.setImpactSpeed(element.getImpactSpeed());
                human.setMinutesOfWaiting(element.getMinutesOfWaiting());
                human.setMood(element.getMood());
                human.setCar(element.getCar());
                return;
            }
        }
    }

    @Override
    public void remove(long id) {
        for (Human human : collection) {
            if (human.getId() == id) {
                collection.remove(human);
                return;
            }
        }
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
