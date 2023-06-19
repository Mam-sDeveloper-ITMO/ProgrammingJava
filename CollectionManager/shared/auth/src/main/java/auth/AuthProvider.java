package auth;

import auth.exceptions.LoginFailed;
import auth.exceptions.LogoutFailed;
import auth.exceptions.RegisterFailed;
import auth.exceptions.VerifyFailed;

/**
 * Adapter provides communication with server
 * and utils for sending triggers requests to server
 *
 * Adapter use DatagramSocket for sending and receiving data.
 */
public interface AuthProvider {
    /**
     * Check credentials and return token on success.
     * Token is valid for 1 hour.
     *
     * @param login    user login
     * @param password user password
     * @return token for user
     *
     * @see AuthToken
     */
    AuthToken login(String login, String password) throws LoginFailed;

    /**
     * Check that user with this login is not registered
     * and register new user with this login and password.
     * Return token on success.
     * Token is valid for 1 hour.
     *
     *
     * @param login    user login
     * @param password user password
     * @return token for user
     *
     * @see AuthToken
     */
    AuthToken register(String login, String password) throws RegisterFailed;

    /**
     * Revoke token and logout user.
     *
     * @param token user token
     *
     * @see AuthToken
     */
    void logout(AuthToken token) throws LogoutFailed;

    /**
     * Check that token is valid and not expired.
     *
     * @param token user token
     * @return true if token is valid
     *
     * @see AuthToken
     */
    boolean verify(AuthToken token) throws VerifyFailed;
}
