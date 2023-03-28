package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.ShowCommandResources;
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
        super(ShowCommandResources.NAME, ShowCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            output.putString(ShowCommandResources.EMPTY);
        } else {
            output.putString(ShowCommandResources.TITLE);
            humans.forEach((human) -> output.putString(human.toString()));
        }
    }
}
