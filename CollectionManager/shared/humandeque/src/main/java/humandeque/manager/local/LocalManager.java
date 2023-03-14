package humandeque.manager.local;

import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExists;
import humandeque.manager.exceptions.ElementNotExists;
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

    @Override
    public void add(Human element) throws ElementAlreadyExists {
        for (Human human : collection) {
            if (human.getId() == element.getId()) {
                throw new ElementAlreadyExists(human.getId());
            }
        }
        collection.add(element);
    }

    @Override
    public void update(Human element) throws ElementNotExists {
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
        throw new ElementNotExists(element.getId());
    }

    @Override
    public void remove(long id) throws ElementNotExists {
        for (Human human : collection) {
            if (human.getId() == id) {
                collection.remove(human);
                return;
            }
        }
        throw new ElementNotExists(id);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
