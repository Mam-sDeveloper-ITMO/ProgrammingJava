package cliapp.commands.collection;

import java.io.FileNotFoundException;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionSaveError;

/**
 * Save collection to file
 */
public class SaveCommand extends CollectionCommand {
    public SaveCommand(CollectionManager collectionManager) {
        super(Messages.SaveCommand.NAME, Messages.SaveCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            collectionManager.save();
            output.putString(Messages.SaveCommand.SUCCESS);
        } catch (CollectionSaveError e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw new ExecutionError(Messages.SaveCommand.FILE_NOT_FOUND);
            } else {
                throw new ExecutionError(Messages.SaveCommand.ERROR);
            }
        }
    }
}
