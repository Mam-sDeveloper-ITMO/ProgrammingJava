package cliapp.commands.cli;

import java.util.List;
import cliapp.Messages;
import cliapp.cliclient.CLIClient;
import cliapp.commands.cli.requirements.FuzzyModeRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;

/**
 * Change fuzzy search mode
 */
public class SetFuzzyCommand extends CLICommand {
    public SetFuzzyCommand(CLIClient client) {
        super(Messages.SetFuzzyCommand.NAME, Messages.SetFuzzyCommand.DESCRIPTION, client);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new FuzzyModeRequirement());
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Boolean fuzzyMode = pipeline.askRequirement(new FuzzyModeRequirement());
            client.setFuzzyMatching(fuzzyMode);
            output.putString(
                fuzzyMode ? Messages.SetFuzzyCommand.FUZZY_ON : Messages.SetFuzzyCommand.FUZZY_OFF);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage(), e);
        }
    }

}
