package textlocale.utils;

import java.util.List;
import java.util.Map;

/**
 * Provides methods for working with text maps.
 */
public class TextMapUtils {
    /**
     * Get text by key and locale.
     *
     * Key format:
     * package.subpackage.key.subkey
     *
     * @param key   Text key.
     * @param texts Text map.
     * @return Text value or key if text not found.
     */
    public static String getText(String key, String locale, Map<String, Object> texts) {
        // get terminate node
        Map<String, Object> subTexts = getSubTexts(key, texts);
        // get text
        return (String) subTexts.getOrDefault(locale, key);
    }

    /**
     * Find sub texts belonging to the specified key.
     *
     * @param key   Subpackage key.
     * @param texts Text map.
     * @return Text map.
     * @throws IllegalArgumentException If texts not found.
     */
    public static Map<String, Object> getSubTexts(String key, Map<String, Object> texts) {
        List<String> keys = List.of(key.split("\\."));
        String currentKey = keys.get(0);

        Object subNode = texts.get(currentKey);
        if (!(subNode instanceof Map)) {
            throw new IllegalArgumentException("Texts not found for key: " + key);
        }

        if (keys.size() == 1) {
            return (Map<String, Object>) subNode;
        }

        Map<String, Object> subTexts = (Map<String, Object>) subNode;
        String subKey = String.join(".", keys.subList(1, keys.size()));
        return getSubTexts(subKey, subTexts);
    }

    /**
     * Verify that map is valid text map.
     *
     * Each text map node must be a map of valid nodes or a string.
     *
     * @param texts Text map.
     * @return True if map is valid text map.
     */
    public static boolean isValidTextMap(Map<String, Object> texts) {
        for (Map.Entry<String, Object> entry : texts.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                if (!isValidTextMap((Map<String, Object>) value)) {
                    return false;
                }
            } else if (!(value instanceof String)) {
                return false;
            }
        }
        return true;
    }
}
