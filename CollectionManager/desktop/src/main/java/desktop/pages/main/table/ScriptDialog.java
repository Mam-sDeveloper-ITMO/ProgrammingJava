package desktop.pages.main.table;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cliapp.cliclient.CLIClient;
import cliapp.commands.cli.ExecuteCommand;
import cliapp.commands.cli.ExitCommand;
import cliapp.commands.cli.HelpCommand;
import cliapp.commands.cli.HistoryCommand;
import cliapp.commands.cli.SetFuzzyCommand;
import cliapp.commands.collection.AddElementCommand;
import cliapp.commands.collection.AverageOfImpactSpeedCommand;
import cliapp.commands.collection.ClearCommand;
import cliapp.commands.collection.FilterByImpactSpeed;
import cliapp.commands.collection.HeadCommand;
import cliapp.commands.collection.InfoCommand;
import cliapp.commands.collection.PrintSortedCommand;
import cliapp.commands.collection.RandomCommand;
import cliapp.commands.collection.RemoveByIdCommand;
import cliapp.commands.collection.RemoveFirstCommand;
import cliapp.commands.collection.RemoveLastCommand;
import cliapp.commands.collection.ShowCommand;
import cliapp.commands.collection.TailCommand;
import cliapp.commands.collection.UpdateElementCommand;
import commands.OutputChannel;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import desktop.App;
import lombok.RequiredArgsConstructor;
import textlocale.text.TextSupplier;

public class ScriptDialog extends JDialog {
    private TextSupplier ts = App.texts
        .getPackage("texts.main.table.toolbar.script.dialog")::getText;

    private File scriptFile;

    private JButton button;

    private JTextArea scriptField;

    private Runnable finishHandler;

    public ScriptDialog(File scriptFile, Runnable finishHandler) {
        this.finishHandler = finishHandler;
        this.scriptFile = scriptFile;
        init();
    }

    private void init() {
        setTitle(scriptFile.getName());

        var contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text area with script content
        String scriptContent;
        try {
            scriptContent = Files.readString(scriptFile.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        scriptField = new JTextArea(scriptContent);
        scriptField.setLineWrap(true);
        scriptField.setWrapStyleWord(true);
        scriptField.setEditable(false);

        var scriptArea = new JScrollPane(scriptField);
        contentPanel.add(scriptArea, BorderLayout.CENTER);

        // Run button
        button = new JButton(ts.t("runButton"));
        button.addActionListener((e) -> new Thread(this::runScript).start());
        contentPanel.add(button, BorderLayout.PAGE_END);

        setContentPane(contentPanel);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private void runScript() {
        var cli = createClient();
        var executor = new ExecuteCommand(cli);

        var pipe = new ScripPipeline(scriptFile);
        var output = new ScriptOutput();

        output.putString(ts.t("running"));
        try {
            executor.execute(pipe, output);
        } catch (Exception e) {
            output.putString(e.getMessage());
            e.printStackTrace();
        }
        output.putString(ts.t("done"));

        button.setText(ts.t("closeButton"));
        button.removeActionListener(button.getActionListeners()[0]);
        button.addActionListener((e) -> dispose());

        if (finishHandler != null) {
            finishHandler.run();
        }
    }

    private CLIClient createClient() {
        var cli = new CLIClient();

        var manager = App.context.getManager();
        cli.registerCommand("info", new InfoCommand(manager));
        cli.registerCommand("show", new ShowCommand(manager));
        cli.registerCommand("add", new AddElementCommand(manager));
        cli.registerCommand("update", new UpdateElementCommand(manager));
        cli.registerCommand("remove_first", new RemoveFirstCommand(manager));
        cli.registerCommand("remove_last", new RemoveLastCommand(manager));
        cli.registerCommand("remove_by_id", new RemoveByIdCommand(manager));
        cli.registerCommand("print_sorted", new PrintSortedCommand(manager));
        cli.registerCommand("random", new RandomCommand(manager));
        cli.registerCommand("clear", new ClearCommand(manager));
        cli.registerCommand("average_speed", new AverageOfImpactSpeedCommand(manager));
        cli.registerCommand("head", new HeadCommand(manager));
        cli.registerCommand("tail", new TailCommand(manager));
        cli.registerCommand("filter", new FilterByImpactSpeed(manager));
        cli.registerCommand("help", new HelpCommand(cli));
        cli.registerCommand("exit", new ExitCommand(cli));
        cli.registerCommand("history", new HistoryCommand(cli));
        cli.registerCommand("fuzzy", new SetFuzzyCommand(cli));
        cli.registerCommand("execute", new ExecuteCommand(cli));

        return cli;
    }

    @RequiredArgsConstructor
    private class ScripPipeline implements RequirementsPipeline {
        private final File scriptFile;

        @Override
        public <I, O> O askRequirement(Requirement<I, O> requirement)
            throws RequirementAskError {
            try {
                if (requirement.getName().equals("Script file")) {
                    return requirement.getValue((I) scriptFile.getAbsolutePath());
                }

                var userInput = JOptionPane.showInputDialog(
                    ScriptDialog.this,
                    requirement.getName(),
                    requirement.getDescription(),
                    JOptionPane.QUESTION_MESSAGE
                );
                return requirement.getValue((I) userInput);
            } catch (ValidationError e) {
                throw new RequirementAskError(requirement.getName(), e);
            } catch (ClassCastException e) {
                throw new RequirementAskError(requirement.getName(), e);
            }
        }
    }

    private class ScriptOutput implements OutputChannel {
        public ScriptOutput() {
            scriptField.setText("");
        }

        @Override
        public void putString(String string) {
            scriptField.append(System.lineSeparator());
            scriptField.append(string);
        }
    }
}
