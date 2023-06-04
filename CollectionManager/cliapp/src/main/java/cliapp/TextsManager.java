package cliapp;

import lombok.Getter;
import lombok.Setter;
import textlocale.TextLocale;
import textlocale.TextPackage;

/**
 * Manager for texts resources.
 */
public class TextsManager {
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
     * Update texts.
     *
     * @throws Exception if an error occurs on package loading.
     */
    public static void updateTexts() throws Exception {
        TextsManager.texts = TextLocale.loadPackage("cliapp", () -> TextsManager.locale);
    }
}
