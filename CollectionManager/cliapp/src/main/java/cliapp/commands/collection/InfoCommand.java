package cliapp.commands.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cliapp.Messages;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.RequirementsPipeline;
import humandeque.manager.CollectionManager;

/**
 * That command shows information about collection.
 */
public class InfoCommand extends CollectionCommand {
    public InfoCommand(CollectionManager collectionManager) {
        super(Messages.InfoCommand.NAME, Messages.InfoCommand.DESCRIPTION, collectionManager);
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        output.putString(Messages.InfoCommand.SUCCESS);

        String collectionType = collectionManager.getCollection().getClass().getSimpleName();
        output.putString(Messages.InfoCommand.TYPE.formatted(collectionType));

        LocalDateTime initTime = collectionManager.getCollection().getCreateTime();
        String formattedInitTime = initTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        output.putString(Messages.InfoCommand.INIT_TIME.formatted(formattedInitTime));

        long size = collectionManager.getCollection().size();
        output.putString(Messages.InfoCommand.ELEMENTS_COUNT.formatted(size));
    }
}