package humandeque.manager;

import humandeque.HumanDeque;

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
    void save(HumanDeque collection);

    /**
     * Load collection from storage
     */
    HumanDeque load();
}
