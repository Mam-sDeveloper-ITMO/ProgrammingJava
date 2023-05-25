package cliapp.commands.collection;

import static textlocale.TextLocale._;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;

/**
 * Command that calculates the average of impact speeds of all humans in the
 * collection.
 */
public class AverageOfImpactSpeedCommand extends CollectionCommand {

    /**
     * Constructor for AverageOfImpactSpeedCommand.
     *
     * @param collectionManager instance of CollectionManager class.
     */
    public AverageOfImpactSpeedCommand(CollectionManager collectionManager) {
        super(_("commands.collection.commands.AverageOfImpactSpeedCommand.Name"),
                _("commands.collection.commands.AverageOfImpactSpeedCommand.Description"),
                collectionManager);
    }

    /**
     * Calculate the average impact speed of humans in the collection.
     *
     * @param pipeline RequirementsPipeline instance.
     * @param output   OutputChannel instance.
     * @throws ExecutionError when there is an error while executing.
     */
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
        output.putString(_("commands.collection.commands.AverageOfImpactSpeedCommand.Title").formatted(averageSpeed));
    }
}
