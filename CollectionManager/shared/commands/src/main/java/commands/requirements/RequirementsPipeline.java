package commands.requirements;

import commands.requirements.exceptions.RequirementAskError;

/**
 * This class is used for supplying requirement values to command from an external source.
 * For example, in a CLI application, you can use this class to ask the user to input a parameter, 
 * or in a UI, it can be used to show input fields or to send a request to a server.
 */
@FunctionalInterface
public interface RequirementsPipeline {
    
    /**
     * Ask for a requirement value from an external source.
     * 
     * @param requirement - the requirement to process.
     * @return the value of the requirement
     * @throws RequirementAskError if there is an error while asking for the requirement
     */
    public <I, O> O askRequirement(Requirement<I, O> requirement) throws RequirementAskError;
}