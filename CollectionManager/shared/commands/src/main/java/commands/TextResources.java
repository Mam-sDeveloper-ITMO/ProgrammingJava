package commands;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class TextResources {
    /**
     * Resources of root package
     */
    public static class Commands {
        public static class ExceptionsResources {
            private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("commands.exceptions");

            public static final String EXECUTION_ERROR = BUNDLE.getString("ExecutionError.Default");

            public static final String EXECUTION_ERROR_WITH_CAUSE = BUNDLE.getString("ExecutionError.WithCause");
        }
    }

    /**
     * Resources of commands.requirements subpackage
     */
    public static class Requirements {
        public static class ExceptionsResources {
            private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
                    "commands.requirements.exceptions");

            public static final String VALIDATION_ERROR = BUNDLE.getString("ValidationError.Default");

            public static final String REQUIREMENT_ASK_ERROR = BUNDLE.getString("RequirementAskError.Default");

            public static final String REQUIREMENT_ASK_ERROR_WITH_MESSAGE = BUNDLE
                    .getString("RequirementAskError.WithMessage");

            public static final String REQUIREMENT_ASK_ERROR_WITH_CAUSE = BUNDLE
                    .getString("RequirementAskError.WithCause");

            public static final String REQUIREMENT_ASK_ERROR_WITH_MESSAGE_AND_CAUSE = BUNDLE
                    .getString("RequirementAskError.WithMessageAndCause");
        }

        public static class Common {
            /**
             * Resources of commands.requirements.validators.common.StringValidators
             */
            public static class StringValidatorsResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("commands.requirements.validators.common.string");

                public static final String BOOLEAN_VALIDATOR_ERROR = BUNDLE.getString("BooleanValidator.Error");

                public static final String DOUBLE_VALIDATOR_ERROR = BUNDLE.getString("DoubleValidator.Error");

                public static final String FLOAT_VALIDATOR_ERROR = BUNDLE.getString("FloatValidator.Error");

                public static final String INTEGER_VALIDATOR_ERROR = BUNDLE.getString("IntegerValidator.Error");

                public static final String DATETIME_VALIDATOR_ERROR = BUNDLE.getString("DateTimeValidator.Error");

                public static final String LONG_VALIDATOR_ERROR = BUNDLE.getString("LongValidator.Error");

                public static final String NOT_EMPTY_VALIDATOR_ERROR = BUNDLE.getString("NotEmptyValidator.Error");
            }

            /**
             * Resources of commands.requirements.validators.common.Misc
             */
            public static class MiscValidatorsResources {
                private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
                        "commands.requirements.validators.common.misc");

                public static final String NOT_NULL_VALIDATOR_ERROR = BUNDLE.getString("NotNullValidator.Error");

                public static final String GREATER_VALIDATOR_ERROR = BUNDLE.getString("GreaterValidator.Error");

                public static final String LOWER_VALIDATOR_ERROR = BUNDLE.getString("LowerValidator.Error");
            }
        }
    }

}
