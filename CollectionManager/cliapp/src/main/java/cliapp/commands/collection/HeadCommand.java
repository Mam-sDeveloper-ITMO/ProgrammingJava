package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.HeadCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;;

public class HeadCommand extends CollectionCommand {
    public HeadCommand(CollectionManager collectionManager) {
        super(HeadCommandResources.NAME, HeadCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            Exception cause = new EmptyCollectionError();
            throw new ExecutionError(cause.getMessage(), cause);
        } else {
            output.putString(HeadCommandResources.TITLE);
            output.putString(humans.getFirst().toString());
        }
    }
}
