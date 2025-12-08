

import java.awt.*;
import java.util.List;
import javax.swing.*;


public class BrowseProductsFrame extends JFrame{
        public BrowseProductsFrame() {
        setTitle("Browse Products");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Product ID", "Name", "Price", "Category", "Quantity", "Average Rating"};
        List<Product> products = EStore.productStorage.getAllProducts();
        Object[][] data = new Object[products.size()][5];

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getName();
            data[i][2] = p.getPrice();
            data[i][3] = p.getCategory();
            data[i][4] = p.getQuantity();
            data[i][5] = String.format("%.1f", p.getAverageRating();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addToCartButton = new JButton("Add to Cart");
        JButton rateProductButton = new Jbutton("Rate Product");
                
        addToCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String productId = (String) table.getValueAt(selectedRow, 0);
                Product selectedProduct = EStore.productStorage.getProductById(productId);
                if (selectedProduct != null) {
                    String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to add to cart:");
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        if (quantity > 0 && quantity <= selectedProduct.getQuantity()) {
                            EStore.currentCart.addProduct(selectedProduct, quantity);
                            JOptionPane.showMessageDialog(this, "Product added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No product selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }); 

        rateProductButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a product first.");
                return;
            }

            String productId = table.getValueAt(row, 0).toString();
            Product p = EStore.productStorage.getProductById(productId);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Error: Product not found.");
                return;
            }

            String ratingStr = JOptionPane.showInputDialog("Enter rating (1–5):");

            try {
                int rating = Integer.parseInt(ratingStr);
                p.addRating(rating); 

                JOptionPane.showMessageDialog(this,
                    "Rating saved!\nNew Avg Rating: " + String.format("%.1f", p.getAverageRating()));

                dispose();
                new BrowseProductsFrame();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid rating.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        buttonPanel.add(rateProductButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
}
