package cliapp.commands.collection;

import java.util.Random;

import cliapp.TextsManager;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementAlreadyExistsError;
import humandeque.manager.exceptions.ManipulationError;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;
import textlocale.TextSupplier;

/**
 * Adds a randomly generated human with various fields to the collection
 */
public class RandomCommand extends CollectionCommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.collection")::getText;

    public RandomCommand(CollectionManager collectionManager) {
        super(ts.t("RandomCommand.Name"),
                ts.t("RandomCommand.Description"),
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

        String[] names = ts.t("RandomCommand.RandomValues.NAMES").split(",");
        int nameIndex = random.nextInt(names.length);
        humanBuilder.name(names[nameIndex]);

        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(random.nextFloat(-500, 1000000));
        coordinatesBuilder.y(random.nextFloat(-500, 1000000));

        humanBuilder.coordinates(coordinatesBuilder.build());

        humanBuilder.realHero(random.nextBoolean());

        humanBuilder.hasToothpick(random.nextBoolean());

        humanBuilder.impactSpeed(random.nextDouble());

        String[] soundtrackNames = ts.t("RandomCommand.RandomValues.SOUNDTRACKS")
                .split(",");
        int soundtrackIndex = random.nextInt(soundtrackNames.length);
        humanBuilder.soundtrackName(soundtrackNames[soundtrackIndex]);

        humanBuilder.minutesOfWaiting(random.nextFloat(0, 200));

        int moodIndex = random.nextInt(Mood.values().length);
        humanBuilder.mood(Mood.values()[moodIndex]);

        String[] carNames = ts.t("RandomCommand.RandomValues.CARS").split(",");
        int carNameIndex = random.nextInt(carNames.length);
        Car car = new Car(carNames[carNameIndex]);

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
            output.putString(ts.t("RandomCommand.Title"));
            output.putString(human.toString());
        } catch (ElementAlreadyExistsError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
