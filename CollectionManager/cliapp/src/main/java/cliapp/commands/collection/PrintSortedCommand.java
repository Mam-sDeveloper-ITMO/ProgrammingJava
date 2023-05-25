package cliapp.commands.collection;

import static textlocale.TextLocale._;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

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
 * A command that prints humans from collection sorted by impact speed.
 */
public class PrintSortedCommand extends CollectionCommand {

    /**
     * Create a new instance of PrintSortedCommand.
     *
     * @param collectionManager a collection manager instance that manages the
     *                          collection to be sorted and printed
     */
    public PrintSortedCommand(CollectionManager collectionManager) {
        super(_("commands.collection.commands.PrintSortedCommand.Name"),
                _("commands.collection.commands.PrintSortedCommand.Description"),
                collectionManager);
    }

    /**
     * A requirement for the sort order of the collection, which can be either "asc"
     * or "des".
     */
    private static final Requirement<String, Boolean> sortOrderRequirement = new Requirement<>(
            _("commands.collection.commands.PrintSortedCommand.SortOrderRequirement.Name"),
            _("commands.collection.commands.PrintSortedCommand.SortOrderRequirement.Description"),
            new SortOrderValidator());

    /**
     * A validator for the sort order requirement.
     * It validates that the provided value is either "asc" or "des".
     */
    private static class SortOrderValidator implements Validator<String, Boolean> {
        @Override
        public Boolean validate(String value) throws ValidationError {
            if (value.equals("des")) {
                return true;
            } else if (value.equals("asc")) {
                return false;
            } else {
                throw new ValidationError(value,
                        _("commands.collection.commands.PrintSortedCommand.SortOrderRequirement.IllegalOrder"));
            }
        }
    }

    /**
     * Get a sorted version of the collection using a PriorityQueue.
     *
     * @param collection the collection to be sorted
     * @return a sorted version of the collection
     */
    private HumanDeque getSortedCollection(Collection<Human> collection) {
        PriorityQueue<Human> sortedQueue = new PriorityQueue<Human>(collection);
        HumanDeque sortedCollection = new HumanDeque();
        sortedCollection.addAll(sortedQueue);
        return sortedCollection;
    }

    /**
     * Get the list of requirements for this command.
     *
     * @return a list of requirements for this command
     */
    @Override
    public List<Requirement<?, ?>> getStaticRequirements() {
        return List.of(sortOrderRequirement);
    }

    /**
     * Execute the command by printing the sorted collection.
     *
     * @param pipeline a requirements pipeline to provide dynamic requirements for
     *                 this command
     * @param output   an output channel to print the sorted collection to
     * @throws ExecutionError if any error occurs during the execution of the
     *                        command
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        try {
            Boolean descendingOrder = pipeline.askRequirement(sortOrderRequirement);
            HumanDeque humans = getSortedCollection(collectionManager.getCollection());
            if (humans.isEmpty()) {
                throw new ExecutionError(_("commands.collection.commands.PrintSortedCommand.EmptyCollection"));
            } else {
                output.putString(_("commands.collection.commands.PrintSortedCommand.Title"));
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
