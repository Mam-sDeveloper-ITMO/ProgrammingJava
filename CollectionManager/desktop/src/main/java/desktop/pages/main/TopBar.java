package desktop.pages.main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import desktop.App;
import desktop.lib.TokenStore;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class TopBar extends JPanel {
    private String username;

    public TopBar(String username) {
        this.username = username;
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Username label
        var usernameLabel = new JLabel(username);
        usernameLabel.putClientProperty("FlatLaf.styleClass", "h3");
        this.add(usernameLabel);

        // Logout button
        var logoutIcon = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 15, Color.WHITE);
        var logoutButton = new JButton(logoutIcon);
        logoutButton.addActionListener(e -> {
            logout();
        });
        this.add(logoutButton);
    }

    private void logout() {
        try {
            TokenStore.deleteToken();
        } catch (Exception e) {
        }
        App.showPage("auth");
    }
}
