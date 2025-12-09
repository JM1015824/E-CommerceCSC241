import java.awt.*;
import java.util.List;
import javax.swing.*;


public class AdminDashboard extends JFrame {
    private UserStorage userStorage;
    private OrderInventory orderInventory;

    public AdminDashboard(UserStorage userStorage, OrderInventory orderInventory) {
        this.userStorage = userStorage;
        this.orderInventory = orderInventory;

        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addProduct = new JButton("Add Product");
        JButton updateProduct = new JButton("Update/Delete Product");
        JButton viewProducts = new JButton("View All Products");
        JButton viewOrders = new JButton("View Orders");
        JButton updateOrderStatus = new JButton("Update Order Status");
        JButton reports = new JButton("Reports");
        JButton logout = new JButton("Logout");


        addProduct.addActionListener(e -> new AddProductFrame());
        updateProduct.addActionListener(e -> new UpdateDeleteProductFrame());
        viewProducts.addActionListener(e -> new ViewProductsFrame());
        viewOrders.addActionListener(e -> new ViewOrdersFrame(orderInventory));
        updateOrderStatus.addActionListener(e -> new UpdateOrderStatusFrame(orderInventory));
        reports.addActionListener(e -> new ReportsFrame());
        
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame(userStorage, EStore.orderInventory);

        });

        panel.add(addProduct);
        panel.add(updateProduct);
        panel.add(viewProducts);
        panel.add(viewOrders);
        panel.add(updateOrderStatus);
        panel.add(reports);
        panel.add(logout);

        add(panel);
        setVisible(true);

    }
}
