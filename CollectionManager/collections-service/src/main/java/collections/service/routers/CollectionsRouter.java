package collections.service.routers;

import java.util.HashMap;
import java.util.Map;

import collections.service.api.StatusCodes;
import collections.service.collections.storage.CollectionsStorage;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionLoadError;
import humandeque.manager.exceptions.CollectionSaveError;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;
import models.Human;
import pingback.Level;
import pingback.Logger;
import server.requests.Request;
import server.responses.Response;
import server.routing.Router;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;
import server.routing.middlewares.InnerMiddleware;
import server.routing.middlewares.OuterMiddleware;

public class CollectionsRouter extends Router {
    /**
     * Dispatch user id to collection manager.
     */
    private CollectionsStorage collectionsDispatcher;

    private static Logger logger = Logger.getLogger("mainLogger");

    /**
     * Create a new collections router.
     *
     * @param collectionsDispatcher Dispatch user id to collection manager.
     */
    public CollectionsRouter(CollectionsStorage collectionsDispatcher) {
        super("collections");
        this.collectionsDispatcher = collectionsDispatcher;
    }

    @InnerMiddleware("")
    public Response handleRequest(HandlerFunction handler, Request request) throws IncorrectRequestData {
        logger.log("Request", request.toString(), Level.DEBUG);
        CollectionManager collectionManager;
        try {
            Integer userId = (Integer) request.getData().get("userId");
            collectionManager = collectionsDispatcher.getCollectionManager(userId);
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
        try {
            collectionManager.load();
        } catch (CollectionLoadError | ManipulationError e) {
            return Response.failure("Collection load error: %s".formatted(e.getMessage()),
                    StatusCodes.CANNOT_LOAD_COLLECTION);
        }
        Map<String, Object> data = new HashMap<>(request.getData());
        data.put("collectionManager", collectionManager);
        Response response = handler.handle(data);
        try {
            collectionManager.save();
        } catch (Exception e) {
            logger.log("Middleware", "Collection save error: %s".formatted(e.getMessage()), Level.ERROR);
        }
        return response;
    }

    @OuterMiddleware("")
    public Response handleResponse(Response response) {
        logger.log("Response", response.toString(), Level.DEBUG);
        Map<String, Object> data = new HashMap<>(response.getData());
        data.remove("collectionManager");
        return new Response(response.getOk(), response.getMessage(), data, response.getCode());
    }

    @Handler("add")
    public Response add(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        Human human = getHuman(data);
        try {
            collectionManager.add(human);
            return Response.success(data);
        } catch (ElementAlreadyExistsError e) {
            return Response.failure("Element already exists", StatusCodes.ELEMENT_ALREADY_EXISTS);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    @Handler("update")
    public Response update(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        Human human = getHuman(data);
        try {
            collectionManager.update(human);
            return Response.success(data);
        } catch (ElementNotExistsError e) {
            return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    @Handler("remove")
    public Response remove(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        Long id;
        try {
            id = (long) data.get("id");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
        try {
            collectionManager.remove(id);
            return Response.success(data);
        } catch (ElementNotExistsError e) {
            return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    @Handler("removeFirst")
    public Response removeFirst(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        try {
            collectionManager.removeFirst();
            return Response.success(data);
        } catch (EmptyCollectionError e) {
            return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    @Handler("removeLast")
    public Response removeLast(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        try {
            collectionManager.removeLast();
            return Response.success(data);
        } catch (EmptyCollectionError e) {
            return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    @Handler("clear")
    public Response clear(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        try {
            collectionManager.clear();
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
        return Response.success(data);
    }

    @Handler("get")
    public Response get(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("collection", collectionManager.getCollection());
        return Response.success(responseData);
    }

    /**
     * Save user collection to file.
     *
     * @param data Request data.
     * @return Response.
     * @throws IncorrectRequestData
     */
    @Handler("save")
    public Response save(Map<String, Object> data) throws IncorrectRequestData {
        CollectionManager collectionManager = getCollectionManager(data);
        try {
            collectionManager.save();
            return Response.success(data);
        } catch (CollectionSaveError e) {
            return Response.failure("Collection save error: %s".formatted(e.getMessage()),
                    StatusCodes.CANNOT_SAVE_COLLECTION);
        } catch (ManipulationError e) {
            return Response.failure("Manipulation error: %s".formatted(e.getMessage()),
                    StatusCodes.UNEXPECTED_MANIPULATION_ERROR);
        }
    }

    /**
     * Get collection manager for specific user from request data.
     *
     * @param data Request data.
     * @return Collection manager.
     * @throws IncorrectRequestData
     */
    private CollectionManager getCollectionManager(Map<String, Object> data) throws IncorrectRequestData {
        try {
            return (CollectionManager) data.get("collectionManager");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
    }

    /**
     * Get human from request data.
     *
     * @param data Request data.
     * @return Human.
     * @throws IncorrectRequestData
     */
    private Human getHuman(Map<String, Object> data) throws IncorrectRequestData {
        try {
            return (Human) data.get("human");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
    }
}
