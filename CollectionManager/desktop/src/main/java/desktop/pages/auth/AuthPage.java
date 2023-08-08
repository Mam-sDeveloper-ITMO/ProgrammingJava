package desktop.pages.auth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.ui.FlatBorder;

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthToken;
import desktop.App;
import desktop.lib.BasePage;
import desktop.lib.Config;
import desktop.lib.TokenStore;
import server.responses.Response;
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
        var signInCard = new SignInCard(authAdapter);

        setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(signInCard);
    }

    private void initAuthAdapter() {
        try {
            authAdapter = new Adapter(Config.authServiceHost, Config.authServicePort);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }
    }
}
