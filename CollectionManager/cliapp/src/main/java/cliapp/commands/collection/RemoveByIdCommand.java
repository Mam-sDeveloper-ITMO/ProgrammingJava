package cliapp.commands.collection;

import static textlocale.TextLocale.t;

import java.util.List;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.ManipulationError;

/**
 * This command removes an element from the collection by ID.
 */
public class RemoveByIdCommand extends CollectionCommand {
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(t("commands.collection.commands.RemoveByIdCommand.Name"),
                t("commands.collection.commands.RemoveByIdCommand.Description"),
                collectionManager);
    }

    /**
     * Returns a list of requirements for this command.
     *
     * @return A list of requirements.
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ExistingIdRequirement(collectionManager));
    }

    /**
     * Executes the command to remove an element from the collection by ID.
     *
     * @param pipeline The pipeline to use for gathering requirements.
     * @param output   The output channel to use for displaying messages.
     * @throws ExecutionError If there is an error executing the command.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Long id;

        try {
            id = pipeline.askRequirement(new ExistingIdRequirement(collectionManager));
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        try {
            collectionManager.remove(id);
            output.putString(t("commands.collection.commands.RemoveByIdCommand.Success"));
        } catch (ElementNotExistsError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
