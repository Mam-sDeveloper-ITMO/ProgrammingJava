package commands;

import java.util.List;

import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import lombok.Data;

/**
 * Represents an abstract command.
 * 
 * It encapsulates the action and requirements for action execution.
 * 
 * The Command object acts like a dispatcher and you need to have only one
 * Command instance for each command
 * type (but different for each thread)
 * 
 * Consists of static and dynamic requirements.
 * 
 * Static requirements are requirements that will be necessary to ask in the
 * beginning of command
 * execution.
 * 
 * Dynamic requirements are optionally asked during command execution.
 */
@Data
public abstract class Command {
    /**
     * The name of the command.
     */
    private final String name;

    /**
     * The description of the command.
     */
    private final String description;

    /**
     * Return a list of static requirements.
     * 
     * In CLI can be used for inline params, in GUI can be used for text fields.
     * 
     * @return A list of static requirements.
     */
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of();
    };

    /**
     * Execute the command action.
     * 
     * Invoke the pipeline and output during execution.
     * 
     * @param pipeline Supplier of requirements from an external source.
     * @param output   Output channel for text messages from command. That messages
     *                 should not be
     *                 ignored and can be useful for the user.
     * @throws ExecutionError If there is an error executing the command.
     */
    abstract public void execute(RequirementsPipeline pipeline, OutputChannel output)
            throws ExecutionError;
}