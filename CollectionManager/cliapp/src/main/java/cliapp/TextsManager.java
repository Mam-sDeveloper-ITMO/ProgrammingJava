package cliapp;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;
import textlocale.TextLocale;
import textlocale.loader.FilesLoader;
import textlocale.loader.common.ResourcesLoader;
import textlocale.text.TextPackage;

/**
 * Manager for texts resources.
 */
public class TextsManager {
    /**
     * Extension for texts files.
     */
    public static final String TEXTS_JSON_EXTENSION = ".tl.json";

    /**
     * Current locale.
     */
    @Getter
    @Setter
    private static String locale = "en";

    /**
     * Texts package.
     */
    @Getter
    private static TextPackage texts;

    /**
     * Static class.
     */
    private TextsManager() {
    }

    /**
     * Load texts.
     */
    static {
        try {
            URL codeSourceUrl = TextsManager.class.getProtectionDomain().getCodeSource().getLocation();
            FilesLoader loader = new ResourcesLoader(codeSourceUrl, TEXTS_JSON_EXTENSION);
            texts = TextLocale.loadPackage("cliapp", TextsManager::getLocale, loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
