package cliapp.commands.collection;

import static commands.requirements.validators.common.StringValidators.booleanValidator;
import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;

/**
 * That command remove all elements collection.
 */
public class ClearCommand extends CollectionCommand {
    public ClearCommand(CollectionManager collectionManager) {
        super(Messages.ClearCommand.NAME, Messages.ClearCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        // ask that user sure
        Boolean approveClear;
        try {
            approveClear = pipeline.askRequirement(
                new Requirement<>(
                    Messages.ClearCommand.APPROVE,
                    Messages.ClearCommand.APPROVE_DESCRIPTION,
                    booleanValidator));

        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        if (approveClear) {
            collectionManager.clear();
            output.putString(Messages.ClearCommand.SUCCESS);
        }
    }
}
