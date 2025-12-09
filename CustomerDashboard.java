

import java.awt.*;
import java.util.List;
import javax.swing.*;


public class CustomerDashboard extends JFrame{
        private String customerName;
        private UserStorage userStorage;

    public CustomerDashboard(UserStorage userStorage, String customerName) {
        this.userStorage = userStorage;
        this.customerName = customerName;

        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton browseProducts = new JButton("Browse Products");
        JButton searchProducts = new JButton("Search Products");
        JButton viewCart = new JButton("View Cart");
        JButton orderHistory = new JButton("Order History");
        JButton trackOrders = new JButton("Track Orders");
        JButton logout = new JButton("Logout");

        browseProducts.addActionListener(e -> new BrowseProductsFrame());
        searchProducts.addActionListener(e -> new SearchProductsFrame());
        viewCart.addActionListener(e -> new ViewCartFrame(customerName));
        orderHistory.addActionListener(e -> new OrderHistoryFrame(customerName));
        trackOrders.addActionListener(e -> new TrackOrdersFrame(customerName));
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame(userStorage, EStore.orderInventory);
        });

        panel.add(browseProducts);
        panel.add(searchProducts);
        panel.add(viewCart);
        panel.add(orderHistory);
        panel.add(trackOrders);
        panel.add(logout);

        add(panel);
        setVisible(true);

    }
}
