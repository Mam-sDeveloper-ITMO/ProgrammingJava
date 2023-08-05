package desktop;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import desktop.pages.AuthPage;
import lombok.Getter;
import lombok.Setter;
import textlocale.TextLocale;
import textlocale.loader.common.ResourcesLoader;
import textlocale.text.TextPackage;

public class App {
    @Getter
    @Setter
    private static String locale = "en";

    public static TextPackage texts;

    public static void main(String[] args) {
        initTexts();
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(App::createAndShowGUI);
    }

    private static void initTexts() {
        try {
            var codeSourceUrl = App.class.getProtectionDomain().getCodeSource().getLocation();
            var loader = new ResourcesLoader(codeSourceUrl, ".tl.json");
            texts = TextLocale.loadPackage("desktop", App::getLocale, loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        var frame = new JFrame(texts.getText("texts.main.title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var authPanel = new AuthPage();
        frame.add(authPanel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}