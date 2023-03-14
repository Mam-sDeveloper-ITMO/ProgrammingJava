package humandeque.manager;

import java.io.IOException;

import humandeque.HumanDeque;
import humandeque.manager.exceptions.ElementAlreadyExists;
import humandeque.manager.exceptions.ElementNotExists;
import models.Human;

/**
 * Abstract class for all managers.
 * 
 * Store collection instance and provide common manipulations over it.
 */
public abstract class CollectionManager {
    protected HumanDeque collection;
    protected CollectionStorage storage;

    /**
     * Add element to collection if it not already exists.
     * 
     * @param element - element to add
     * @throws ElementAlreadyExists - if element with id already exists in
     *                              collection
     */
    public abstract void add(Human element) throws ElementAlreadyExists;

    /**
     * Update element in collection by id.
     * 
     * @param element
     * @throws ElementNotExists - if element with id not exists in collection
     */
    public abstract void update(Human element) throws ElementNotExists;

    /**
     * Remove element from collection by id.
     * 
     * @param id - id of element to remove
     * @throws ElementNotExists - if element with id not exists in collection
     */
    public abstract void remove(long id) throws ElementNotExists;

    /**
     * Remove all items in collection
     */
    public abstract void clear();

    /**
     * Load collection from storage
     */
    public void load() throws IOException {
        collection = storage.load();
    }

    /**
     * Save collection to storage
     */
    public void save() throws IOException {
        storage.save(collection);
    }

    /**
     * Return current collection instance
     */
    public HumanDeque getCollection() {
        return collection;
    }
}
