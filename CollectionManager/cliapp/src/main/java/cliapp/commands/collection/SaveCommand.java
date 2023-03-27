package cliapp.commands.collection;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionSaveError;

/**
 * Save collection to file
 */
public class SaveCommand extends CollectionCommand {
    public SaveCommand(CollectionManager collectionManager) {
        super(Messages.SaveCommand.NAME, Messages.SaveCommand.DESCRIPTION, collectionManager);
    }

    /**
     * TODO: ask new file path on error
     */
    private static class FileNameRequirement extends Requirement<String, String> {
        public FileNameRequirement() {
            super(
                    Messages.SaveCommand.FILE_NAME_REQUIREMENT_NAME,
                    Messages.SaveCommand.FILE_NAME_REQUIREMENT_DESCRIPTION,
                    filePathValidator);
        }

        private static final Validator<String, String> filePathValidator = new Validator<String, String>() {
            @Override
            public String validate(String value) throws ValidationError {
                try {
                    Path path = Paths.get(value);
                    return path.toAbsolutePath().toString();
                } catch (InvalidPathException e) {
                    throw new ValidationError(e.getMessage(), value);
                }
            }
        };
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
