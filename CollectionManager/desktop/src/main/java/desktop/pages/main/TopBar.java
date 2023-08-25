package desktop.pages.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import desktop.App;
import desktop.lib.TokenStore;
import desktop.pages.main.lang.LangSelect;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class TopBar extends JPanel {
    private String username;

    private ContentSwitch contentSwitch;

    private LangSelect langSelect;

    public TopBar(String username) {
        this.username = username;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Language select
        langSelect = new LangSelect(App.context.getLangs(), App.context.getLang());
        add(langSelect, BorderLayout.WEST);

        // Content switcher
        contentSwitch = new ContentSwitch();
        add(contentSwitch, BorderLayout.CENTER);

        // User container
        var userContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        // Username label
        var usernameLabel = new JLabel(username);
        usernameLabel.putClientProperty("FlatLaf.styleClass", "h3");
        userContainer.add(usernameLabel);

        // Logout button
        var logoutIcon = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 15, Color.WHITE);
        var logoutButton = new JButton(logoutIcon);
        logoutButton.addActionListener(e -> {
            logout();
        });
        userContainer.add(logoutButton);

        add(userContainer, BorderLayout.EAST);

        // Bottom separator
        add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.PAGE_END);
    }

    private void logout() {
        try {
            TokenStore.deleteToken();
        } catch (Exception e) {
        }
        App.showPage("auth");
    }

    public void setOpenTable(Runnable r) {
        contentSwitch.setShowTable(r);
    }

    public void setOpenViz(Runnable r) {
        contentSwitch.setShowViz(r);
    }

    public void setLangSelectHandler(Consumer<String> handler) {
        langSelect.setLangSelectHandler(handler);
    }
}
