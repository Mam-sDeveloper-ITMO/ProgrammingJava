package cliapp.commands.collection;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

/**
 * That command remove all elements collection.
 */
public class RemoveLastCommand extends CollectionCommand {
    public RemoveLastCommand(CollectionManager collectionManager) {
        super(Messages.RemoveLastCommand.NAME, Messages.RemoveLastCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeFirst();
            output.putString(Messages.RemoveLastCommand.SUCCESS);
        } catch (EmptyCollectionError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
