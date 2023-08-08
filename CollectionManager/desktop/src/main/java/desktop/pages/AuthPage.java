package desktop.pages;

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

    private JTextField usernameField;
    private JPasswordField passwordField;

    private TextSupplier ts = App.texts.getPackage("texts.auth")::getText;

    public AuthPage() {
        super("auth");
        initAuthAdapter();
    }

    @Override
    public void beforeShow() {
        var authBox = new JPanel(new GridBagLayout());
        authBox.setBorder(
                BorderFactory.createCompoundBorder(new FlatBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;

        var authTitle = new JLabel(ts.t("title"));
        authTitle.putClientProperty("FlatLaf.styleClass", "h2");
        authTitle.setHorizontalAlignment(JLabel.CENTER);
        authTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        authBox.add(authTitle, gbc);

        var usernameLabel = new JLabel(ts.t("username.title"));
        gbc.gridwidth = 1;
        gbc.gridy++;
        authBox.add(usernameLabel, gbc);

        var passwordLabel = new JLabel(ts.t("password.title"));
        gbc.gridy++;
        authBox.add(passwordLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx++;
        gbc.gridy = 1;
        authBox.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridy++;
        authBox.add(passwordField, gbc);

        var loginButton = new JButton(ts.t("signInButton"));
        loginButton.addActionListener(this::login);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        authBox.add(loginButton, gbc);

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(authBox);
    }

    private void initAuthAdapter() {
        try {
            authAdapter = new Adapter(Config.authServiceHost, Config.authServicePort);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }
    }

    private void login(ActionEvent event) {
        var username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        var password = new String(passwordChars);

        Map<String, Serializable> data = Map.of("login", username, "password", password);
        Response response;
        try {
            response = authAdapter.triggerServer("auth.login", data);
            if (response.getCode() == 200) {
                JOptionPane.showMessageDialog(this, ts.t("messages.loginSuccess"));
                try {
                    var token = (AuthToken) response.getData().get("token");
                    TokenStore.saveToken(token.getToken());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, ts.t("messages.tokenSaveError") + e.getMessage());
                }
            } else if (response.getCode() == authservice.api.StatusCodes.INCORRECT_LOGIN_OR_PASSWORD) {
                JOptionPane.showMessageDialog(this, ts.t("messages.invalidCredentials"));
            } else {
                JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
            }
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }

        usernameField.setText("");
        passwordField.setText("");
        App.showPage("table");
    }
}
