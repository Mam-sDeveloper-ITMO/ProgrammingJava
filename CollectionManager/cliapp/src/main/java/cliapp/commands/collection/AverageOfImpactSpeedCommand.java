package cliapp.commands.collection;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

public class AverageOfImpactSpeedCommand extends CollectionCommand {
    public AverageOfImpactSpeedCommand(CollectionManager collectionManager) {
        super(Messages.AverageOfImpactSpeedCommand.NAME, Messages.AverageOfImpactSpeedCommand.DESCRIPTION,
                collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        HumanDeque humans = collectionManager.getCollection();
        if (humans.isEmpty()) {
            Exception cause = new EmptyCollectionError();
            throw new ExecutionError(cause.getMessage(), cause);
        }
        Double averageSpeed = humans.stream()
                .map(human -> human.getImpactSpeed())
                .reduce(0d, (a, b) -> a + b) / humans.size();
        output.putString(Messages.AverageOfImpactSpeedCommand.TITLE.formatted(averageSpeed));
    }
}
