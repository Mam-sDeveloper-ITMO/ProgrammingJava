package cliapp.commands.collection;

import java.util.List;

import cliapp.Messages;
import cliapp.commands.collection.requirements.IdRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;
import models.Human;

/**
 * That command updates element in collection.
 */
public class UpdateElementCommand extends ElementCommand {
    public UpdateElementCommand(CollectionManager collectionManager) {
        super(Messages.UpdateElementCommand.NAME, Messages.UpdateElementCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public List<Requirement<?>> getStaticRequirements() {
        return List.of(new IdRequirement(collectionManager));
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Long id;

        try {
            id = pipeline.askRequirement(new IdRequirement(collectionManager));
        } catch (RequirementAskError e) {
            throw new ExecutionError(Messages.UpdateElementCommand.ERROR, e);
        }

        try {
            Human human = askHuman(pipeline, output);
            human.setId(id);
            try {
                collectionManager.update(human);
                output.putString(Messages.UpdateElementCommand.SUCCESS);
            } catch (ElementNotExistsError e) {
                throw new ExecutionError(Messages.UpdateElementCommand.ERROR, e);
            }
        } catch (RequirementAskError e) {
            throw new ExecutionError(Messages.UpdateElementCommand.ERROR, e);
        }
    }
}
