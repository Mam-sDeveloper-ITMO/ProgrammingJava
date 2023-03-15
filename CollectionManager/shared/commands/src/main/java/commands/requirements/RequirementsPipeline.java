package commands.requirements;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;

/**
 * This class used for dynamic supply of requirements to command from external
 * source.
 * 
 * For example, in CLI application you can use this class for asking user to
 * input param, in UI - to show input field, or send request to server.
 */
@RequiredArgsConstructor
public class RequirementsPipeline {
    private final Consumer<Requirement<?>> processor;

    /**
     * Ask to update requirement value from external source.
     * 
     * Pipeline processor must update requirement value directly and not create new
     * instance.
     * 
     * For exclude problems with multiple threads, you must create new instance of
     * Command that consists requirements instances.
     * 
     * @param requirement - requirement for process.
     * @return
     */
    public <T> Requirement<T> askRequirement(Requirement<T> requirement) {
        processor.accept(requirement);
        return requirement;
    }
}
