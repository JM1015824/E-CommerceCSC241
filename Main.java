import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UserStorage userStorage = new UserStorage();
        ProductStorage productStorage = new ProductStorage();
        LoginManager loginManager = new LoginManager(userStorage);

        // Add sample products
        productStorage.addProduct(new Product("P001", "Smartphone", "Electronics", 699.99, 15));
        productStorage.addProduct(new Product("P002", "Blender", "Kitchen", 49.99, 8));
        productStorage.addProduct(new Product("P003", "Desk Lamp", "Home", 29.99, 20));

        // Add sample users (IDs are internal only)
        userStorage.addUser(new User("C01", "Jillian", "customer", "pass123"));
        userStorage.addUser(new User("A01", "Adam", "admin", "admin123"));
        userStorage.addUser(new User("C02", "Bob", "customer", "bobpass"));

        // List all users
        System.out.println("=== All Users ===");
        for (User user : userStorage.getAllUsers()) {
            System.out.println(user);
        }

        // List all products
        System.out.println("\n=== All Products ===");
        for (Product product : productStorage.getAllProducts()) {
            System.out.println(product);
        }

        // Launch the login GUI
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(userStorage, loginManager).setVisible(true);
        });
    }
}
