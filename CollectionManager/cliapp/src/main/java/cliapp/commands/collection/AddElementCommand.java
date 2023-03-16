package cliapp.commands.collection;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import models.Human;

public class AddElementCommand extends ElementCommand {
    public AddElementCommand(CollectionManager collectionManager) {
        super(Messages.AddElementCommand.NAME, Messages.AddElementCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Human human = askHuman(pipeline, output);
            try {
                collectionManager.add(human);
                output.putString(Messages.AddElementCommand.SUCCESS);
            } catch (ElementAlreadyExistsError e) {
                throw new ExecutionError(Messages.AddElementCommand.ERROR, e);
            }
        } catch (RequirementAskError e) {
            throw new ExecutionError(Messages.AddElementCommand.ERROR, e);
        }
    }
}
