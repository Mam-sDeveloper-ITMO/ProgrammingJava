package cliapp.commands.collection;

import java.util.Random;

import cliapp.TextResources.Commands.Collection.RandomCommandResources;
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
 * Adds a randomly generated human with various fields to the collection
 */
public class RandomCommand extends CollectionCommand {
    public RandomCommand(CollectionManager collectionManager) {
        super(RandomCommandResources.NAME, RandomCommandResources.DESCRIPTION,
                collectionManager);
    }

    /**
     * Generates a human with random values for all fields
     * 
     * @return the randomly generated human
     */
    private Human generateHuman() {
        Random random = new Random();

        Human.HumanBuilder humanBuilder = Human.builder();

        int nameIndex = random.nextInt(RandomCommandResources.RandomValues.NAMES.length);
        humanBuilder.name(RandomCommandResources.RandomValues.NAMES[nameIndex]);

        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(random.nextFloat(-500, 1000000));
        coordinatesBuilder.y(random.nextFloat(-500, 1000000));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(random.nextBoolean());

        humanBuilder.hasToothpick(random.nextBoolean());

        humanBuilder.impactSpeed(random.nextDouble());

        int soundtrackIndex = random.nextInt(RandomCommandResources.RandomValues.SOUNDTRACKS.length);
        humanBuilder.soundtrackName(RandomCommandResources.RandomValues.SOUNDTRACKS[soundtrackIndex]);

        humanBuilder.minutesOfWaiting(random.nextFloat(0, 200));

        int moodIndex = random.nextInt(Mood.values().length);
        humanBuilder.mood(Mood.values()[moodIndex]);

        int carNameIndex = random.nextInt(RandomCommandResources.RandomValues.CARS.length);
        Car car = new Car(RandomCommandResources.RandomValues.CARS[carNameIndex]);
        humanBuilder.car(car);

        return humanBuilder.build();
    }

    /**
     * Adds a randomly generated human to the collection and prints information
     * about it
     * 
     * @param pipeline the pipeline of requirements
     * @param output   the output channel to write to
     * @throws ExecutionError if an error occurs while adding the human to the
     *                        collection
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        Human human = generateHuman();
        try {
            collectionManager.add(human);
            output.putString(RandomCommandResources.TITLE);
            output.putString(human.toString());
        } catch (ElementAlreadyExistsError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
