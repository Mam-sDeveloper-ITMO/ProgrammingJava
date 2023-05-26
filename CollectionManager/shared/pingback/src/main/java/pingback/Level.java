package pingback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Level of logging.
 */
@RequiredArgsConstructor
public enum Level {
    CRITICAL("CRITICAL"),
    ERROR("ERROR"),
    WARNING("WARNING"),
    INFO("INFO"),
    DEBUG("DEBUG"),
    NOTSET("NOTSET");

    /**
     * Level name.
     */
    @Getter
    private final String name;

    /**
     * Get icon for level.
     *
     * @return icon
     */
    public String getIcon() {
        switch (this) {
            case CRITICAL:
                return ":fire:";
            case ERROR:
                return ":x:";
            case WARNING:
                return ":warning:";
            case INFO:
                return ":information_source:";
            case DEBUG:
                return ":bug:";
            case NOTSET:
                return ":question:";
            default:
                return ":question:";
        }
    }
}
