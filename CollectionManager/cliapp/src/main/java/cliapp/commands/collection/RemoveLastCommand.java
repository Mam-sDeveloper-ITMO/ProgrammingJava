package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.RemoveLastCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;;

/**
 * That command remove all elements collection.
 */
public class RemoveLastCommand extends CollectionCommand {
    public RemoveLastCommand(CollectionManager collectionManager) {
        super(RemoveLastCommandResources.NAME, RemoveLastCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeFirst();
            output.putString(RemoveLastCommandResources.SUCCESS);
        } catch (EmptyCollectionError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
