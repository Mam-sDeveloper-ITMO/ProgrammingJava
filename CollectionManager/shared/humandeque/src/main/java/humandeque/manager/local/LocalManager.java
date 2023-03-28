package humandeque.manager.local;

import java.io.IOException;

import humandeque.HumanDeque;
import humandeque.TextResources.Manager.ExceptionsResources;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.EmptyCollectionError;
import lombok.Getter;
import lombok.Setter;
import models.Human;

/**
 * That manager execute all manipulations on client and store collection in
 * local csv file
 */
public class LocalManager extends CollectionManager {
    @Getter @Setter
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
    public LocalManager(String filePath) throws CollectionLoadError {
        this.filePath = filePath;
        load();
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

    @Override
    public void removeFirst() throws EmptyCollectionError {
        if (collection.isEmpty()) {
            throw new EmptyCollectionError();
        }
        collection.removeFirst();
    }

    @Override
    public void removeLast() throws EmptyCollectionError {
        if (collection.isEmpty()) {
            throw new EmptyCollectionError();
        }
        collection.removeLast();
    }

    @Override
    public void load() throws CollectionLoadError {
        if (filePath == null) {
            throw new CollectionLoadError(ExceptionsResources.FILE_PATH_NOT_SPECIFIED_ERROR);
        }
        storage = new CsvStorage(filePath);
        super.load();
    }

    @Override
    public void save() throws CollectionSaveError {
        if (filePath == null) {
            throw new CollectionSaveError(ExceptionsResources.FILE_PATH_NOT_SPECIFIED_ERROR);
        }
        storage = new CsvStorage(filePath);
        super.save();
    }
}
