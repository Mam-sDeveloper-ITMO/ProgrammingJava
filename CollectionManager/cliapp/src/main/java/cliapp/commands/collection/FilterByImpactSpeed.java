package cliapp.commands.collection;

import java.util.List;

import cliapp.Messages;
import cliapp.commands.collection.requirements.ImpactSpeedRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;

public class FilterByImpactSpeed extends CollectionCommand {
    public FilterByImpactSpeed(CollectionManager collectionManager) {
        super(Messages.FilterByImpactSpeed.NAME, Messages.FilterByImpactSpeed.DESCRIPTION, collectionManager);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ImpactSpeedRequirement());
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Double impactSpeed;
        try {
            impactSpeed = pipeline.askRequirement(new ImpactSpeedRequirement());
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        HumanDeque humans = collectionManager.getCollection();
        humans = humans.stream()
                .filter(human -> human.getImpactSpeed() > impactSpeed)
                .collect(HumanDeque::new, HumanDeque::add, HumanDeque::addAll);

        if (humans.isEmpty()) {
            output.putString(Messages.FilterByImpactSpeed.EMPTY);
        } else {
            output.putString(Messages.FilterByImpactSpeed.TITLE);
            humans.forEach((human) -> output.putString(human.toString()));
        }
    }
}
