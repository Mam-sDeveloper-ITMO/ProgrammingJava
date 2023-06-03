package cliapp.commands.collection;

import static textlocale.TextLocale.t;

import java.util.List;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;

/**
 * A command that shows humans whose impact speed is greater than the specified
 * value.
 */
public class FilterByImpactSpeed extends CollectionCommand {
    /**
     * Constructs a new FilterByImpactSpeed command with the given collection
     * manager.
     *
     * @param collectionManager the collection manager
     */
    public FilterByImpactSpeed(CollectionManager collectionManager) {
        super(t("commands.collection.commands.FilterByImpactSpeedCommand.Name"),
                t("commands.collection.commands.FilterByImpactSpeedCommand.Description"),
                collectionManager);
    }

    /**
     * Gets the static requirements of the FilterByImpactSpeed command.
     *
     * @return a list of the static requirements
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(impactSpeedRequirement);
    }

    /**
     * Executes the FilterByImpactSpeed command by asking the user for the impact
     * speed threshold,
     * filtering the humans in the collection by impact speed, and outputting the
     * filtered list of humans.
     *
     * @param pipeline the requirements pipeline
     * @param output   the output channel
     * @throws ExecutionError if there is an error executing the command
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Double impactSpeed;
        try {
            impactSpeed = pipeline.askRequirement(impactSpeedRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        HumanDeque humans = collectionManager.getCollection();
        humans = humans.stream()
                .filter(human -> human.getImpactSpeed() > impactSpeed)
                .collect(HumanDeque::new, HumanDeque::add, HumanDeque::addAll);

        if (humans.isEmpty()) {
            output.putString(t("commands.collection.commands.FilterByImpactSpeedCommand.Empty"));
        } else {
            output.putString(t("commands.collection.commands.FilterByImpactSpeedCommand.Title"));
            humans.forEach((human) -> output.putString(human.toString()));
        }
    }
}
