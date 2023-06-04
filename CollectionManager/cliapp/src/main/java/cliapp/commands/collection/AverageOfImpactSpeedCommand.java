package cliapp.commands.collection;

import cliapp.TextsManager;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.EmptyCollectionError;
import textlocale.TextSupplier;

/**
 * Command that calculates the average of impact speeds of all humans in the
 * collection.
 */
public class AverageOfImpactSpeedCommand extends CollectionCommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.collection")::getText;

    /**
     * Constructor for AverageOfImpactSpeedCommand.
     *
     * @param collectionManager instance of CollectionManager class.
     */
    public AverageOfImpactSpeedCommand(CollectionManager collectionManager) {
        super(ts.t("AverageOfImpactSpeedCommand.Name"),
                ts.t("AverageOfImpactSpeedCommand.Description"),
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
        output.putString(ts.t("AverageOfImpactSpeedCommand.Title", averageSpeed));
    }
}
