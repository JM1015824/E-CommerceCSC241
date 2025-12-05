import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UserStorage userStorage = new UserStorage();
        ProductStorage productStorage = new ProductStorage();

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

       // Create a cart and add items
        Cart cart = new Cart(1, "John Doe");
        Product product1 = new Product("P001", "Laptop", "Electronics", 999.99, 10);
        Product product2 = new Product("P002", "Smartphone", "Electronics", 499.99, 20);    
    
        cart.addProduct(product1, 1);
        cart.addProduct(product2, 2);
    
        // Display cart details
        System.out.println("Cart Details: " + cart.getItems());
    
        // Calculate total amount
        System.out.println("Cart Total Amount: " + cart.getTotalAmount());
    
        // Create an order from the cart
        Order order = new Order(1001, "John Doe", cart.getItems(), cart.getTotalAmount());
        System.out.println("Order Details: " + order);
    
        // Manage orders
        OrderInventory orderInventory = new OrderInventory();
        orderInventory.addOrder(order);
        System.out.println("All Orders: " + orderInventory.getOrders());
        
        // Update order status
        orderInventory.updateOrderStatus(1001, Order.Status.SHIPPED);
        
        // Launch the login GUI
        SwingUtilities.invokeLater(() -> new LoginFrame());

    }
}
