package commands.requirements.exceptions;

import static commands.Messages.Requirements.REQUIREMENT_ASK_FAILED;

public class RequirementAskFailed extends Exception {
    public RequirementAskFailed() {
        super(REQUIREMENT_ASK_FAILED);
    }
}
