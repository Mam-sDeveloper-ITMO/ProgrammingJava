package cliapp.commands.collection;

import static textlocale.TextLocale.t;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

/**
 * A command that displays the last element of the collection.
 */
public class TailCommand extends CollectionCommand {

    /**
     * Constructs a TailCommand object with a collection manager.
     *
     * @param collectionManager the collection manager to be used by this command.
     */
    public TailCommand(CollectionManager collectionManager) {
        super(t("commands.collection.commands.TailCommand.Name"),
                t("commands.collection.commands.TailCommand.Description"),
                collectionManager);
    }

    /**
     * Displays the last element of the collection to the output channel.
     *
     * @param pipeline the pipeline of requirements to be used by this command.
     * @param output   the output channel where messages will be displayed.
     * @throws ExecutionError if the collection is empty.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            Exception cause = new EmptyCollectionError();
            throw new ExecutionError(cause.getMessage(), cause);
        } else {
            output.putString(t("commands.collection.commands.TailCommand.Title"));
            output.putString(humans.getLast().toString());
        }
    }
}