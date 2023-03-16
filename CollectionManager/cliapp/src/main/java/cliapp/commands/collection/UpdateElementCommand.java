package cliapp.commands.collection;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;
import models.Human;

public class UpdateElementCommand extends ElementCommand {
    public UpdateElementCommand(CollectionManager collectionManager) {
        super(Messages.UpdateElementCommand.NAME, Messages.UpdateElementCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Human human = askHuman(pipeline, output);
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
