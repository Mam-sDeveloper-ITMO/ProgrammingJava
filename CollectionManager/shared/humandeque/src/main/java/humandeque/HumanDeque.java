package humandeque;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

import lombok.Data;
import lombok.EqualsAndHashCode;
import models.Human;

/**
 * 
 * Target collection of project.
 * 
 * Consists human objects and some meta information.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HumanDeque extends ArrayDeque<Human> {
    private LocalDateTime createTime;

    /**
     * Constructor for new empty collection.
     */
    public HumanDeque() {
        super();
        createTime = LocalDateTime.now();
    }

    /**
     * Constructor for existed collection.
     * 
     * @param arrayDeque
     */
    public HumanDeque(ArrayDeque<Human> arrayDeque) {
        this();
    }
}
