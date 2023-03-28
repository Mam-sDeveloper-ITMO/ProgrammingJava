package cliapp.commands.collection;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cliapp.TextResources.Commands.Collection.SaveCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.CollectionSaveError;;

/**
 * Save collection to file
 */
public class SaveCommand extends CollectionCommand {
    public SaveCommand(CollectionManager collectionManager) {
        super(SaveCommandResources.NAME, SaveCommandResources.DESCRIPTION, collectionManager);
    }

    /**
     * TODO: ask new file path on error
     */
    private static class FileNameRequirement extends Requirement<String, String> {
        public FileNameRequirement() {
            super(
                    SaveCommandResources.FileNameRequirement.NAME,
                    SaveCommandResources.FileNameRequirement.DESCRIPTION,
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
            output.putString(SaveCommandResources.SUCCESS);
        } catch (CollectionSaveError e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw new ExecutionError(SaveCommandResources.FILE_NOT_FOUND);
            } else {
                throw new ExecutionError(SaveCommandResources.SAVE_ERROR);
            }
        }
    }
}
