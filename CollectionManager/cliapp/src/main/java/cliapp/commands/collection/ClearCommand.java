package cliapp.commands.collection;

import static commands.requirements.validators.common.StringValidators.booleanValidator;
import static textlocale.TextLocale.t;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ManipulationError;

/**
 * A command that removes all elements from the collection.
 */
public class ClearCommand extends CollectionCommand {

    /**
     * The requirement that asks the user to confirm that they want to clear the
     * collection.
     */
    private static Requirement<String, Boolean> approveRequirement = new Requirement<>(
            t("commands.collection.commands.ClearCommand.ApproveRequirement.Name"),
            t("commands.collection.commands.ClearCommand.ApproveRequirement.Description"),
            booleanValidator);

    /**
     * Constructs a new ClearCommand with the given collection manager.
     *
     * @param collectionManager the collection manager
     */
    public ClearCommand(CollectionManager collectionManager) {
        super(t("commands.collection.commands.ClearCommand.Name"),
                t("commands.collection.commands.ClearCommand.Description"),
                collectionManager);
    }

    /**
     * Executes the ClearCommand by asking the user to confirm that they want to
     * clear the collection,
     * and then clearing the collection if the user confirms.
     *
     * @param pipeline the requirements pipeline
     * @param output   the output channel
     * @throws ExecutionError if there is an error executing the command
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        // ask the user to confirm that they want to clear the collection
        Boolean approveClear;
        try {
            approveClear = pipeline.askRequirement(approveRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        if (approveClear) {
            // clear the collection and output a success message
            try {
                collectionManager.clear();
            } catch (ManipulationError e) {
                throw new ExecutionError(e.getMessage());
            }
            output.putString(t("commands.collection.commands.ClearCommand.Success"));
        }
    }
}
