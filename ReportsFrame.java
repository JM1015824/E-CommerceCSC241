
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

class ReportsFrame extends JFrame {
    private JTable reportTable;
    private JButton updateStatusButton;
    private JTextField orderIdField;
    private JComboBox<String> statusCombo;

    public ReportsFrame() {
        setTitle("Reports");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Build report data
        String[] columnNames = {"Order ID", "Customer", "Total Amount", "Status"};
        List<Order> orders = EStore.orderInventory.getOrders();
        Object[][] data = new Object[orders.size()][4];

        double totalRevenue = 0;
        Map<String, Integer> productFrequency = new HashMap<>();

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            data[i][0] = o.getOrderId();
            data[i][1] = o.getCustomerName();
            data[i][2] = o.getTotalAmount();
            data[i][3] = o.getStatus();

            totalRevenue += o.getTotalAmount();

            // Count product frequency
            for (CartItem item : o.getItems()) {
                productFrequency.put(item.getProduct().getName(),
                        productFrequency.getOrDefault(item.getProduct().getName(), 0) + item.getQuantity());
            }
        }

        reportTable = new JTable(data, columnNames);
        panel.add(new JScrollPane(reportTable), BorderLayout.CENTER);

        // Summary area
        JTextArea summary = new JTextArea();
        summary.setEditable(false);
        summary.append("Total Orders: " + orders.size() + "\n");
        summary.append("Total Revenue: $" + totalRevenue + "\n");

        // Find most frequently ordered product
        String topProduct = productFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("None");
        summary.append("Most Frequently Ordered Product: " + topProduct + "\n");

        // Out-of-stock products
        summary.append("Out of Stock Products:\n");
        for (Product p : EStore.productStorage.getAllProducts()) {
            if (p.getQuantity() == 0) {
                summary.append(" - " + p.getName() + "\n");
            }
        }

        panel.add(summary, BorderLayout.SOUTH);

        // Status update controls
        JPanel updatePanel = new JPanel(new FlowLayout());
        orderIdField = new JTextField(5);
        statusCombo = new JComboBox<>(new String[]{"PROCESSING", "SHIPPED", "DELIVERED"});
        updateStatusButton = new JButton("Update Status");

        updateStatusButton.addActionListener(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                String newStatus = (String) statusCombo.getSelectedItem();
                EStore.orderInventory.updateOrderStatus(orderId, Order.Status.valueOf(newStatus));
                JOptionPane.showMessageDialog(this, "Order status updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updatePanel.add(new JLabel("Order ID:"));
        updatePanel.add(orderIdField);
        updatePanel.add(new JLabel("New Status:"));
        updatePanel.add(statusCombo);
        updatePanel.add(updateStatusButton);

        panel.add(updatePanel, BorderLayout.NORTH);

        add(panel);
        setVisible(true);
    }
}
