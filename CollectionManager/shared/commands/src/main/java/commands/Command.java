package commands;

import java.util.List;

import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import lombok.Data;

/**
 * Represent abstract command.
 * 
 * It encapsulate action and requirements for action execution.
 * 
 * Command object acts like dispatcher and you need have only one Command
 * instance for each command type (but different for each thread)
 * 
 * Consists static and dynamic requirements.
 * 
 * Static requirements are requirements that will be necessary asked in begging
 * of command execution.
 * 
 * Dynamic requirements are optionally asked during command execution.
 */
@Data
public abstract class Command {
    private final String name;

    private final String description;

    /**
     * Return list of static requirements.
     * 
     * In CLI can be used for inline params, in GUI can be used for text fields.
     */
    public List<Requirement<?>> getStaticRequirements() {
        return List.of();
    };

    /**
     * Execute command action.
     * 
     * Invoke pipeline and output during execution.
     * 
     * @param pipeline - Supplier of requirements from external source
     * @param output   - Output channel for text messages from command. That
     *                 messages should not be
     *                 ignored and can be useful for user.
     */
    abstract public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError;
}
