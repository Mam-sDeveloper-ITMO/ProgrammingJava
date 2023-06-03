package cliapp.commands.collection;

import static textlocale.TextLocale.t;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;

/**
 * This command removes the first element from the collection.
 */
public class RemoveFirstCommand extends CollectionCommand {
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super(t("commands.collection.commands.RemoveFirstCommand.Name"),
                t("commands.collection.commands.RemoveFirstCommand.Description"),
                collectionManager);
    }

    /**
     * Executes the command to remove the first element from the collection.
     *
     * @param pipeline The pipeline to use for gathering requirements.
     * @param output   The output channel to use for displaying messages.
     * @throws ExecutionError If there is an error executing the command.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeFirst();
            output.putString(t("commands.collection.commands.RemoveFirstCommand.Success"));
        } catch (EmptyCollectionError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
