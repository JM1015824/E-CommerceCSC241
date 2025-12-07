


import java.awt.*;
import java.util.List;
import javax.swing.*;


public class UpdateOrderStatusFrame extends JFrame{
    
    private JTextField orderIdField, customerField, totaField;
    private JComboBox<String> statusDropdown;
    private JButton updateButton, searchButton;

    private Order currentOrder = null;


    public UpdateOrderStatusFrame() {
        setTitle("Update Order Status");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Order ID:"));
        JTextField orderIdField = new JTextField();
        panel.add(orderIdField);

        panel.add(new Label("Search"));
        JButton searchButton = new JButton("Search");
        panel.add(searchButton);

        panel.add(new JLabel("Customer:"));
        JTextField customerField = new JTextField();
        customerField.setEditable(false);
        panel.add(customerField);

        panel.add(new JLabel("Total Amount:"));
        JTextField totalField = new JTextField();
        totalField.setEditable(false);
        panel.add(totalField);

        panel.add(new JLabel("New Status:"));
        statusDropdown = new JComboBox<>(new String[]{"Pending", "Shipped", "Delivered", "Cancelled"});
        panel.add(statusDropdown);  



        JButton updateButton = new JButton("Update");
        panel.add(updateButton);

        panel.add(new JLabel("")); // Placeholder

        searchButton.addActionListener(e -> searchOrder());
        updateButton.addActionListener(e -> updateStatus());

        add(panel);
        setVisible(true);
    }

    private void searchOrder() {
        String orderId = orderIdField.getText();
        try {
            int id = Integer.parseInt(orderId);
            currentOrder = EStore.orderInventory.getOrderById(id);
            if (currentOrder == null) {
                JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                customerField.setText(currentOrder.getCustomerName());
                totaField.setText(String.valueOf(currentOrder.getTotalAmount()));   
                statusDropdown.setSelectedItem(currentOrder.getStatus());
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "No order selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentOrder.getStatus();
        JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
