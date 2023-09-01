package desktop.pages.main.lang;

import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import lombok.Getter;

public class LangItem {
    @Getter
    private Icon icon;

    @Getter
    private String lang;

    @Getter
    private String langCode;

    public LangItem(String lang, String langCode) {
        this.lang = lang;
        this.langCode = langCode;
        this.icon = createImageIcon("/desktop/langs/" + langCode + ".png", lang, 16, 16);
    }

    protected ImageIcon createImageIcon(String path, String description, int width, int height) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            var icon = new ImageIcon(imgURL, description);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
