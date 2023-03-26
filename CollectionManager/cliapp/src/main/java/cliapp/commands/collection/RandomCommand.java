package cliapp.commands.collection;

import java.util.Random;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;

/**
 * Add human with random fields to collection
 */
public class RandomCommand extends CollectionCommand {
    public RandomCommand(CollectionManager collectionManager) {
        super(Messages.RandomCommand.NAME, Messages.RandomCommand.DESCRIPTION,
                collectionManager);
    }

    /**
     * Generate human with random fields
     */
    private Human generateHuman() {
        Random random = new Random();

        Human.HumanBuilder humanBuilder = Human.builder();

        int nameIndex = random.nextInt(Messages.RandomCommand.NAMES.length);
        humanBuilder.name(Messages.RandomCommand.NAMES[nameIndex]);

        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(random.nextFloat(-500, 1000000));
        coordinatesBuilder.y(random.nextFloat(-500, 1000000));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(random.nextBoolean());

        humanBuilder.hasToothpick(random.nextBoolean());

        humanBuilder.impactSpeed(random.nextDouble());

        int soundtrackIndex = random.nextInt(Messages.RandomCommand.SOUNDTRACKS.length);
        humanBuilder.soundtrackName(Messages.RandomCommand.SOUNDTRACKS[soundtrackIndex]);

        humanBuilder.minutesOfWaiting(random.nextFloat(0, 200));

        int moodIndex = random.nextInt(Mood.values().length);
        humanBuilder.mood(Mood.values()[moodIndex]);

        int carNameIndex = random.nextInt(Messages.RandomCommand.CARS.length);
        Car car = new Car(Messages.RandomCommand.CARS[carNameIndex]);
        humanBuilder.car(car);

        return humanBuilder.build();
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Human human = generateHuman();
        try {
            collectionManager.add(human);
            output.putString(Messages.RandomCommand.TITLE);
            output.putString(human.toString());
        } catch (ElementAlreadyExistsError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
