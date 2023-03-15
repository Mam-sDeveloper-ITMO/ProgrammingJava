package commands;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class Messages {
    /**
     * Resources of commands.requirements subpackage
     */
    public static class Requirements {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
                "commands.requirements.exceptions");

        public static final String VALIDATION_ERROR = EXCEPTIONS_BUNDLE.getString("ValidationError");
    }
}
