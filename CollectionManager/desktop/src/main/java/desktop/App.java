package desktop;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import desktop.lib.BasePage;
import desktop.pages.auth.AuthPage;
import desktop.pages.main.MainPage;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
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

    public static Context context = new Context();

    private static JPanel pagesPanel;

    private static CardLayout pagesLayout;

    private static Map<String, BasePage> pages = new HashMap<>();

    public static void main(String[] args) {
        initTexts();
        FlatMacDarkLaf.setup();
        IconFontSwing.register(FontAwesome.getIconFont());
        SwingUtilities.invokeLater(App::createAndShowGUI);
    }

    public static void includePage(BasePage page) {
        pages.put(page.getName(), page);
        pagesPanel.add(page, page.getName());
    }

    public static void showPage(String name) {
        var page = pages.get(name);
        if (page == null) {
            return;
        }

        page.beforeShow();
        pagesLayout.show(pagesPanel, page.getName());
        page.afterShow();
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        pagesLayout = new CardLayout();
        pagesPanel = new JPanel(pagesLayout);
        frame.getContentPane().add(pagesPanel);

        initPages();
        showPage("auth");

        frame.setVisible(true);
    }

    private static void initPages() {
        includePage(new AuthPage());
        includePage(new MainPage());
    }
}
