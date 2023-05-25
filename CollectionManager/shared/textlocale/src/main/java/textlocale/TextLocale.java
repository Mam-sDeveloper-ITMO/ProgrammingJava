package textlocale;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;
import textlocale.utils.FilesUtils;
import textlocale.utils.TLJsonUtils;

/**
 * Provides methods for working with text localization.
 *
 * Localization is stored in tl.json files.
 * Format of tl.json files:
 * {
 * "key1": {
 * "en": "value1",
 * "ru": "значение1"
 * },
 * "key2": {
 * subkey1: {
 * "en": "value2",
 * "ru": "значение2"
 * },
 * ...
 * }
 */
public class TextLocale {
    /**
     * Contains all loaded texts.
     */
    private static Map<String, Object> texts = new HashMap<>();

    /**
     * Current locale.
     */
    @Getter
    @Setter
    private static String locale = Locale.getDefault().getLanguage();

    /**
     * Loads texts from specified directory.
     * Subdirectories used as subkeys.
     *
     * @param directory Directory with tl.json files.
     */
    public static void loadPackage(String packageName) throws IOException {
        Map<String, Object> matchingFiles = FilesUtils.findFilesWithExtension(packageName, ".tl.json");
        texts = _parseJsonFiles(matchingFiles);
    }

    /**
     * Parse map with json files.
     *
     * Each node in json file must be a map or a string.
     *
     * @param jsonFiles map with json files
     * @return map with parsed json files
     * @throws IOException if an I/O error occurs
     */
    private static Map<String, Object> _parseJsonFiles(Map<String, Object> jsonFiles) throws IOException {
        Map<String, Object> texts = new HashMap<>();

        for (Map.Entry<String, Object> entry : jsonFiles.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                texts.put(key, _parseJsonFiles((Map<String, Object>) value));
            } else if (value instanceof File) {
                texts.put(key, TLJsonUtils.jsonFileToMap((File) value));
            }
        }
        return texts;
    }

    /**
     * Get text by key.
     *
     * Key format:
     * package.subpackage.key.subkey
     *
     * @param key Text key.
     * @return Text value.
     */
    public static String getText(String key) {
        return _getText(key, texts);
    }

    /**
     * Alias for getText.
     * @param key Text key.
     * @param texts Texts map.
     * @return Text value.
     */
    public static String _(String key) {
        return getText(key);
    }

    /**
     * Get text by key.
     *
     * Key format:
     * package.subpackage.key.subkey
     *
     * @param key  Text key.
     * @param args Text arguments.
     * @return Text value.
     */
    private static String _getText(String key, Map<String, Object> texts) {
        String[] keys = key.split("\\.");
        String currentKey = keys[0];
        Object value = texts.get(currentKey);

        if (value instanceof Map) {
            Map<String, Object> subTexts = (Map<String, Object>) value;
            if (subTexts.size() > 0) {
                Entry<String, Object> firstEntry = subTexts.entrySet().iterator().next();
                if (firstEntry.getValue() instanceof String) {
                    return (String) subTexts.getOrDefault(TextLocale.locale, key);
                } else {
                    String subKey = String.join(".", Arrays.copyOfRange(keys, 1, keys.length));
                    return _getText(subKey, subTexts);
                }
            } else {
                return key;
            }
        }
        return key;
    }
}
