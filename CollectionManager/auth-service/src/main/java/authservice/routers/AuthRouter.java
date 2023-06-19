package authservice.routers;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

import auth.AuthToken;
import authservice.api.StatusCodes;
import authservice.dbmodels.AccountModel;
import authservice.utils.Encrypt;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.connection.ConnectionManager;
import fqme.view.View;
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

public class AuthRouter extends Router {
    private static Logger logger = Logger.getLogger("mainLogger");

    private final Encrypt encrypt;

    /**
     * Create a new auth router.
     */
    public AuthRouter() {
        super("auth");
        try {
            encrypt = new Encrypt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @InnerMiddleware("")
    public Response handleRequest(HandlerFunction handler, Request request) throws IncorrectRequestData {
        logger.log("Request", request.toString(), Level.DEBUG);
        // prepare request data
        Map<String, Object> data = DataConverter.serializableToObjects(request.getData());

        try {
            Connection connection = ConnectionManager.getConnection(AccountModel.class);
            data.put("connection", connection);
            return handler.handle(data);
        } catch (SQLException e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @OuterMiddleware("")
    public Response handleResponse(Request request, Response response) {
        logger.log("Response", response.toString(), Level.DEBUG);
        return response;
    }

    @Handler("register")
    public Response register(Map<String, Object> data) throws IncorrectRequestData {
        String login = getLogin(data);
        String password = getPassword(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<AccountModel> view = View.of(AccountModel.class, connection);

            Set<AccountModel> accounts = view.get(AccountModel.login_.eq(login));
            if (accounts.size() > 0) {
                return Response.failure("Account with this login already exists", StatusCodes.LOGIN_ALREADY_EXISTS);
            }

            String salt = encrypt.generateSalt();
            password = encrypt.hash(password, salt);

            AccountModel account = new AccountModel(null, login, password, salt, null, null);
            account = view.put(account).iterator().next();

            String tokenString = generateToken();
            AuthToken token = new AuthToken(account.getId(), tokenString, LocalDateTime.now().plusHours(3));

            account.setToken(token.getToken());
            account.setTokenExpireAt(token.getExpiresAt());
            view.put(account);

            connection.commit();

            return Response.success(Map.of("token", token));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("login")
    public Response login(Map<String, Object> data) throws IncorrectRequestData {
        String login = getLogin(data);
        String password = getPassword(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<AccountModel> view = View.of(AccountModel.class, connection);

            Set<AccountModel> accounts = view.get(AccountModel.login_.eq(login));
            if (accounts.size() == 0) {
                return Response.failure("Account with this login does not exist",
                        StatusCodes.INCORRECT_LOGIN_OR_PASSWORD);
            }

            AccountModel account = accounts.iterator().next();

            password = encrypt.hash(password, account.getSalt());
            if (!account.getPassword().equals(password)) {
                return Response.failure("Incorrect password", StatusCodes.INCORRECT_LOGIN_OR_PASSWORD);
            }

            String tokenString = generateToken();
            AuthToken token = new AuthToken(account.getId(), tokenString, LocalDateTime.now().plusHours(3));
            account.setToken(token.getToken());
            account.setTokenExpireAt(token.getExpiresAt());

            view.put(account);
            connection.commit();

            return Response.success(Map.of("token", token));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("logout")
    public Response logout(Map<String, Object> data) throws IncorrectRequestData {
        AuthToken token = getToken(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<AccountModel> view = View.of(AccountModel.class, connection);

            Set<AccountModel> accounts = view.get(AccountModel.id_.eq(token.getUserId()));
            if (accounts.size() == 0) {
                return Response.failure("Account with this login does not exist", StatusCodes.INCORRECT_LOGIN_OR_PASSWORD);
            }

            AccountModel account = accounts.iterator().next();
            account.setToken(null);
            account.setTokenExpireAt(null);

            view.put(account);
            connection.commit();

            return Response.success(Map.of("message", "Account logged out"));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    @Handler("verify")
    public Response verify(Map<String, Object> data) throws IncorrectRequestData {
        AuthToken token = getToken(data);
        Connection connection = (Connection) data.get("connection");
        try {
            connection.setAutoCommit(false);
            View<AccountModel> view = View.of(AccountModel.class, connection);

            Set<AccountModel> accounts = view.get(AccountModel.id_.eq(token.getUserId()));
            if (accounts.size() == 0) {
                return Response.failure("Account with this login does not exist", StatusCodes.INCORRECT_LOGIN_OR_PASSWORD);
            }

            AccountModel account = accounts.iterator().next();
            if (account.getToken() == null || account.getTokenExpireAt() == null) {
                return Response.failure("Account is not logged in", 400);
            }

            if (!account.getToken().equals(token.getToken())) {
                return Response.failure("Incorrect token", 400);
            }

            if (account.getTokenExpireAt().isBefore(LocalDateTime.now())) {
                return Response.failure("Token expired", 400);
            }

            return Response.success(Map.of("message", "Token is valid"));
        } catch (SQLException | UnsupportedValueType | UnsupportedSqlType e) {
            return Response.failure("Database error: %s".formatted(e.getMessage()), 400);
        }
    }

    private String getLogin(Map<String, Object> data) throws IncorrectRequestData {
        String login = (String) data.get("login");
        if (login == null) {
            throw new IncorrectRequestData();
        }
        return login;
    }

    private String getPassword(Map<String, Object> data) throws IncorrectRequestData {
        String login = (String) data.get("password");
        if (login == null) {
            throw new IncorrectRequestData();
        }
        return login;
    }

    private AuthToken getToken(Map<String, Object> data) throws IncorrectRequestData {
        AuthToken token = (AuthToken) data.get("token");
        if (token == null) {
            throw new IncorrectRequestData();
        }
        return token;
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String randomString = Base64.getEncoder().encodeToString(randomBytes);
        return randomString;
    }
}