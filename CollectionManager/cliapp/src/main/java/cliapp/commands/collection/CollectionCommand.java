package cliapp.commands.collection;

import commands.Command;
import humandeque.manager.CollectionManager;

/**
 * Basic class of all commands, that use CollectionManager as receiver
 */
public abstract class CollectionCommand extends Command {
    protected CollectionManager collectionManager;

    public CollectionCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }
}
