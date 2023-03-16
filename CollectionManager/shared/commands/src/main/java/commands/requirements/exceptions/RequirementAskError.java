package commands.requirements.exceptions;

import static commands.Messages.Requirements.REQUIREMENT_ASK_ERROR;
import static commands.Messages.Requirements.REQUIREMENT_ASK_ERROR_WITH_MESSAGE;

public class RequirementAskError extends Exception {
    public RequirementAskError() {
        super(REQUIREMENT_ASK_ERROR);
    }

    public RequirementAskError(String message) {
        super(REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(message));
    }

    public RequirementAskError(Exception cause) {
        super(REQUIREMENT_ASK_ERROR_WITH_MESSAGE.formatted(cause.toString()));
    }
}
