import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private UserStorage userStorage;
    private LoginManager loginManager;

    public LoginGUI(UserStorage userStorage, LoginManager loginManager) {
        this.userStorage = userStorage;
        this.loginManager = loginManager;

        setTitle("E-Commerce Login");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Username:");
        JTextField nameField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); 
        panel.add(loginButton);
        panel.add(new JLabel()); 
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String name = nameField.getText();
            String password = new String(passField.getPassword());
            User user = loginManager.loginUserManual(name, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + " (" + user.getRole() + ")");
                if (user.getRole().equals("admin")) {
                    new AdminDashboard().setVisible(true);
                } else {
                    new CustomerDashboard().setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterGUI(userStorage).setVisible(true);
        });
    }
}

