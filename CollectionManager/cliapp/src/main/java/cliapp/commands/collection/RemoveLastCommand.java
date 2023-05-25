package cliapp.commands.collection;

import static textlocale.TextLocale._;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;

/**
 * This command removes the last element from the collection.
 */
public class RemoveLastCommand extends CollectionCommand {
    public RemoveLastCommand(CollectionManager collectionManager) {
        super(_("commands.collection.commands.RemoveLastCommand.Name"),
                _("commands.collection.commands.RemoveLastCommand.Description"),
                collectionManager);
    }

    /**
     * Executes the command to remove the last element from the collection.
     *
     * @param pipeline The pipeline to use for gathering requirements.
     * @param output   The output channel to use for displaying messages.
     * @throws ExecutionError If there is an error executing the command.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeLast();
            output.putString(_("commands.collection.commands.RemoveLastCommand.Success"));
        } catch (EmptyCollectionError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}