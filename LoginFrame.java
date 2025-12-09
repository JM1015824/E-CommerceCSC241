

import java.awt.*;
import java.util.List;
import javax.swing.*;



public class LoginFrame extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private UserStorage userStorage;
    private OrderInventory orderInventory;

    public LoginFrame(UserStorage userStorage, OrderInventory orderInventory) {
        this.userStorage = userStorage;
        this.orderInventory = orderInventory;

        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> authenticate());
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> openRegisterFrame());
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void openRegisterFrame() {
        new RegisterGUI(userStorage).setVisible(true);  // RegisterGUI
    }

    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded accounts
        if (username.equals("admin") && password.equals("password")) {
            dispose();
            new AdminDashboard(userStorage, new OrderInventory());
            return;
        } else if (username.equals("customer") && password.equals("password")) {
            EStore.currentCart = new Cart(1, "customer");
            new CustomerDashboard(userStorage, "customer");
            dispose();
            return;
        }

        // UserStorage accounts
        User user = userStorage.findUserByName(username);
        if (user != null && user.getPassword().equals(password)) {
            dispose();
            if (user.getRole().equalsIgnoreCase("admin")) {
                new AdminDashboard(userStorage, new OrderInventory());
            } else {
                EStore.currentCart = new Cart(1, user.getName());
                new CustomerDashboard(userStorage, user.getName());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
