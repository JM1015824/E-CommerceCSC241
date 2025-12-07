


import java.awt.*;
import java.util.List;
import javax.swing.*;


public class UpdateDeleteProductFrame extends JFrame{
        public UpdateDeleteProductFrame() {
        setTitle("Update/Delete Product");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Enter Product ID:"));
        JTextField productIdField = new JTextField();
        panel.add(productIdField);

        JButton searchButton = new JButton("Search");
        panel.add(searchButton);
        panel.add(new JLabel("")); // Placeholder

        panel.add(new JLabel("Product Name:"));
        JTextField productNameField = new JTextField();
        panel.add(productNameField);

        panel.add(new JLabel("Product Price:"));
        JTextField productPriceField = new JTextField();
        panel.add(productPriceField);

        panel.add(new JLabel("Product Category:"));
        JTextField productCategoryField = new JTextField();
        panel.add(productCategoryField);

        panel.add(new JLabel("Product Quantity:"));
        JTextField productQuantityField = new JTextField();
        panel.add(productQuantityField);

        JButton updateButton = new JButton("Update");
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        panel.add(deleteButton);

        searchButton.addActionListener(e -> {
            String productId = productIdField.getText();
            Product product = EStore.productStorage.getProductById(productId);
            if (product != null) {
                productNameField.setText(product.getName());
                productPriceField.setText(String.valueOf(product.getPrice()));
                productCategoryField.setText(product.getCategory());
                productQuantityField.setText(String.valueOf(product.getQuantity()));
            } else {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            String productId = productIdField.getText();
            Product product = EStore.productStorage.getProductById(productId);
            if (product != null) {
                product.setName(productNameField.getText());
                product.setPrice(Double.parseDouble(productPriceField.getText()));
                product.setCategory(productCategoryField.getText());
                product.setQuantity(Integer.parseInt(productQuantityField.getText()));
                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            String id = productIdField.getText();
            Product product = EStore.productStorage.getProductById(id);

            if (product == null) {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            EStore.productStorage.removeProduct(id);
            JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            productNameField.setText("");
            productPriceField.setText("");
            productCategoryField.setText("");
            productQuantityField.setText("");

        });

        add(panel);
        setVisible(true);
    }
}
