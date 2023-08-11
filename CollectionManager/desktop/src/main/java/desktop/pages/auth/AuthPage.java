package desktop.pages.auth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import adapter.Adapter;
import desktop.App;
import desktop.lib.BasePage;
import desktop.lib.Config;
import textlocale.text.TextSupplier;

public class AuthPage extends BasePage {
    private Adapter authAdapter;

    private TextSupplier ts = App.texts.getPackage("texts.auth")::getText;

    public AuthPage() {
        super("auth");
        initAuthAdapter();
    }

    @Override
    public void beforeShow() {
        var cardsPanel = new CardsPanel(authAdapter);
        cardsPanel.showSignInCard();

        this.setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(cardsPanel, gbc);
    }

    private void initAuthAdapter() {
        try {
            authAdapter = new Adapter(Config.authServiceHost, Config.authServicePort);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }
    }
}
