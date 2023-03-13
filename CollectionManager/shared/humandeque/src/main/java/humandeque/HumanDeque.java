package humandeque;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
    @NonNull
    private final LocalDateTime createTime = LocalDateTime.now();
}
