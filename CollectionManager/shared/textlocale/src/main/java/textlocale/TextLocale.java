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
import textlocale.utils.TextMapUtils;

/**
 * Provides methods for working with text localization.
 *
 * Localization is stored in tl.json files.
 * Format of tl.json files:
 *
 * <pre>
 * {
 *    "key1": {
 *        "en": "value1",
 *        "ru": "значение1"
 *    },
 *    "key2": {
 *        subkey1: {
 *            "en": "value2",
 *            "ru": "значение2"
 *        },
 *        ...
 *    }
 *    ...
 * }
 * </pre>
 */
public class TextLocale {
    /**
     * Current locale.
     */
    @Getter
    @Setter
    private static String locale = Locale.getDefault().getLanguage();

    /**
     * Loaded texts.
     */
    @Getter
    private static Map<String, Object> texts = new HashMap<>();

    /**
     * Contains all loaded texts.
     */
    @Getter
    private static TextPackage rootPackage = new TextPackage("", () -> TextLocale.texts, TextLocale::getLocale);

    /**
     * Loads texts from specified package.
     * Previous texts will be removed.
     *
     * @param directory Directory with tl.json files.
     * @throws IllegalArgumentException if json file is invalid
     */
    public static void loadPackage(String packageName) throws IOException {
        Map<String, Object> matchingFiles = FilesUtils.findFilesWithExtension(packageName, ".tl.json");
        Map<String, Object> loadedTexts = loadPackageFromJson(matchingFiles);
        if (!TextMapUtils.isValidTextMap(loadedTexts)) {
            throw new IllegalArgumentException("Invalid json file");
        }
        texts = loadedTexts;
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
    private static Map<String, Object> loadPackageFromJson(Map<String, Object> jsonFiles) throws IOException {
        Map<String, Object> texts = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonFiles.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                texts.put(key, loadPackageFromJson((Map<String, Object>) value));
            } else if (value instanceof File) {
                texts.put(key, TLJsonUtils.jsonFileToMap((File) value));
            }
        }
        return texts;
    }

    /**
     * Get package by key.
     *
     * @param key Package key.
     * @return Package.
     *
     * @see TextPackage
     */
    public static TextPackage getPackage(String key) {
        return rootPackage.getPackage(key);
    }
}
