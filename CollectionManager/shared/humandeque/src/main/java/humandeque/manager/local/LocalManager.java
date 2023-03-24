package humandeque.manager.local;

import java.io.IOException;

import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
import models.Human;

/**
 * That manager execute all manipulations on client and store collection in
 * local csv file
 */
public class LocalManager extends CollectionManager {
    private String filePath;

    /**
     * Create new LocalManager with empty collection
     */
    public LocalManager() {
        super();
        collection = new HumanDeque();
    }

    /**
     * Create new LocalManager with collection from csv file
     * 
     * @param filePath - path to csv file
     * @throws IOException
     */
    public LocalManager(String filePath) throws IOException {
        this.filePath = filePath;
        storage = new CsvStorage(filePath);
        load();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void add(Human element) throws ElementAlreadyExistsError {
        if (isElementExists(element.getId())) {
            throw new ElementAlreadyExistsError(element.getId());
        }
        collection.add(element);
    }

    @Override
    public void update(Human element) throws ElementNotExistsError {
        Human human = getElementById(element.getId());
        collection.remove(human);
        collection.add(element);
        return;
    }

    @Override
    public void remove(long id) throws ElementNotExistsError {
        Human human = getElementById(id);
        collection.remove(human);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
