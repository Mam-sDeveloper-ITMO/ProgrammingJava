package desktop.pages;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.ui.FlatBorder;

import desktop.App;
import desktop.lib.BasePage;
import textlocale.text.TextSupplier;

public class AuthPage extends BasePage {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private TextSupplier t = App.texts.getPackage("texts.auth")::getText;

    public AuthPage() {
        var authBox = new JPanel(new GridBagLayout());
        authBox.setBorder(BorderFactory.createCompoundBorder(
                new FlatBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;

        var authTitle = new JLabel(t.getText("title"));
        authTitle.putClientProperty("FlatLaf.styleClass", "h2");
        authTitle.setHorizontalAlignment(JLabel.CENTER);
        authTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        authBox.add(authTitle, gbc);

        var usernameLabel = new JLabel(t.getText("login.title"));
        gbc.gridwidth = 1;
        gbc.gridy++;
        authBox.add(usernameLabel, gbc);

        var passwordLabel = new JLabel(t.getText("password.title"));
        gbc.gridy++;
        authBox.add(passwordLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx++;
        gbc.gridy = 1;
        authBox.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridy++;
        authBox.add(passwordField, gbc);

        var loginButton = new JButton(t.getText("loginButton"));
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

    private void login(ActionEvent e) {
        var username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        var password = new String(passwordChars);

        if (username.equals("admin") && password.equals("password")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
        }

        usernameField.setText("");
        passwordField.setText("");
    }
}
