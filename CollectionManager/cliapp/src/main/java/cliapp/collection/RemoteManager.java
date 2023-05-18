package cliapp.collection;

import java.util.Map;

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import humandeque.HumanDeque;
import humandeque.TextResources.Manager.ExceptionsResources;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;
import lombok.Getter;
import models.Human;
import server.responses.Response;

/**
 * That manager execute all manipulations on client and store collection in
 * local csv file
 */
public class RemoteManager extends CollectionManager {
    /**
     * user id
     */
    @Getter
    private final int userId;

    /**
     * Adapter for send requests to collections service
     */
    private Adapter serviceAdapter;

    /**
     * Create new RemoteManager and load collection from collections service
     *
     * @param serviceAdapter Adapter for send requests to collections service
     * @param userId         user id
     * @throws CollectionLoadError if collection can't be loaded
     */
    public RemoteManager(Adapter serviceAdapter, int userId) throws CollectionLoadError, ManipulationError {
        this.userId = userId;
        this.serviceAdapter = serviceAdapter;
        load();
    }

    @Override
    public void add(Human element) throws ElementAlreadyExistsError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId, "human", element);
            Response response = serviceAdapter.triggerServer("collections.add", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            this.collection.add(element);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void update(Human element) throws ElementNotExistsError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId, "human", element);
            Response response = serviceAdapter.triggerServer("collections.update", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            Human human = getElementById(element.getId());
            collection.remove(human);
            collection.add(element);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void remove(long id) throws ElementNotExistsError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId, "id", id);
            Response response = serviceAdapter.triggerServer("collections.remove", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            Human human = getElementById(id);
            collection.remove(human);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void clear() throws ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId);
            Response response = serviceAdapter.triggerServer("collections.clear", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            collection.clear();
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void removeFirst() throws EmptyCollectionError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId);
            Response response = serviceAdapter.triggerServer("collections.removeFirst", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            collection.removeFirst();
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void removeLast() throws EmptyCollectionError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId);
            Response response = serviceAdapter.triggerServer("collections.removeLast", data);
            if (!response.getOk()) {
                throw new ManipulationError(response.getMessage());
            }
            collection.removeLast();
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new ManipulationError(e.getMessage());
        }
    }

    @Override
    public void load() throws CollectionLoadError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId);
            Response response = serviceAdapter.triggerServer("collections.get", data);
            if (response.ok) {
                HumanDeque loadedCollection = (HumanDeque) response.data.get("collection");
                this.collection = loadedCollection;
            } else {
                throw new CollectionLoadError(ExceptionsResources.COLLECTION_LOAD_ERROR);
            }
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new CollectionLoadError(ExceptionsResources.COLLECTION_LOAD_ERROR);
        }
    }

    @Override
    public void save() throws CollectionSaveError, ManipulationError {
        try {
            Map<String, Object> data = Map.of("userId", userId, "collection", collection);
            Response response = serviceAdapter.triggerServer("collections.save", data);
            if (!response.getOk()) {
                throw new CollectionSaveError(ExceptionsResources.COLLECTION_SAVE_ERROR);
            }
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed | ClassCastException e) {
            throw new CollectionSaveError(ExceptionsResources.COLLECTION_SAVE_ERROR);
        }
    }
}
