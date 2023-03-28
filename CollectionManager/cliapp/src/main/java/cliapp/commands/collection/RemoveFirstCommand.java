package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.RemoveFirstCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

/**
 * That command remove all elements collection.
 */
public class RemoveFirstCommand extends CollectionCommand {
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super(RemoveFirstCommandResources.NAME, RemoveFirstCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeFirst();
            output.putString(RemoveFirstCommandResources.SUCCESS);
        } catch (EmptyCollectionError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
