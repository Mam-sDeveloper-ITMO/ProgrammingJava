package cliapp.commands.collection;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import cliapp.TextResources.Commands.Collection.ClearCommandResources;
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
    private static Requirement<String, Boolean> approveRequirement = new Requirement<>(
            ClearCommandResources.ApproveRequirement.NAME,
            ClearCommandResources.ApproveRequirement.DESCRIPTION,
            booleanValidator);

    public ClearCommand(CollectionManager collectionManager) {
        super(ClearCommandResources.NAME, ClearCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        // ask that user sure
        Boolean approveClear;
        try {
            approveClear = pipeline.askRequirement(approveRequirement);

        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        if (approveClear) {
            collectionManager.clear();
            output.putString(ClearCommandResources.SUCCESS);
        }
    }
}
