package GUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import model.Cart;
import model.CartItem;
import model.EStore;
import model.Order;

public class CheckoutFrame extends JFrame {
        public CheckoutFrame() {
        setTitle("Checkout");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Cart cart = EStore.currentCart;
        if (cart == null || cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

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

        JButton confirmButton = new JButton("Confirm Order");
        confirmButton.addActionListener(e -> {
            Order order = cart.checkout(); // uses the checkout() method we added earlier
            JOptionPane.showMessageDialog(this,
                    "Order placed!\nOrder ID: " + order.getOrderId() +
                            "\nTotal: $" + order.getTotalAmount(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(confirmButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
