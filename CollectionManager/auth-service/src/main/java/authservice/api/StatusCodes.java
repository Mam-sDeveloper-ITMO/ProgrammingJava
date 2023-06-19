package authservice.api;

/**
 * Status codes for service responses.
 */
public class StatusCodes {
    // when try to register with existing login
    public static final int LOGIN_ALREADY_EXISTS = 901;

    // when try to login with incorrect login or password
    public static final int INCORRECT_LOGIN_OR_PASSWORD = 902;

    // when try to access to protected resource without token
    public static final int TOKEN_NOT_PROVIDED = 903;

    // when try to access to protected resource with incorrect token
    public static final int INCORRECT_TOKEN = 904;
}
