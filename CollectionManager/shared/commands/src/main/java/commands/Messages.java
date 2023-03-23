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
        public static final String REQUIREMENT_ASK_ERROR_WITH_MESSAGE = EXCEPTIONS_BUNDLE
                .getString("RequirementAskErrorWithMessage");
        public static final String REQUIREMENT_ASK_ERROR_WITH_CAUSE = EXCEPTIONS_BUNDLE
                .getString("RequirementAskErrorWithCause");
        public static final String REQUIREMENT_ASK_ERROR_WITH_MESSAGE_AND_CAUSE = EXCEPTIONS_BUNDLE
                .getString("RequirementAskErrorWithMessageAndCause");
    }

    /**
     * Resources of commands.requirements.validators.common.StringValidators
     */
    public static class StringValidatorsMessages {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
                "commands.requirements.validators.common.string");

        public static final String BOOLEAN_ERROR = EXCEPTIONS_BUNDLE.getString("BooleanError");
        public static final String DOUBLE_ERROR = EXCEPTIONS_BUNDLE.getString("DoubleError");
        public static final String FLOAT_ERROR = EXCEPTIONS_BUNDLE.getString("FloatError");
        public static final String INTEGER_ERROR = EXCEPTIONS_BUNDLE.getString("IntegerError");
        public static final String DATETIME_ERROR = EXCEPTIONS_BUNDLE.getString("DateTimeError");
        public static final String LONG_ERROR = EXCEPTIONS_BUNDLE.getString("LongError");
        public static final String EMPTY_ERROR = EXCEPTIONS_BUNDLE.getString("EmptyError");
    }
    
    /**
     * Resources of commands.requirements.validators.common.Misc
     */
    public static class MiscValidatorsMessages {
        private static final ResourceBundle EXCEPTIONS_BUNDLE = ResourceBundle.getBundle(
            "commands.requirements.validators.common.misc");
            
        public static final String NULL_ERROR = EXCEPTIONS_BUNDLE.getString("NullError");
        public static final String GREATER_ERROR = EXCEPTIONS_BUNDLE.getString("GreaterError");
        public static final String LOWER_ERROR = EXCEPTIONS_BUNDLE.getString("LowerError");
    }
}
