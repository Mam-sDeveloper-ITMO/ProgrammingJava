package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.TailCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

public class TailCommand extends CollectionCommand {
    public TailCommand(CollectionManager collectionManager) {
        super(TailCommandResources.NAME, TailCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            Exception cause = new EmptyCollectionError();
            throw new ExecutionError(cause.getMessage(), cause);
        } else {
            output.putString(TailCommandResources.TITLE);
            output.putString(humans.getLast().toString());
        }
    }
}
