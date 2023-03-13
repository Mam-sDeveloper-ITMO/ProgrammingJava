package humandeque.manager;

import java.io.IOException;

import humandeque.HumanDeque;
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
     * @param element
     */
    public abstract void add(Human element);

    /**
     * Update element in collection by id.
     * 
     * @param element
     */
    public abstract void update(Human element);

    /**
     * Remove element from collection by id.
     * 
     * @param id
     */
    public abstract void remove(int id);

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
