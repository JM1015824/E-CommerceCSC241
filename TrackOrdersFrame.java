


import java.awt.*;
import java.util.List;
import javax.swing.*;



public class TrackOrdersFrame extends JFrame{
        public TrackOrdersFrame() {
        setTitle("Track Orders");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Enter Order ID:"));
        JTextField orderIdField = new JTextField();
        panel.add(orderIdField);

        JButton trackButton = new JButton("Track");
        panel.add(trackButton);

        trackButton.addActionListener(e -> {
            String orderId = orderIdField.getText();

            try {
                int id = Integer.parseInt(orderId);
                Order ord = EStore.orderInventory.getOrderById(id);

                if (ord != null) {
                    String message = "Order ID: " + ord.getOrderId() +
                            "\nCustomer Name: " + ord.getCustomerName() +
                            "\nTotal Amount: $" + ord.getTotalAmount() +
                            "\nStatus: " + ord.getStatus() +
                            "\nOrder Date: " + ord.getTimestamp();
                    JOptionPane.showMessageDialog(this, message, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }
}
