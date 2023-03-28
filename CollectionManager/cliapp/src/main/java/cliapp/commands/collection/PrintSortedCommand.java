package cliapp.commands.collection;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import cliapp.TextResources.Commands.Collection.PrintSortedCommandResources;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import humandeque.HumanDeque;
import humandeque.manager.CollectionManager;
import models.Human;

/**
 * Print humans from collection sorted by impact speed.
 */
public class PrintSortedCommand extends CollectionCommand {
    public PrintSortedCommand(CollectionManager collectionManager) {
        super(PrintSortedCommandResources.NAME, PrintSortedCommandResources.DESCRIPTION,
                collectionManager);
    }

    private static final Requirement<String, Boolean> sortOrderRequirement = new Requirement<>(
            PrintSortedCommandResources.SortOrderRequirement.NAME,
            PrintSortedCommandResources.SortOrderRequirement.DESCRIPTION,
            new SortOrderValidator());

    /**
     * Validate that string is des or asc and return true if des
     */
    private static class SortOrderValidator implements Validator<String, Boolean> {
        @Override
        public Boolean validate(String value) throws ValidationError {
            if (value.equals("des")) {
                return true;
            } else if (value.equals("asc")) {
                return false;
            } else {
                throw new ValidationError(value, PrintSortedCommandResources.SortOrderRequirement.ILLEGAL_ORDER);
            }
        }
    }

    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(sortOrderRequirement);
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
            Boolean descendingOrder = pipeline.askRequirement(sortOrderRequirement);
            HumanDeque humans = getSortedCollection(collectionManager.getCollection());
            if (humans.isEmpty()) {
                output.putString(PrintSortedCommandResources.EMPTY);
            } else {
                output.putString(PrintSortedCommandResources.TITLE);
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
