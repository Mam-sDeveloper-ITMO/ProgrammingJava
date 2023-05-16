package humandeque.manager;

import humandeque.HumanDeque;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;
import models.Human;

/**
 * An abstract class for managing a collection of Human objects.
 *
 * This class provides common manipulations for a HumanDeque collection,
 * including adding, updating, and removing
 * elements from the collection. It also allows the collection to be loaded and
 * saved from a storage medium using
 * a CollectionStorage object.
 */
public abstract class CollectionManager {
    /**
     * The HumanDeque collection being managed.
     */
    protected HumanDeque collection;

    /**
     * The CollectionStorage object used to load and save the collection.
     */
    protected CollectionStorage storage;

    /**
     * Add an element to the collection if it does not already exist.
     *
     * @param element the element to add
     * @throws ElementAlreadyExistsError if an element with the same id already
     *                                   exists in the collection
     */
    public abstract void add(Human element) throws ElementAlreadyExistsError, ManipulationError;

    /**
     * Update an element in the collection by its id.
     *
     * @param element the updated element
     * @throws ElementNotExistsError if no element with the same id exists in the
     *                               collection
     */
    public abstract void update(Human element) throws ElementNotExistsError, ManipulationError;

    /**
     * Remove an element from the collection by its id.
     *
     * @param id the id of the element to remove
     * @throws ElementNotExistsError if no element with the given id exists in the
     *                               collection
     */
    public abstract void remove(long id) throws ElementNotExistsError, ManipulationError;

    /**
     * Remove the first element in the collection.
     *
     * @throws EmptyCollectionError if the collection is empty
     */
    public abstract void removeFirst() throws EmptyCollectionError, ManipulationError;

    /**
     * Remove the last element in the collection.
     *
     * @throws EmptyCollectionError if the collection is empty
     */
    public abstract void removeLast() throws EmptyCollectionError, ManipulationError;

    /**
     * Remove all elements from the collection.
     */
    public abstract void clear();

    /**
     * Load the collection from the storage medium using the assigned
     * CollectionStorage object.
     *
     * @throws CollectionLoadError if there was an error loading the collection
     */
    public void load() throws CollectionLoadError, ManipulationError {
        collection = storage.load();
    }

    /**
     * Save the collection to the storage medium using the assigned
     * CollectionStorage object.
     *
     * @throws CollectionSaveError if there was an error saving the collection
     */
    public void save() throws CollectionSaveError, ManipulationError {
        try {
            storage.save(collection);
        } catch (CollectionSaveError e) {
            throw e;
        } catch (Exception e) {
            throw new CollectionSaveError(e.getMessage(), e);
        }
    }

    /**
     * Get the current collection instance.
     *
     * @return the current collection instance
     */
    public HumanDeque getCollection() {
        return collection;
    }

    /**
     * Check if an element with the given id exists in the collection.
     *
     * @param id the id to check for
     * @return true if an element with the given id exists in the collection, false
     *         otherwise
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
     * @param id id of element
     * @return element from collection
     * @throws ElementNotExistsError - if element with id not exists in collection
     */
    public Human getElementById(long id) throws ElementNotExistsError, ManipulationError {
        for (Human human : collection) {
            if (human.getId() == id) {
                return human;
            }
        }
        throw new ElementNotExistsError(id);
    }
}
