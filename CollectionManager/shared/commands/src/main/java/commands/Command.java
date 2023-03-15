package commands;

import java.util.List;

import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import lombok.Data;

/**
 * Represent abstract command.
 * It encapsulate action and requirements for action execution.
 * 
 * Consists static and dynamic requirements.
 * 
 * Static requirements are specified on command creation.
 * 
 * Dynamic requirements are passed during command execution.
 */
@Data
public abstract class Command {
    private final String name;

    private final String description;

    /**
     * Can be utilized for dynamic supplying of requirements.
     */
    private final RequirementsPipeline pipeline;

    /**
     * Can be utilized for passing messages from command toe external source.
     */
    private final OutputChannel output;

    /**
     * Can be utilized for validating params for command constructor.
     */
    public List<Requirement<?>> getStaticRequirements() {
        return List.of();
    };

    /**
     * Execute command action.
     */
    abstract public void execute() throws ExecutionError;
}
