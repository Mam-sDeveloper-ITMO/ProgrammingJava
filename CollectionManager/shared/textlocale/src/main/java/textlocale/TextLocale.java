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

public class TextLocale {
    private static final String TL_EXTENSION = ".tl.json";

    private static Map<String, Map<String, Object>> packagesToTexts = new HashMap<>();

    @Getter
    @Setter
    private static Locale locale = Locale.getDefault();

    public static void loadPackage(String packageName) throws IOException {
        // Map<String, File> tlFiles = FilesUtils.findFilesWithExtension(packageName, TL_EXTENSION);
        // Map<String, Object> texts = new HashMap<>();
        // for (Map.Entry<String, File> entry : tlFiles.entrySet()) {
        //     String fileName = entry.getKey();
        //     String key = fileName.substring(0, fileName.length() - TL_EXTENSION.length());

        //     File file = entry.getValue();
        //     Map<String, Object> fileTexts = TLJsonUtils.jsonFileToMap(file.getAbsolutePath());

        //     texts.put(key, fileTexts);
        // }
        // packagesToTexts.put(packageName, texts);
    }

    public static String getText(String packageName, String key) {
        Map<String, Object> texts = packagesToTexts.get(packageName);
        if (texts == null) {
            return key;
        }
        if (!texts.containsKey(key)) {
            return key;
        }
        if (texts.get(key) instanceof String) {
            return (String) texts.get(key);
        }
        if (texts.get(key) instanceof Map) {
            Map<String, String> textsMap = (Map<String, String>) texts.get(key);
            return textsMap.getOrDefault(key, key);
        }
        return key;
    }
}
