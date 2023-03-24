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
import models.Human;

/**
 * That command updates element in collection.
 */
public class UpdateElementCommand extends ElementCommand {
    public UpdateElementCommand(CollectionManager collectionManager) {
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
            // get new values of human fields from user
            Human human = askHuman(pipeline, output);
            // create same human but with specified id
            human = Human.builder()
                    .id(id)
                    .name(human.getName())
                    .coordinates(human.getCoordinates())
                    .realHero(human.getRealHero())
                    .hasToothpick(human.getHasToothpick())
                    .impactSpeed(human.getImpactSpeed())
                    .soundtrackName(human.getSoundtrackName())
                    .minutesOfWaiting(human.getMinutesOfWaiting())
                    .mood(human.getMood())
                    .car(human.getCar())
                    .build();
            // try to update element in collection
            try {
                collectionManager.update(human);
                output.putString(Messages.UpdateElementCommand.SUCCESS);
            } catch (ElementNotExistsError e) {
                throw new ExecutionError(e.getMessage());
            }
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
