package auth.builtin;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthProvider;
import auth.AuthToken;
import auth.exceptions.LoginFailed;
import auth.exceptions.LogoutFailed;
import auth.exceptions.RegisterFailed;
import auth.exceptions.VerifyFailed;
import server.responses.Response;
import server.utils.StatusCodes;

/**
 * Provides communication with auth service.
 * Cache auth tokens in memory.
 */
public class MemCachedProvider implements AuthProvider {
    private final Adapter serviceAdapter;

    private final Map<String, AuthToken> tokenCache = new HashMap<>();

    public MemCachedProvider(String host, Integer port) {
        this.serviceAdapter = new Adapter(host, port);
    }

    @Override
    public AuthToken login(String login, String password) throws LoginFailed {
        if (tokenCache.containsKey(login)) {
            AuthToken token = tokenCache.get(login);
            if (checkToken(token)) {
                return token;
            }
            throw new LoginFailed("Token expired");
        }

        Response response;
        try {
            Map<String, Serializable> data = new HashMap<>();
            data.put("login", login);
            data.put("password", password);
            response = this.serviceAdapter.triggerServer("login", data);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            throw new LoginFailed("Cannot send request to auth service: %s".formatted(e.getMessage()));
        }

        if (response.getCode() == StatusCodes.OK) {
            AuthToken token = (AuthToken) response.getData();
            tokenCache.put(login, token);
            return token;
        } else {
            throw new LoginFailed(response.getMessage());
        }
    }

    @Override
    public AuthToken register(String login, String password) throws RegisterFailed {
        if (tokenCache.containsKey(login)) {
            throw new RegisterFailed("User already registered");
        }

        Response response;
        try {
            Map<String, Serializable> data = new HashMap<>();
            data.put("login", login);
            data.put("password", password);
            response = this.serviceAdapter.triggerServer("register", data);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            throw new RegisterFailed("Cannot send request to auth service: %s".formatted(e.getMessage()));
        }

        if (response.getCode() == StatusCodes.OK) {
            AuthToken token = (AuthToken) response.getData();
            tokenCache.put(login, token);
            return token;
        } else {
            throw new RegisterFailed(response.getMessage());
        }
    }

    @Override
    public void logout(AuthToken token) throws LogoutFailed {
        if (!tokenCache.containsValue(token)) {
            throw new LogoutFailed("User not logged in");
        }
        tokenCache.forEach((login, cachedToken) -> {
            if (cachedToken.equals(token)) {
                tokenCache.remove(login);
            }
        });
    }

    @Override
    public boolean verify(AuthToken token) throws VerifyFailed {
        if (tokenCache.containsValue(token)) {
            return checkToken(token);
        }

        Response response;
        try {
            Map<String, Serializable> data = new HashMap<>();
            data.put("token", token);
            response = this.serviceAdapter.triggerServer("verify", data);
            return response.getCode() == StatusCodes.OK;
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            throw new VerifyFailed("Cannot send request to auth service: %s".formatted(e.getMessage()));
        }
    }

    private Boolean checkToken(AuthToken token) {
        LocalDateTime now = LocalDateTime.now();
        Boolean expired = token.getExpiresAt().isAfter(now);
        if (expired) {
            tokenCache.forEach((login, cachedToken) -> {
                if (cachedToken.equals(token)) {
                    tokenCache.remove(login);
                }
            });
        }
        return !expired;
    }
}
