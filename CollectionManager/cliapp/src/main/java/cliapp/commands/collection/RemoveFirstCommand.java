package cliapp.commands.collection;

import cliapp.TextsManager;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import humandeque.manager.exceptions.ManipulationError;
import textlocale.text.TextSupplier;

/**
 * This command removes the first element from the collection.
 */
public class RemoveFirstCommand extends CollectionCommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.collection")::getText;

    public RemoveFirstCommand(CollectionManager collectionManager) {
        super(ts.t("RemoveFirstCommand.Name"),
                ts.t("RemoveFirstCommand.Description"),
                collectionManager);
    }

    /**
     * Executes the command to remove the first element from the collection.
     *
     * @param pipeline The pipeline to use for gathering requirements.
     * @param output   The output channel to use for displaying messages.
     * @throws ExecutionError If there is an error executing the command.
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.removeFirst();
            output.putString(ts.t("RemoveFirstCommand.Success"));
        } catch (EmptyCollectionError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
