

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {
    private UserStorage userStorage;

    public RegisterGUI(UserStorage userStorage) {
        this.userStorage = userStorage;

        setTitle("User Registration");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Username:");
        JTextField nameField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            // Always assign role as "customer"
            User newUser = new User("", name, "customer", password);
            userStorage.addUser(newUser);

            JOptionPane.showMessageDialog(this,
                    "Registration complete\nWelcome " + name);
            dispose();
        });
    }
}
