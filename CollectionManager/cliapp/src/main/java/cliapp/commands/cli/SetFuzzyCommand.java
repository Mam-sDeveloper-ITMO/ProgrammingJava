package cliapp.commands.cli;

import static commands.requirements.validators.common.StringValidators.booleanValidator;

import java.util.List;

import cliapp.TextsManager;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import textlocale.TextSupplier;

/**
 * Change fuzzy search mode
 */
public class SetFuzzyCommand extends CLICommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.cli")::getText;

    private static final Requirement<String, Boolean> fuzzyModeRequirement = new Requirement<>(
            ts.t("SetFuzzyCommand.FuzzyModeRequirement.Name"),
            ts.t("SetFuzzyCommand.FuzzyModeRequirement.Description"),
            booleanValidator);

    /**
     * Constructor for SetFuzzyCommand
     *
     * @param client the instance of {@link CLIClient} class
     */
    public SetFuzzyCommand(CLIClient client) {
        super(ts.t("SetFuzzyCommand.Name"),
                ts.t("SetFuzzyCommand.Description"),
                client);
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
                    fuzzyMode ? ts.t("SetFuzzyCommand.FuzzyOn")
                            : ts.t("SetFuzzyCommand.FuzzyOff"));
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
    }

}