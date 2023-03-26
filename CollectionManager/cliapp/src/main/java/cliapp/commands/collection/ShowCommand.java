package cliapp.commands.collection;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;

/**
 * That command shows information about collection.
 */
public class ShowCommand extends CollectionCommand {
    public ShowCommand(CollectionManager collectionManager) {
        super(Messages.ShowCommand.NAME, Messages.ShowCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            output.putString(Messages.ShowCommand.EMPTY);
        } else {
            output.putString(Messages.ShowCommand.LIST);
            humans.forEach((human) -> output.putString(human.toString()));
        }
    }
}
