package cliapp.commands.collection;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import textlocale.TextLocale;
import textlocale.TextSupplier;

/**
 * This command returns the first element of the collection.
 */
public class HeadCommand extends CollectionCommand {
    static TextSupplier ts = TextLocale.getPackage("commands.collection")::getText;

    /**
     * Constructs a new HeadCommand with the given collection manager.
     *
     * @param collectionManager the collection manager to be used by this command
     */
    public HeadCommand(CollectionManager collectionManager) {
        super(ts.t("HeadCommand.Name"),
                ts.t("HeadCommand.Description"),
                collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            Exception cause = new EmptyCollectionError();
            throw new ExecutionError(cause.getMessage(), cause);
        } else {
            output.putString(ts.t("HeadCommand.Title"));
            output.putString(humans.getFirst().toString());
        }
    }
}
