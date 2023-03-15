package commands.requirements;

/**
 * This interface used for dynamic supply of requirements to command from
 * external source.
 * 
 * Used in {@link RequirementsPipeline} for processing requirements.
 * 
 */
@FunctionalInterface
public interface RequirementsProcessor {
    /**
     * Process requirement and return it`s value.
     * 
     * Throw exception if requirement can`t be processed.
     * @param requirement - requirement for process.
     */
    <T> T processRequirement(Requirement<T> requirement) throws Exception;
}
