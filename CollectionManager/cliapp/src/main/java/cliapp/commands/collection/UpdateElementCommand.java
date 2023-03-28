package cliapp.commands.collection;

import java.util.List;

import cliapp.TextResources;
import cliapp.TextResources.Commands.Collection.RequirementsResources.MoodRequirement;
import cliapp.TextResources.Commands.Collection.UpdateElementCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * That command updates element in collection.
 */
public class UpdateElementCommand extends CollectionCommand {
    public UpdateElementCommand(CollectionManager collectionManager) {
        super(UpdateElementCommandResources.NAME, UpdateElementCommandResources.DESCRIPTION,
                collectionManager);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ExistingIdRequirement(collectionManager));
    }

    /**
     * Try to ask requirement with default option, except return default value
     */
    private <I, O> O askOrDefault(RequirementsPipeline pipeline, OutputChannel output,
            Requirement<I, O> requirement,
            O defaultValue) {
        // suggest to skip field
        output.putString(
                TextResources.CLIClientResources.ASK_DEFAULT_REQUIREMENT.formatted(defaultValue));
        try {
            O value = pipeline.askRequirement(requirement);
            return value;
        } catch (RequirementAskError e) {
            return defaultValue;
        }
    }

    /**
     * Ask fields that must be updated. If field is skipped set default value
     */
    private Human askUpdatedHuman(Human defaultHuman, RequirementsPipeline pipeline,
            OutputChannel output)
            throws RequirementAskError {

        Human.HumanBuilder humanBuilder = Human.builder();

        humanBuilder.id(defaultHuman.getId());
        humanBuilder.creationDate(defaultHuman.getCreationDate());

        humanBuilder.name(askOrDefault(pipeline, output, nameRequirement, defaultHuman.getName()));

        Coordinates defaultCoordinates = defaultHuman.getCoordinates();
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(askOrDefault(pipeline, output, coordinateXRequirement, defaultCoordinates.getX()));

        coordinatesBuilder.y(askOrDefault(pipeline, output, coordinateYRequirement, defaultCoordinates.getY()));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(askOrDefault(pipeline, output, realHeroRequirement, defaultHuman.getRealHero()));

        humanBuilder.hasToothpick(
                askOrDefault(pipeline, output, hasToothpickRequirement, defaultHuman.getHasToothpick()));

        humanBuilder.impactSpeed(
                askOrDefault(pipeline, output, impactSpeedRequirement, defaultHuman.getImpactSpeed()));

        humanBuilder.soundtrackName(
                askOrDefault(pipeline, output, soundtrackRequirement, defaultHuman.getSoundtrackName()));

        humanBuilder.minutesOfWaiting(
                askOrDefault(pipeline, output, minutesOfWaitingRequirement, defaultHuman.getMinutesOfWaiting()));

        String moods = "";
        for (int i = 0; i < Mood.values().length; i++) {
            moods += i + " - " + Mood.values()[i] + System.lineSeparator();
        }
        output.putString(MoodRequirement.TITLE + System.lineSeparator() + moods);
        humanBuilder.mood(askOrDefault(pipeline, output, moodRequirement, defaultHuman.getMood()));

        Car car = new Car(askOrDefault(pipeline, output, carNameRequirement, defaultHuman.getCar().getName()));
        humanBuilder.car(car);

        return humanBuilder.build();
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        // ask id requirement
        Long id;
        try {
            id = pipeline.askRequirement(new ExistingIdRequirement(collectionManager));
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
        // get human that will be updated
        Human defaultHuman;
        try {
            defaultHuman = collectionManager.getElementById(id);
        } catch (ElementNotExistsError e) {
            return; // unreachable due to id validation
        }
        // create new human with updated field
        Human human;
        try {
            human = askUpdatedHuman(defaultHuman, pipeline, output);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
        // update human element in collection
        try {
            collectionManager.update(human);
            output.putString(UpdateElementCommandResources.SUCCESS);
        } catch (ElementNotExistsError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
