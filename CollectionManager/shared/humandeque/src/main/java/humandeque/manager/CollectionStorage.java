package humandeque.manager;

import humandeque.HumanDeque;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;

/**
 * An interface for storing collections of Human objects.
 */
public interface CollectionStorage {
    /**
     * Save a HumanDeque collection to the storage medium.
     * 
     * @param collection the collection to save
     * @throws CollectionSaveError if there was an error while saving the collection
     */
    void save(HumanDeque collection) throws CollectionSaveError;

    /**
     * Load a HumanDeque collection from the storage medium.
     * 
     * @return the loaded collection
     * @throws CollectionLoadError if there was an error while loading the collection
     */
    HumanDeque load() throws CollectionLoadError;
}
