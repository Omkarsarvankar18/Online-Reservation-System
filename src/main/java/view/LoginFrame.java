
package view;

import javax.swing.*;

import model.User;

import java.awt.*;


import service.AuthService;


public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private AuthService authService = new AuthService();

    public LoginFrame() {

        setTitle("Online Reservation System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Heading
        JLabel heading = new JLabel("User Login");
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        // Add components
        panel.add(heading);
        panel.add(new JLabel(""));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        // Login button action
        loginButton.addActionListener(e -> loginUser());

        setVisible(true);
    }

    private void loginUser() {

        String username = usernameField.getText().trim();

        String password = new String(passwordField.getPassword());

        User user = authService.userLogin(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful! Welcome " + user.getUsername()
            );

            dispose();

            new DashboardFrame(user.getUsername());

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!"
            );
        }
    }
}
