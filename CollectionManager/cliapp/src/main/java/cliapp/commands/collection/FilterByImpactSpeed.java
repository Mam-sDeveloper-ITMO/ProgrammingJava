package cliapp.commands.collection;

import java.util.List;

import cliapp.TextResources.Commands.Collection.FilterByImpactSpeedCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;

/**
 * Show humans whose impact speed is greater than the specified value.
 */
public class FilterByImpactSpeed extends CollectionCommand {
    public FilterByImpactSpeed(CollectionManager collectionManager) {
        super(FilterByImpactSpeedCommandResources.NAME, FilterByImpactSpeedCommandResources.DESCRIPTION,
                collectionManager);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(impactSpeedRequirement);
    }

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
            output.putString(FilterByImpactSpeedCommandResources.EMPTY);
        } else {
            output.putString(FilterByImpactSpeedCommandResources.TITLE);
            humans.forEach((human) -> output.putString(human.toString()));
        }
    }
}
