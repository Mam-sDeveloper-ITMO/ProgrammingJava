package humandeque;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import lombok.Data;
import lombok.EqualsAndHashCode;
import models.Human;

/**
 * Represents a collection of human objects and associated metadata.
 * 
 * This class extends the {@link java.util.ArrayDeque} class and adds a creation
 * time field to track when the
 * collection was created.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HumanDeque extends ArrayDeque<Human> {
    /**
     * The time when this collection was created.
     */
    private LocalDateTime createTime;

    /**
     * Creates a new, empty HumanDeque instance with the current time set as the
     * creation time.
     */
    public HumanDeque() {
        super();
        createTime = LocalDateTime.now();
    }

    /**
     * Creates a new HumanDeque instance with the same contents as the provided
     * ArrayDeque. The creation time
     * of this new instance will be set to the current time.
     * 
     * @param arrayDeque the ArrayDeque to copy
     */
    public HumanDeque(ArrayDeque<Human> arrayDeque) {
        this();
        this.addAll(arrayDeque);
    }
}
