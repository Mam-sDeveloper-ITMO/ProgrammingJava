package commands;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class Messages {
    /**
     * Resources of root package
     */
    public static class Commands {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle("commands.exceptions");

        public static final String EXECUTION_ERROR = EXCEPTIONS_BUNDLE.getString("ExecutionError");
    }

    /**
     * Resources of commands.requirements subpackage
     */
    public static class Requirements {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
                "commands.requirements.exceptions");

        public static final String VALIDATION_ERROR = EXCEPTIONS_BUNDLE.getString("ValidationError");
        public static final String REQUIREMENT_ASK_ERROR = EXCEPTIONS_BUNDLE.getString("RequirementAskError");
        public static final String REQUIREMENT_ASK_ERROR_WITH_MESSAGE = EXCEPTIONS_BUNDLE.getString("RequirementAskErrorWithMessage");
    }

    /**
     * Resources of commands.requirements.validators subpackage
     */
    public static class Validators {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
                "commands.requirements.validators");

        public static final String BOOLEAN_ERROR = EXCEPTIONS_BUNDLE.getString("BooleanError");
        public static final String DOUBLE_ERROR = EXCEPTIONS_BUNDLE.getString("DoubleError");
        public static final String FLOAT_ERROR = EXCEPTIONS_BUNDLE.getString("FloatError");
        public static final String INTEGER_ERROR = EXCEPTIONS_BUNDLE.getString("IntegerError");
        public static final String DATETIME_ERROR = EXCEPTIONS_BUNDLE.getString("DateTimeError");
        public static final String LONG_ERROR = EXCEPTIONS_BUNDLE.getString("LongError");
        public static final String NULL_ERROR = EXCEPTIONS_BUNDLE.getString("NullError");
    }
}
