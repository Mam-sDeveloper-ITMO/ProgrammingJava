package cliapp.commands.cli.requirements;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cliapp.Messages;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

public class ScriptFileRequirement extends Requirement<String, Path> {
    public ScriptFileRequirement() {
        super(
                Messages.ExecuteCommand.SCRIPT_FILE_REQUIREMENT_NAME,
                Messages.ExecuteCommand.SCRIPT_FILE_REQUIREMENT_DESCRIPTION,
                new ScriptPathValidator());
    }

    /**
     * Validate string as file path and return absolute path
     * 
     * @return true if string is des and false if asc
     */
    private static class ScriptPathValidator implements Validator<String, Path> {
        @Override
        public Path validate(String value) throws ValidationError {
            if (!value.endsWith(".neko")) {
                throw new ValidationError(value, Messages.ExecuteCommand.INCORRECT_FILE);
            }
            try {
                Path path = Paths.get(value);
                return path.toAbsolutePath();
            } catch (InvalidPathException e) {
                throw new ValidationError(value, e.getMessage());
            }
        }
    }
}
