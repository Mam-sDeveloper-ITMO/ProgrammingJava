package textlocale;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private static Locale locale = Locale.getDefault();

    /**
     * Loads texts from specified directory.
     * Subdirectories used as subkeys.
     *
     * @param directory Directory with tl.json files.
     */
    public static void loadPackage(String packageName) throws IOException {
        Map<String, Object> matchingFiles = FilesUtils.findFilesWithExtension(packageName, ".tl.json");
        texts = _parseJsonFiles(matchingFiles);
        System.out.println();
    }

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
}
