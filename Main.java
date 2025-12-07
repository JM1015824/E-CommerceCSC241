import java.util.Calendar;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
    UserStorage userStorage = new UserStorage();
    ProductStorage productStorage = new ProductStorage();

    // Add sample products
    EStore.productStorage.addProduct(new Product("P001", "Smartphone", "Electronics", 699.99, 15));
    EStore.productStorage.addProduct(new Product("P002", "Blender", "Kitchen", 49.99, 0));
    EStore.productStorage.addProduct(new Product("P003", "Desk Lamp", "Home", 29.99, 20));

    // Add sample users (IDs are internal only)
    userStorage.addUser(new User("C01", "Jillian", "customer", "pass123"));
    userStorage.addUser(new User("A01", "Adam", "admin", "admin123"));
    userStorage.addUser(new User("C02", "Bob", "customer", "bobpass"));

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, 30);
    CouponStorage.addCoupon(new Coupon("FREE",100, cal.getTime()));
    CouponStorage.addCoupon(new Coupon("HALF",100, cal.getTime()));

    // List all users
    System.out.println("=== All Users ===");
    for (User user : userStorage.getAllUsers()) {
        System.out.println(user);
    }

    // List all products
    System.out.println("\n=== All Products ===");
    for (Product product : EStore.productStorage.getAllProducts()) {
        System.out.println(product);
    }
    // Launch the login GUI
    SwingUtilities.invokeLater(() -> new LoginFrame(userStorage));
    }
}
