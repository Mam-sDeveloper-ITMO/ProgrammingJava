package cliapp.commands.collection;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import cliapp.Messages;
import cliapp.commands.collection.requirements.SortOrderRequirement;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import models.Human;


/**
 * Print humans from collection sorted by impact speed.
 */
public class PrintSortedCommand extends CollectionCommand {
    public PrintSortedCommand(CollectionManager collectionManager) {
        super(Messages.PrintSortedCommand.NAME, Messages.PrintSortedCommand.DESCRIPTION,
            collectionManager);
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(new SortOrderRequirement());
    }

    private HumanDeque getSortedCollection(Collection<Human> collection) {
        PriorityQueue<Human> sortedQueue = new PriorityQueue<Human>(collection);
        HumanDeque sortedCollection = new HumanDeque();
        sortedCollection.addAll(sortedQueue);
        return sortedCollection;
    }

    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Boolean descendingOrder = pipeline.askRequirement(new SortOrderRequirement());
            HumanDeque humans = getSortedCollection(collectionManager.getCollection());
            if (humans.isEmpty()) {
                output.putString(Messages.ShowCommand.EMPTY);
            } else {
                output.putString(Messages.ShowCommand.LIST);
                while (!humans.isEmpty()) {
                    if (descendingOrder) {
                        output.putString(humans.pollLast().toString());
                    } else {
                        output.putString(humans.pollFirst().toString());
                    }
                }
            }
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
    }
}
