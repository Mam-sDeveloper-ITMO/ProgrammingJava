package cliapp.commands.collection;

import static textlocale.TextLocale._;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;

/**
 * This command shows information about the collection.
 */
public class InfoCommand extends CollectionCommand {

    /**
     * Constructs a new InfoCommand with the given collection manager.
     *
     * @param collectionManager the collection manager to be used by this command
     */
    public InfoCommand(CollectionManager collectionManager) {
        super(_("commands.collection.commands.InfoCommand.Name"),
                _("commands.collection.commands.InfoCommand.Description"),
                collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output.putString(_("commands.collection.commands.InfoCommand.Title"));

        String collectionType = collectionManager.getCollection().getClass().getSimpleName();
        output.putString(_("commands.collection.commands.InfoCommand.Type").formatted(collectionType));

        LocalDateTime initTime = collectionManager.getCollection().getCreateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedInitTime = initTime.format(formatter);
        output.putString(_("commands.collection.commands.InfoCommand.InitTime").formatted(formattedInitTime));

        long size = collectionManager.getCollection().size();
        output.putString(_("commands.collection.commands.InfoCommand.ElementsCount").formatted(String.valueOf(size)));
    }
}
