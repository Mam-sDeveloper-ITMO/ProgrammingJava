package textlocale;

import java.util.Map;
import java.util.function.Supplier;

import lombok.Data;
import textlocale.utils.TextMapUtils;

/**
 * Container for localized texts.
 *
 * @see TextLocale
 */
@Data
public class TextPackage {
    /**
     * Package key.
     */
    private final String key;

    /**
     * Supplier of texts belonging to this package.
     */
    private final Supplier<Map<String, Object>> texts;

    /**
     * Supplier of current locale.
     */
    private final Supplier<String> locale;

    /**
     * Get text by key.
     *
     * Key format:
     * package.subpackage.key.subkey
     *
     * @param key Text key.
     * @return Text value.
     */
    public String getText(String key) {
        return TextMapUtils.getText(key, this.locale.get(), this.texts.get());
    }

    /**
     * Get instance of subpackage by key
     *
     * @param key Subpackage key.
     * @return Subpackage instance.
     * @throws IllegalArgumentException If subpackage not found.
     */
    public TextPackage getPackage(String key) {
        Supplier<Map<String, Object>> subTextsSupplier = () -> {
            Map<String, Object> texts = TextMapUtils.getSubTexts(key, this.texts.get());
            return texts;
        };
        return new TextPackage(key, subTextsSupplier, this.locale);
    }
}
