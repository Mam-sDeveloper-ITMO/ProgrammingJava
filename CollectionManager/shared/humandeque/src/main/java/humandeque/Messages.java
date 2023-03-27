package humandeque;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class Messages {
    /**
     * Resources of humandeque.manager subpackage
     */
    public static class Manager {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
                "humandeque.manager.exceptions");

        public static final String ELEMENT_NOT_EXISTS = EXCEPTIONS_BUNDLE.getString("ElementNotExistsError");

        public static final String ELEMENT_ALREADY_EXISTS = EXCEPTIONS_BUNDLE.getString("ElementAlreadyExistsError");

        public static final String EMPTY_COLLECTION = EXCEPTIONS_BUNDLE.getString("EmptyCollectionError");

        public static final String COLLECTION_LOAD_ERROR = EXCEPTIONS_BUNDLE.getString("CollectionLoadError");

        public static final String COLLECTION_SAVE_ERROR = EXCEPTIONS_BUNDLE.getString("CollectionSaveError");

        public static final String FILE_PATH_NOT_SPECIFIED = EXCEPTIONS_BUNDLE.getString("FilePathNotSpecified");
    }
}
