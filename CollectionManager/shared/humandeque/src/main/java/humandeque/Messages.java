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

        public static final String ELEMENT_NOT_EXISTS =
            EXCEPTIONS_BUNDLE.getString("ElementNotExistsError");

        public static final String ELEMENT_ALREADY_EXISTS =
            EXCEPTIONS_BUNDLE.getString("ElementAlreadyExistsError");
    }
}
