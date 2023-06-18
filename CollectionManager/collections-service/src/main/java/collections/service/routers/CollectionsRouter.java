package collections.service.routers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import auth.AuthProvider;
import auth.AuthToken;
import auth.exceptions.VerifyFailed;
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
    private final AuthProvider authProvider;

    private static Logger logger = Logger.getLogger("mainLogger");

    /**
     * Create a new collections router.
     */
    public CollectionsRouter(AuthProvider authProvider) {
        super("collections");
        this.authProvider = authProvider;
    }

    @InnerMiddleware("")
    public Response handleRequest(HandlerFunction handler, Request request) throws IncorrectRequestData {
        logger.log("Request", request.toString(), Level.DEBUG);
        // prepare request data
        Map<String, Object> data = DataConverter.serializableToObjects(request.getData());

        if (!checkLogin(data)) {
            return Response.failure("Unauthorized", 401);
        }
        AuthToken token = getToken(data);
        data.put("ownerId", token.getUserId());

        try (Connection connection = ConnectionManager.getConnection(HumanModel.class)) {
            data.put("connection", connection);
            return handler.handle(data);
        } catch (SQLException e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    private Boolean checkLogin(Map<String, Object> data) throws IncorrectRequestData {
        AuthToken token = getToken(data);
        try {
            return this.authProvider.verify(token);
        } catch (VerifyFailed e) {
            logger.log("VerifyFailed", e.getMessage(), Level.ERROR);
            return false;
        }
    }

    @OuterMiddleware("")
    public Response handleResponse(Request request, Response response) {
        logger.log("Response", response.toString(), Level.DEBUG);
        return response;
    }

    @Handler("add")
    public Response add(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Human human = getHuman(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            HumanModel humanModel = HumanConverter.toHumanModel(human, ownerId);
            humanModel = humanView.put(humanModel).iterator().next();
            connection.commit();

            return Response.success(Map.of("human", HumanConverter.toHuman(humanModel)));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("update")
    public Response update(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Human human = getHuman(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            HumanModel humanModel = HumanConverter.toHumanModel(human, ownerId);
            Set<HumanModel> humanModels = humanView.get(
                    HumanModel.ownerId_.eq(ownerId)
                            .and(HumanModel.id_.eq(humanModel.getId())));

            if (humanModels.isEmpty()) {
                return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
            }
            humanView.put(humanModel);
            connection.commit();

            return Response.success(Map.of("human", human));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("remove")
    public Response remove(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Integer humanId;
        try {
            humanId = ((Long) data.get("id")).intValue();
        } catch (ClassCastException | NullPointerException e) {
            throw new IncorrectRequestData();
        }
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(
                    HumanModel.ownerId_.eq(ownerId)
                            .and(HumanModel.id_.eq(humanId)));

            if (humanModels.isEmpty()) {
                return Response.failure("Element not exists", StatusCodes.ELEMENT_NOT_EXISTS);
            }
            HumanModel humanModel = humanModels.iterator().next();
            humanView.delete(HumanModel.id_.eq(humanId));

            Human removedHuman = HumanConverter.toHuman(humanModel);

            connection.commit();
            return Response.success(Map.of("human", removedHuman));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("removeFirst")
    public Response removeFirst(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);

            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(ownerId));
            if (humanModels.isEmpty()) {
                return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
            }
            HumanModel humanModel = humanModels.stream()
                    .min(Comparator.comparing(HumanModel::getImpactSpeed)).get();
            humanView.delete(HumanModel.id_.eq(humanModel.getId()));

            Human removedHuman = HumanConverter.toHuman(humanModel);
            connection.commit();
            return Response.success(Map.of("human", removedHuman));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("removeLast")
    public Response removeLast(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(ownerId));
            if (humanModels.isEmpty()) {
                return Response.failure("Collection is empty", StatusCodes.COLLECTION_IS_EMPTY);
            }

            HumanModel humanModel = humanModels.stream()
                    .max(Comparator.comparing(HumanModel::getImpactSpeed)).get();
            humanView.delete(HumanModel.id_.eq(humanModel.getId()));

            Human removedHuman = HumanConverter.toHuman(humanModel);
            connection.commit();
            return Response.success(Map.of("human", removedHuman));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("clear")
    public Response clear(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<HumanModel> humanView = View.of(HumanModel.class, connection);

            humanView.delete(HumanModel.ownerId_.eq(ownerId));

            connection.commit();
            return Response.success();
        } catch (Exception e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("get")
    public Response get(Map<String, Object> data) throws IncorrectRequestData {
        Integer ownerId = (Integer) data.get("ownerId");
        Connection connection = (Connection) data.get("connection");
        try {
            View<HumanModel> humanView = View.of(HumanModel.class, connection);
            Set<HumanModel> humanModels = humanView.get(HumanModel.ownerId_.eq(ownerId));
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
    private Integer getOwnerId(Map<String, Object> data) throws IncorrectRequestData {
        try {
            return (Integer) data.get("ownerId");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
    }

    /**
     * Get auth token from request data.
     *
     * @param data Request data.
     * @return Auth token.
     * @throws IncorrectRequestData
     */
    private AuthToken getToken(Map<String, Object> data) throws IncorrectRequestData {
        try {
            return (AuthToken) data.get("token");
        } catch (ClassCastException e) {
            throw new IncorrectRequestData();
        }
    }
}
