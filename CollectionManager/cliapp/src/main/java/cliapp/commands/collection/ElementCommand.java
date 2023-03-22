package cliapp.commands.collection;

import cliapp.Messages.ElementRequirements;

import cliapp.commands.collection.requirements.CarNameRequirement;
import cliapp.commands.collection.requirements.CoordinatesXRequirement;
import cliapp.commands.collection.requirements.CoordinatesYRequirement;
import cliapp.commands.collection.requirements.HasToothpickRequirement;
import cliapp.commands.collection.requirements.ImpactSpeedRequirement;
import cliapp.commands.collection.requirements.MinutesOfWaitingRequirement;
import cliapp.commands.collection.requirements.MoodRequirement;
import cliapp.commands.collection.requirements.NameRequirement;
import cliapp.commands.collection.requirements.RealHeroRequirement;
import cliapp.commands.collection.requirements.SoundtrackNameRequirement;
import commands.OutputChannel;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * That class used for commands that need to ask user about human.
 */
public abstract class ElementCommand extends CollectionCommand {
    public ElementCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description, collectionManager);
    }

    protected Human askHuman(RequirementsPipeline pipeline, OutputChannel output) throws RequirementAskError {
        Human.HumanBuilder humanBuilder = Human.builder();

        humanBuilder.name(pipeline.askRequirement(new NameRequirement()));

        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(pipeline.askRequirement(new CoordinatesXRequirement()));
        coordinatesBuilder.y(pipeline.askRequirement(new CoordinatesYRequirement()));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(pipeline.askRequirement(new RealHeroRequirement()));

        humanBuilder.hasToothpick(pipeline.askRequirement(new HasToothpickRequirement()));

        humanBuilder.impactSpeed(pipeline.askRequirement(new ImpactSpeedRequirement()));

        humanBuilder.soundtrackName(pipeline.askRequirement(new SoundtrackNameRequirement()));

        humanBuilder.minutesOfWaiting(pipeline.askRequirement(new MinutesOfWaitingRequirement()));

        String moods = "";
        for (int i = 0; i < Mood.values().length; i++) {
            moods += i + " - " + Mood.values()[i] + System.lineSeparator();
        }
        output.putString(ElementRequirements.MOODS_TITLE + "\n" + moods);
        humanBuilder.mood(pipeline.askRequirement(new MoodRequirement()));

        Car car = new Car(pipeline.askRequirement(new CarNameRequirement()));
        humanBuilder.car(car);

        return humanBuilder.build();
    }
}
