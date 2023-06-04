package textlocale;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
     * Loads texts from specified package.
     *
     * @param packageName    name of package
     * @param localeSupplier supplier of current locale
     * @return loaded package
     * @throws IOException              if an I/O error occurs
     * @throws IllegalArgumentException if texts are invalid
     * @see TextPackage
     */
    public static TextPackage loadPackage(String packageName, Supplier<String> localeSupplier) throws IOException {
        Map<String, Object> matchingFiles = FilesUtils.findFilesWithExtension(packageName, ".tl.json");
        Map<String, Object> loadedTexts = loadPackageFromJson(matchingFiles);
        if (!TextMapUtils.isValidTextMap(loadedTexts)) {
            throw new IllegalArgumentException("Invalid json file");
        }
        return new TextPackage(packageName, () -> loadedTexts, localeSupplier);
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
}
