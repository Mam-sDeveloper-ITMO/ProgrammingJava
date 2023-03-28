package cliapp.commands.collection;

import cliapp.TextResources.Commands.Collection.AddElementCommandResources;
import cliapp.TextResources.Commands.Collection.RequirementsResources.MoodRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * That command adds new element to collection.
 */
public class AddElementCommand extends CollectionCommand {
    public AddElementCommand(CollectionManager collectionManager) {
        super(AddElementCommandResources.NAME, AddElementCommandResources.DESCRIPTION,
                collectionManager);
    }

    private Human askHuman(RequirementsPipeline pipeline, OutputChannel output)
            throws RequirementAskError {
        Human.HumanBuilder humanBuilder = Human.builder();

        humanBuilder.name(pipeline.askRequirement(nameRequirement));

        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(pipeline.askRequirement(coordinateXRequirement));
        coordinatesBuilder.y(pipeline.askRequirement(coordinateYRequirement));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(pipeline.askRequirement(realHeroRequirement));

        humanBuilder.hasToothpick(pipeline.askRequirement(hasToothpickRequirement));

        humanBuilder.impactSpeed(pipeline.askRequirement(impactSpeedRequirement));

        humanBuilder.soundtrackName(pipeline.askRequirement(soundtrackRequirement));

        humanBuilder.minutesOfWaiting(pipeline.askRequirement(minutesOfWaitingRequirement));

        String moods = "";
        for (int i = 0; i < Mood.values().length - 1; i++) {
            moods += i + " - " + Mood.values()[i] + System.lineSeparator();
        }
        moods += (Mood.values().length - 1) + " - " + Mood.values()[Mood.values().length - 1];

        output.putString(MoodRequirement.TITLE + System.lineSeparator() + moods);
        humanBuilder.mood(pipeline.askRequirement(moodRequirement));

        Car car = new Car(pipeline.askRequirement(carNameRequirement));
        humanBuilder.car(car);

        return humanBuilder.build();
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Human human = askHuman(pipeline, output);
            try {
                collectionManager.add(human);
                output.putString(AddElementCommandResources.SUCCESS);
            } catch (ElementAlreadyExistsError e) {
                throw new ExecutionError(e.getMessage());
            }
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
