package cliapp.commands.collection;

import java.util.List;

import cliapp.TextsManager;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.manager.CollectionManager;
import humandeque.manager.exceptions.ElementNotExistsError;
import humandeque.manager.exceptions.ManipulationError;
import models.Car;
import models.Coordinates;
import models.Human;
import models.Mood;
import textlocale.text.TextSupplier;

/**
 * Command for updating an element in the collection.
 */
public class UpdateElementCommand extends CollectionCommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.collection")::getText;

    /**
     * Constructs a new UpdateElementCommand with the specified collection manager.
     *
     * @param collectionManager the collection manager to use
     */
    public UpdateElementCommand(CollectionManager collectionManager) {
        super(ts.t("UpdateElementCommand.Name"),
                ts.t("UpdateElementCommand.Description"),
                collectionManager);
    }

    /**
     * Gets the list of static requirements for this command.
     *
     * @return the list of static requirements
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new ExistingIdRequirement(collectionManager));
    }

    /**
     * Asks the specified requirement with the default option, or returns the
     * default value.
     *
     * @param pipeline     the requirements pipeline to use
     * @param output       the output channel to use
     * @param requirement  the requirement to ask
     * @param defaultValue the default value to use
     * @return the value obtained from the requirement, or the default value if the
     *         requirement fails
     */
    private <I, O> O askOrDefault(RequirementsPipeline pipeline, OutputChannel output,
            Requirement<I, O> requirement,
            O defaultValue) {
        // suggest to skip field
        TextSupplier pipelineTs = TextsManager.getTexts().getPackage("cliclient.user_input_pipeline")::getText;
        output.putString(pipelineTs.t("AskDefaultRequirement", defaultValue));
        try {
            O value = pipeline.askRequirement(requirement);
            return value;
        } catch (RequirementAskError e) {
            return defaultValue;
        }
    }

    /**
     * Asks for the updated fields of a human. If a field is skipped, sets its
     * default value.
     *
     * @param defaultHuman the default human to use
     * @param pipeline     the requirements pipeline to use
     * @param output       the output channel to use
     * @return the updated human
     * @throws RequirementAskError if there is an error while asking a requirement
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
        String moodTitle = TextsManager.getTexts().getPackage("commands.requirements").getText("MoodRequirement.Title");
        output.putString(moodTitle + System.lineSeparator() + moods);
        humanBuilder.mood(askOrDefault(pipeline, output, moodRequirement, defaultHuman.getMood()));

        Car car = new Car(askOrDefault(pipeline, output, carNameRequirement, defaultHuman.getCar().getName()));
        humanBuilder.car(car);

        return humanBuilder.build();
    }

    /**
     * Update user by provided values
     *
     * @param pipeline the pipeline of requirements to be used by this command.
     * @param output   the output channel where messages will be displayed.
     * @throws ExecutionError if the collection is empty.
     */
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
        } catch (ManipulationError e) {
            throw new ExecutionError(e.getMessage());
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
            output.putString(ts.t("UpdateElementCommand.Success"));
        } catch (ElementNotExistsError | ManipulationError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
