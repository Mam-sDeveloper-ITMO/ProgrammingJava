package collections.service.routers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import collections.service.api.StatusCodes;
import collections.service.dbmodels.HumanModel;
import collections.service.dbmodels.converters.HumanConverter;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.connection.ConnectionManager;
import fqme.view.View;
import humandeque.HumanDeque;
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
import server.utils.DataConverter;

public class CollectionsRouter extends Router {
    private static Logger logger = Logger.getLogger("mainLogger");

    /**
     * Create a new collections router.
     */
    public CollectionsRouter() {
        super("collections");
    }

    @InnerMiddleware("")
    public Response handleRequest(HandlerFunction handler, Request request) throws IncorrectRequestData {
        logger.log("Request", request.toString(), Level.DEBUG);
        // prepare request data
        Map<String, Object> data = DataConverter.serializableToObjects(request.getData());
        getUserId(data);
        return handler.handle(data);
    }

    @OuterMiddleware("")
    public Response handleResponse(Request request, Response response) {
        logger.log("Response", response.toString(), Level.DEBUG);
        return response;
    }

    @Handler("add")
    public Response add(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        Human human = getHuman(data);
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            HumanModel humanModel = HumanConverter.toHumanModel(human, userId);
            humanModel = humanView.put(humanModel).iterator().next();

            return Response.success(Map.of("human", HumanConverter.toHuman(humanModel)));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("update")
    public Response update(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        Human human = getHuman(data);
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            HumanModel humanModel = HumanConverter.toHumanModel(human, userId);
            Set<HumanModel> humanModels = humanView.get(
                    HumanModel.ownerId_.eq(userId)
                            .and(HumanModel.id_.eq(humanModel.getId())));

            if (humanModels.isEmpty()) {
                return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
            }
            humanView.put(humanModel);

            return Response.success(Map.of("human", human));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("remove")
    public Response remove(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        Integer humanId;
        try {
            humanId = ((Long) data.get("id")).intValue();
        } catch (ClassCastException | NullPointerException e) {
            throw new IncorrectRequestData();
        }
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(
                    HumanModel.ownerId_.eq(userId)
                            .and(HumanModel.id_.eq(humanId)));

            if (humanModels.isEmpty()) {
                return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
            }
            humanView.delete(HumanModel.id_.eq(humanId));

            return Response.success(Map.of("humanId", humanId));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("removeFirst")
    public Response removeFirst(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(userId));
            if (humanModels.isEmpty()) {
                return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
            }

            HumanModel humanModel = humanModels.stream().min(Comparator.comparing(HumanModel::getImpactSpeed)).get();
            humanView.delete(HumanModel.id_.eq(humanModel.getId()));

            return Response.success(Map.of("human", HumanConverter.toHuman(humanModel)));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("removeLast")
    public Response removeLast(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(userId));
            if (humanModels.isEmpty()) {
                return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
            }

            HumanModel humanModel = humanModels.stream().max(Comparator.comparing(HumanModel::getImpactSpeed)).get();
            humanView.delete(HumanModel.id_.eq(humanModel.getId()));

            return Response.success(Map.of("human", HumanConverter.toHuman(humanModel)));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("clear")
    public Response clear(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            humanView.delete(HumanModel.ownerId_.eq(userId));
            
            return Response.success();
        } catch (Exception e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("get")
    public Response get(Map<String, Object> data) throws IncorrectRequestData {
        Integer userId = (Integer) data.get("userId");
        try {
            Connection connection = ConnectionManager.getConnection(HumanModel.class);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(userId));
            HumanDeque collection = HumanConverter.toHumanDeque(humanModels);
            return Response.success(Map.of("collection", collection));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
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

    /**
     * Get user id from request data.
     *
     * @param data Request data.
     * @return User id.
     * @throws IncorrectRequestData
     */
    private Integer getUserId(Map<String, Object> data) throws IncorrectRequestData {
        try {
            return (Integer) data.get("userId");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
    }
}
