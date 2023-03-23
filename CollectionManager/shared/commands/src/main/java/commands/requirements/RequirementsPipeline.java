package commands.requirements;

import commands.requirements.exceptions.RequirementAskError;

/**
 * This class used for supplying of requirements values to command from
 * external source.
 * 
 * For example, in CLI application you can use this class for asking user to
 * input param, in UI - to show input field, or send request to server.
 */
@FunctionalInterface
public interface RequirementsPipeline {
    /**
     * Ask for requirement value from external source.
     * 
     * @param requirement - requirement for process.
     * @return
     */
    public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError;
}
