package humandeque.manager;

import humandeque.HumanDeque;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;

/**
 * 
 * Interface for collection storages
 */
public interface CollectionStorage {
    /**
     * Save collection
     * 
     * @param collection
     */
    void save(HumanDeque collection) throws CollectionSaveError;

    /**
     * Load collection from storage
     */
    HumanDeque load() throws CollectionLoadError;
}
