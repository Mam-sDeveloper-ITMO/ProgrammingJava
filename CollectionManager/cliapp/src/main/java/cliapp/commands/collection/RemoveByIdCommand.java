package cliapp.commands.collection;

import java.util.List;

import cliapp.Messages;
import cliapp.commands.collection.requirements.ExistingIdRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;

/**
 * That command removes element from collection by id.
 */
public class RemoveByIdCommand extends ElementCommand {
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super(Messages.UpdateElementCommand.NAME, Messages.UpdateElementCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ExistingIdRequirement(collectionManager));
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Long id;

        try {
            id = pipeline.askRequirement(new ExistingIdRequirement(collectionManager));
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        try {
            collectionManager.remove(id);
            output.putString(Messages.RemoveByIdCommand.SUCCESS);
        } catch (ElementNotExistsError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
