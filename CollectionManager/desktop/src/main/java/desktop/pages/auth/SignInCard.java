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

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthToken;
import authservice.api.StatusCodes;
import desktop.App;
import desktop.lib.TokenStore;
import desktop.lib.components.PasswordField;
import desktop.lib.components.TextField;
import server.responses.Response;
import textlocale.text.TextSupplier;

public class SignInCard extends JPanel {
    private TextSupplier ts = App.texts.getPackage("texts.auth")::getText;

    private TextField usernameField;
    private PasswordField passwordField;

    private Adapter authAdapter;
    private Runnable openSignUpCard;

    public SignInCard(Adapter authAdapter, Runnable openSignUp) {
        super(new GridBagLayout());
        this.authAdapter = authAdapter;
        this.openSignUpCard = openSignUp;
        init();
    }

    private void init() {
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;

        // Title
        var authTitle = new JLabel(ts.t("signInCard.title"));
        authTitle.putClientProperty("FlatLaf.styleClass", "h2");
        authTitle.setHorizontalAlignment(JLabel.CENTER);
        authTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(authTitle, gbc);

        // Username
        usernameField = new TextField(ts.t("signInCard.username.title"), 25);
        gbc.gridy++;
        this.add(usernameField, gbc);

        // Password
        passwordField = new PasswordField(ts.t("signInCard.password.title"), 25);
        gbc.gridy++;
        this.add(passwordField, gbc);

        // Sign in button
        var loginButton = new JButton(ts.t("signInCard.signInButton"));
        loginButton.addActionListener(this::signIn);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(loginButton, gbc);

        // Sign up button
        var signUpButton = new JButton(ts.t("signInCard.openSignUpButton"));
        signUpButton.setBackground(null);
        signUpButton.setHorizontalAlignment(JLabel.RIGHT);
        signUpButton.addActionListener(e -> openSignUpCard.run());
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(signUpButton, gbc);
    }

    private void signIn(ActionEvent event) {
        var username = usernameField.getTextField().getText();
        char[] passwordChars = passwordField.getPasswordField().getPassword();
        var password = new String(passwordChars);

        Map<String, Serializable> data = Map.of("login", username, "password", password);
        Response response;
        try {
            response = authAdapter.triggerServer("auth.login", data);
            if (response.getCode() == 200) {
                JOptionPane.showMessageDialog(this, ts.t("messages.signInSuccess"));
                try {
                    var token = (AuthToken) response.getData().get("token");
                    TokenStore.saveToken(token.getToken());
                    App.context.setUsername(username);
                    App.context.initCollectionManager(token);
                    App.showPage("main");
                    usernameField.getTextField().setText("");
                    passwordField.getPasswordField().setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                    ts.t("messages.signInError") + e.getMessage());
                }
            } else if (response.getCode() == StatusCodes.INCORRECT_LOGIN_OR_PASSWORD) {
                JOptionPane.showMessageDialog(this, ts.t("messages.invalidCredentials"));
            } else {
                JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
            }
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }
    }
}
