package cliapp.commands.collection;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;
import textlocale.TextLocale;
import textlocale.TextSupplier;

/**
 * This command removes the last element from the collection.
 */
public class RemoveLastCommand extends CollectionCommand {
    static TextSupplier ts = TextLocale.getPackage("commands.collection")::getText;

    public RemoveLastCommand(CollectionManager collectionManager) {
        super(ts.t("RemoveLastCommand.Name"),
                ts.t("RemoveLastCommand.Description"),
                collectionManager);
    }

    /**
     * Executes the command to remove the last element from the collection.
     *
     * @param pipeline The pipeline to use for gathering requirements.
     * @param output   The output channel to use for displaying messages.
     * @throws ExecutionError If there is an error executing the command.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeLast();
            output.putString(ts.t("RemoveLastCommand.Success"));
        } catch (EmptyCollectionError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}