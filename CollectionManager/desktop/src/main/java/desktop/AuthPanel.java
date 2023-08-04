package desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AuthPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AuthPanel() {
        setLayout(new GridBagLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Perform authentication logic here, e.g., check credentials against a database

                // For demonstration purposes, let's assume the username is "admin" and password
                // is "password"
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(AuthPanel.this, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(AuthPanel.this, "Invalid credentials. Try again.");
                }

                // Clear the fields after login attempt
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(usernameLabel, gbc);

        gbc.gridy++;
        add(passwordLabel, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.gridy++;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);
    }
}
