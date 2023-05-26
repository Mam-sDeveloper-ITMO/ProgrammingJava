package collections.service.api;

/**
 * Status codes for service responses.
 */
public class StatusCodes {
    // unexpected manipulation error
    public static final int UNEXPECTED_MANIPULATION_ERROR = 420;
    // element already exists
    public static final int ELEMENT_ALREADY_EXISTS = 421;
    // element not exists
    public static final int ELEMENT_NOT_EXISTS = 422;
    // collection is empty
    public static final int COLLECTION_IS_EMPTY = 423;
    // cannot save collection
    public static final int CANNOT_SAVE_COLLECTION = 424;
    // cannot load collection
    public static final int CANNOT_LOAD_COLLECTION = 425;
}
