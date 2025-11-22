import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CustomerDashboard extends JFrame {

    private Cart cart;

    public CustomerDashboard(String customerName) {
        this.cart = new Cart(1, customerName);

        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("Welcome " + customerName + "!", SwingConstants.CENTER);

        JTextField couponField = new JTextField();
        JButton addItemBtn = new JButton("Add Test Item");
        JButton checkoutBtn = new JButton("Checkout");

        add(label);
        add(new JLabel("Enter Coupon Code:", SwingConstants.CENTER));
        add(couponField);
        add(addItemBtn);
        add(checkoutBtn);

        // test item button
        addItemBtn.addActionListener(e -> {
            Product p = new Product("1", "Sample", "Category", 10.0, 10);
            cart.addItem(p, 1);
            JOptionPane.showMessageDialog(this, "Added Sample Item ($10)");
        });

        // checkout button
        checkoutBtn.addActionListener(e -> {
            String code = couponField.getText();
            Coupon coupon = CouponStorage.find(code);

            double finalTotal = cart.applyCoupon(coupon);

            int orderId = (int)(Math.random() * 10000);

            Order order = new Order(orderId, customerName, cart.getItems(), finalTotal);

            OrderInventory inventory = new OrderInventory();
            inventory.addOrder(order);


            JOptionPane.showMessageDialog(this, 
                "Order Placed!\nTotal: $" + finalTotal + "\nOrder ID: " + orderId);
        });
    }
}

