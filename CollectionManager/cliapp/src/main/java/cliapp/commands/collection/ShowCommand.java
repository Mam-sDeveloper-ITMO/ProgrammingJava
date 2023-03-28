package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.ShowCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;

/**
 * A command that displays information about the collection.
 */
public class ShowCommand extends CollectionCommand {

    /**
     * Constructs a ShowCommand object with a collection manager.
     * 
     * @param collectionManager the collection manager to be used by this command.
     */
    public ShowCommand(CollectionManager collectionManager) {
        super(ShowCommandResources.NAME, ShowCommandResources.DESCRIPTION, collectionManager);
    }

    /**
     * Displays the contents of the collection to the output channel.
     * 
     * @param pipeline the pipeline of requirements to be used by this command.
     * @param output   the output channel where messages will be displayed.
     * @throws ExecutionError if there was an error while displaying the collection.
     */
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