package GUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import model.Cart;
import model.CartItem;
import model.EStore;
import model.Order;

class CheckoutFrame extends JFrame {
    private JTextField areaCodeField;
    private JTextField couponField;
    private JLabel totalLabel;

    public CheckoutFrame() {
        setTitle("Checkout");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Cart cart = EStore.currentCart;
        if (cart == null || cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        // Table of cart items
        String[] columnNames = {"Product ID", "Name", "Quantity", "Price", "Total"};
        List<CartItem> items = cart.getItems();
        Object[][] data = new Object[items.size()][5];
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

        JPanel bottomPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bottomPanel.add(new JLabel("Area Code:"));
        areaCodeField = new JTextField();
        bottomPanel.add(areaCodeField);

        bottomPanel.add(new JLabel("Coupon Code:"));
        couponField = new JTextField();
        bottomPanel.add(couponField);

        totalLabel = new JLabel("Total: $" + cart.getTotalAmount());
        bottomPanel.add(totalLabel);

        JButton confirmButton = new JButton("Confirm Order");
        bottomPanel.add(confirmButton);

        confirmButton.addActionListener(e -> {
            try{
                String areaCode = areaCodeField.getText().trim();
                double taxRate = getTaxRate(areaCode);
                double subtotal = cart.getTotalAmount();
                double taxedTotal = subtotal + (subtotal * taxRate);

                // Coupon application
                String code = couponField.getText().trim();
                Coupon coupon = CouponStorage.find(code);
                double finalTotal = taxedTotal;
                if (coupon != null) {
                    finalTotal = taxedTotal - (taxedTotal * (coupon.getPercentOff() / 100));
                    finalTotal = Math.round(finalTotal * 100.0) / 100.0;
                }

                // Checkout
                Order order = cart.checkout();
                JOptionPane.showMessageDialog(this,
                        "Order placed!\nOrder ID: " + order.getOrderId() +
                                "\nSubtotal: $" + subtotal +
                                "\nTax (" + (taxRate * 100) + "%): $" + (subtotal * taxRate) +
                                (coupon != null ? "\nCoupon Applied: " + coupon.getCode() : "") +
                                "\nFinal Total: $" + finalTotal,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Example tax lookup by area code
    private double getTaxRate(String areaCode) {
        switch (areaCode) {
            case "10001": return 0.088; // NYC
            case "19104": return 0.06;  // Philadelphia
            case "90001": return 0.095; // Los Angeles
            default: return 0.05;       // fallback
        }
    }
}
