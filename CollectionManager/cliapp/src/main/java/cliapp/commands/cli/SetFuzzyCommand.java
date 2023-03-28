package cliapp.commands.cli;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import java.util.List;

import cliapp.TextResources.Commands.Cli.SetFuzzyCommandResources;
import cliapp.TextResources.Commands.Cli.SetFuzzyCommandResources.FuzzyModeRequirementResources;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;

/**
 * Change fuzzy search mode
 */
public class SetFuzzyCommand extends CLICommand {

    private static final Requirement<String, Boolean> fuzzyModeRequirement = new Requirement<>(
            FuzzyModeRequirementResources.NAME,
            FuzzyModeRequirementResources.DESCRIPTION,
            booleanValidator);

    /**
     * Constructor for SetFuzzyCommand
     * 
     * @param client the instance of {@link CLIClient} class
     */
    public SetFuzzyCommand(CLIClient client) {
        super(SetFuzzyCommandResources.NAME, SetFuzzyCommandResources.DESCRIPTION, client);
    }

    /**
     * Get the static requirements for this command
     * 
     * @return a list of {@link Requirement} objects
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(fuzzyModeRequirement);
    }

    /**
     * Change the fuzzy search mode
     * 
     * @param pipeline an instance of {@link RequirementsPipeline} class
     * @param output   an instance of {@link OutputChannel} class
     * @throws ExecutionError if there is an error executing the command
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Boolean fuzzyMode = pipeline.askRequirement(fuzzyModeRequirement);
            client.setFuzzyMatching(fuzzyMode);
            output.putString(
                    fuzzyMode ? SetFuzzyCommandResources.FUZZY_ON : SetFuzzyCommandResources.FUZZY_OFF);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
    }

}