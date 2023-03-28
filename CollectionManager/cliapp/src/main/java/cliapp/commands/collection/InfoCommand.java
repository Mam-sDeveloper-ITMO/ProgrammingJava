package cliapp.commands.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cliapp.TextResources.Commands.Collection.InfoCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;

/**
 * That command shows information about collection.
 */
public class InfoCommand extends CollectionCommand {
    public InfoCommand(CollectionManager collectionManager) {
        super(InfoCommandResources.NAME, InfoCommandResources.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output.putString(InfoCommandResources.TITLE);

        String collectionType = collectionManager.getCollection().getClass().getSimpleName();
        output.putString(InfoCommandResources.TYPE.formatted(collectionType));

        LocalDateTime initTime = collectionManager.getCollection().getCreateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedInitTime = initTime.format(formatter);
        output.putString(InfoCommandResources.INIT_TIME.formatted(formattedInitTime));

        long size = collectionManager.getCollection().size();
        output.putString(InfoCommandResources.ELEMENTS_COUNT.formatted(size));
    }
}
