package humandeque.manager;

import java.io.IOException;

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
    void save(HumanDeque collection) throws IOException;

    /**
     * Load collection from storage
     */
    HumanDeque load() throws IOException;
}
