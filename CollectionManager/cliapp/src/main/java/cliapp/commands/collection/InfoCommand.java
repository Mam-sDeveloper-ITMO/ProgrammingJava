package cliapp.commands.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cliapp.TextsManager;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;
import textlocale.TextSupplier;

/**
 * This command shows information about the collection.
 */
public class InfoCommand extends CollectionCommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.collection")::getText;

    /**
     * Constructs a new InfoCommand with the given collection manager.
     *
     * @param collectionManager the collection manager to be used by this command
     */
    public InfoCommand(CollectionManager collectionManager) {
        super(ts.t("InfoCommand.Name"),
                ts.t("InfoCommand.Description"),
                collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output.putString(ts.t("InfoCommand.Title"));

        String collectionType = collectionManager.getCollection().getClass().getSimpleName();
        output.putString(ts.t("InfoCommand.Type", collectionType));

        LocalDateTime initTime = collectionManager.getCollection().getCreateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedInitTime = initTime.format(formatter);
        output.putString(ts.t("InfoCommand.InitTime", formattedInitTime).formatted());

        long size = collectionManager.getCollection().size();
        output.putString(ts.t("InfoCommand.ElementsCount", String.valueOf(size)));
    }
}
