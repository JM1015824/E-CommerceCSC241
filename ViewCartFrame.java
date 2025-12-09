import java.awt.*;
import java.util.List;
import javax.swing.*;


public class ViewCartFrame extends JFrame{
    private String customerName;


    public ViewCartFrame(String customerName) {
        this.customerName = customerName;
            
        setTitle("View Cart");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Cart cart = EStore.currentCart;
        List<CartItem> items = cart.getItems();
        Object[][] data = new Object[items.size()][5];
        String[] columnNames = {"Product ID", "Name", "Quantity", "Price", "Total Price"};

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            data[i][0] = item.getProduct().getId();
            data[i][1] = item.getProduct().getName();
            data[i][2] = item.getQuantity();
            data[i][3] = item.getProduct().getPrice();
            data[i][4] = item.getTotalPrice();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
       
        JButton removeButton = new JButton("Remove Selected Item");
        JButton couponButton = new JButton("Apply coupon");
        JButton checkoutButton = new JButton("Checkout");

        removeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String productId = (String) table.getValueAt(selectedRow, 0);
                cart.removeProduct(productId);
                dispose();
                new ViewCartFrame(customerName);
            } else {
                JOptionPane.showMessageDialog(this, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }); 
        
         couponButton.addActionListener(e -> {
            String code = JOptionPane.showInputDialog("Enter coupon code:");
            if (code == null || code.isEmpty()) return;

            Coupon coupon = CouponStorage.find(code);

            if (coupon == null) {
                JOptionPane.showMessageDialog(this, "Invalid or expired coupon.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double discountedTotal = cart.applyCoupon(coupon);

            JOptionPane.showMessageDialog(this, "Coupon applied!\nNew total = $" + discountedTotal,"Success", JOptionPane.INFORMATION_MESSAGE);
        });


        checkoutButton.addActionListener(e -> {
            if (cart.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Order newOrder = EStore.orderInventory.placeOrder(customerName, cart.getItems());
            JOptionPane.showMessageDialog(this, 
                "Order placed successfully! Order ID: " + newOrder.getOrderId(),
                 "Success", JOptionPane.INFORMATION_MESSAGE);
            EStore.currentCart = new Cart(1, customerName);
            dispose();
        });
       
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(couponButton);
        buttonPanel.add(checkoutButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
                
        setVisible(true);
    }
}
