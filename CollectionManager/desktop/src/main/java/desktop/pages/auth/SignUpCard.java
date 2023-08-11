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

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthToken;
import authservice.api.StatusCodes;
import desktop.App;
import desktop.lib.TokenStore;
import server.responses.Response;
import textlocale.text.TextSupplier;

public class SignUpCard extends JPanel {
    private TextSupplier ts = App.texts.getPackage("texts.auth")::getText;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private Adapter authAdapter;
    private Runnable openSignInCard;

    public SignUpCard(Adapter authAdapter, Runnable openSignInCard) {
        super(new GridBagLayout());
        this.authAdapter = authAdapter;
        this.openSignInCard = openSignInCard;
        init();
    }

    private void init() {
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;

        var authTitle = new JLabel(ts.t("signUpCard.title"));
        authTitle.putClientProperty("FlatLaf.styleClass", "h2");
        authTitle.setHorizontalAlignment(JLabel.CENTER);
        authTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(authTitle, gbc);

        var usernameLabel = new JLabel(ts.t("signUpCard.username.title"));
        gbc.gridwidth = 1;
        gbc.gridy++;
        this.add(usernameLabel, gbc);

        var passwordLabel = new JLabel(ts.t("signUpCard.password.title"));
        gbc.gridy++;
        this.add(passwordLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx++;
        gbc.gridy = 1;
        this.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridy++;
        this.add(passwordField, gbc);

        var loginButton = new JButton(ts.t("signUpCard.signUpButton"));
        loginButton.addActionListener(this::signUp);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(loginButton, gbc);

        var signInButton = new JButton(ts.t("signUpCard.openSignInButton"));
        signInButton.setBackground(null);
        signInButton.setHorizontalAlignment(JLabel.RIGHT);
        signInButton.addActionListener(e -> openSignInCard.run());
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(signInButton, gbc);
    }

    private void signUp(ActionEvent event) {
        var username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        var password = new String(passwordChars);

        Map<String, Serializable> data = Map.of("login", username, "password", password);
        Response response;
        try {
            response = authAdapter.triggerServer("auth.register", data);
            if (response.getCode() == 200) {
                JOptionPane.showMessageDialog(this, ts.t("messages.signUpSuccess"));
                try {
                    var token = (AuthToken) response.getData().get("token");
                    TokenStore.saveToken(token.getToken());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, ts.t("messages.tokenSaveError") + e.getMessage());
                }
                App.showPage("table");
            } else if (response.getCode() == StatusCodes.LOGIN_ALREADY_EXISTS) {
                JOptionPane.showMessageDialog(this, ts.t("messages.usernameTaken"));
            } else {
                JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
            }
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            JOptionPane.showMessageDialog(this, ts.t("messages.connectionError"));
        }

        usernameField.setText("");
        passwordField.setText("");
    }
}
