package server.utils;

/**
 * Contains common status codes.
 */
public class StatusCodes {
    // Success
    public static final Integer OK = 200;

    // Client errors
    public static final Integer BAD_REQUEST = 400;
    public static final Integer INCORRECT_REQUEST_DATA = 401;
    public static final Integer UNHANDLED = 404;

    // Server errors
    public static final Integer INTERNAL_SERVER_ERROR = 500;
    public static final Integer BAD_REQUEST_STREAM = 501;
    public static final Integer BAD_RESPONSE_STREAM = 502;
}
