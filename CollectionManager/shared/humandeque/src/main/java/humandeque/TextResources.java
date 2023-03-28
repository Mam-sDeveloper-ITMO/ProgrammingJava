package humandeque;

import java.util.ResourceBundle;

/**
 * This class holds all the text resources used in the application, organized
 * into different nested classes for
 * easier access.
 */
public class TextResources {
    public static class Manager {
        public static class ExceptionsResources {
            private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("humandeque.manager.exceptions");

            public static final String ELEMENT_NOT_EXISTS = BUNDLE.getString("ElementNotExistsError");

            public static final String ELEMENT_ALREADY_EXISTS = BUNDLE.getString("ElementAlreadyExistsError");

            public static final String EMPTY_COLLECTION = BUNDLE.getString("EmptyCollectionError");

            public static final String COLLECTION_LOAD_ERROR = BUNDLE.getString("CollectionLoadError");

            public static final String COLLECTION_SAVE_ERROR = BUNDLE.getString("CollectionSaveError");

            public static final String FILE_PATH_NOT_SPECIFIED_ERROR = BUNDLE.getString("FilePathNotSpecifiedError");
        }
    }
}
