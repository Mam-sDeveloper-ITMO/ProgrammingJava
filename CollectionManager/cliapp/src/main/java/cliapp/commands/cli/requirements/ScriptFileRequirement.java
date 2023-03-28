package cliapp.commands.cli.requirements;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cliapp.TextResources.Commands.Cli.ExecuteCommandResources.ScriptFileRequirementResources;
import commands.requirements.Requirement;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;

public class ScriptFileRequirement extends Requirement<String, Path> {
    public ScriptFileRequirement() {
        super(
                ScriptFileRequirementResources.NAME,
                ScriptFileRequirementResources.DESCRIPTION,
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
                throw new ValidationError(value, ScriptFileRequirementResources.INCORRECT_EXTENSION);
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
