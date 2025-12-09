


import java.awt.*;
import java.util.List;
import javax.swing.*;


public class UpdateOrderStatusFrame extends JFrame{
    
    private JTextField orderIdField, customerField, totalField;
    private JComboBox<String> statusDropdown;
    private JButton updateButton, searchButton;

    private Order currentOrder = null;
    private OrderInventory orderInventory;


    public UpdateOrderStatusFrame(OrderInventory orderInventory) {
        this.orderInventory = orderInventory;

        setTitle("Update Order Status");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        panel.add(orderIdField);

        panel.add(new Label("Search"));
        searchButton = new JButton("Search");
        panel.add(searchButton);

        panel.add(new JLabel("Customer:"));
        customerField = new JTextField();
        customerField.setEditable(false);
        panel.add(customerField);

        panel.add(new JLabel("Total Amount:"));
        totalField = new JTextField();
        totalField.setEditable(false);
        panel.add(totalField);

        panel.add(new JLabel("New Status:"));
        statusDropdown = new JComboBox<>(new String[]{"PROCESSED", "SHIPPED", "DELIVERED"});
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
        try {
            int id = Integer.parseInt(orderIdField.getText());
            currentOrder = EStore.orderInventory.getOrderById(id);

            if (currentOrder == null) {
                JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                customerField.setText(currentOrder.getCustomerName());
                totalField.setText(String.valueOf(currentOrder.getTotalAmount()));   
                statusDropdown.setSelectedItem(currentOrder.getStatus().name());
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

        String selected = (String) statusDropdown.getSelectedItem();

        Order.Status newStatus = Order.Status.valueOf(selected.toUpperCase());

        currentOrder.setStatus(newStatus);


        JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
