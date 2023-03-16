package humandeque.manager;

import java.io.IOException;

import humandeque.HumanDeque;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
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
     * @throws ElementAlreadyExistsError - if element with id already exists in
     *                                   collection
     */
    public abstract void add(Human element) throws ElementAlreadyExistsError;

    /**
     * Update element in collection by id.
     * 
     * @param element
     * @throws ElementNotExistsError - if element with id not exists in collection
     */
    public abstract void update(Human element) throws ElementNotExistsError;

    /**
     * Remove element from collection by id.
     * 
     * @param id - id of element to remove
     * @throws ElementNotExistsError - if element with id not exists in collection
     */
    public abstract void remove(long id) throws ElementNotExistsError;

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

    /**
     * Check if element with id exists in collection.
     * 
     * @param id
     * @return
     */
    public boolean isElementExists(long id) {
        for (Human human : collection) {
            if (human.getId() == id) {
                return true;
            }
        }
        return false;
    };

    /**
     * Return element from collection by id.
     * 
     * @param id
     * @return
     * @throws ElementNotExistsError - if element with id not exists in collection
     */
    public Human getElementById(long id) throws ElementNotExistsError {
        for (Human human : collection) {
            if (human.getId() == id) {
                return human;
            }
        }
        throw new ElementNotExistsError(id);
    }
}
