

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AddProductFrame extends JFrame {
        private JTextField productIdField, productNameField, productPriceField, productCategoryField, productQuantityField;
    private JButton saveButton, cancelButton;

    public AddProductFrame() {
        setTitle("Add Product");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add product form components here
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        panel.add(productIdField);

        panel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        panel.add(productNameField);

        panel.add(new JLabel("Product Price:"));
        productPriceField = new JTextField();
        panel.add(productPriceField);

        panel.add(new JLabel("Product Category:"));
        productCategoryField = new JTextField();
        panel.add(productCategoryField);

        panel.add(new JLabel("Product Quantity:"));
        productQuantityField = new JTextField();
        panel.add(productQuantityField);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());


        add(panel);
        setVisible(true);
    }

    private void saveProduct() {
        try{
            String id = productIdField.getText();
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            String category = productCategoryField.getText();
            int quantity = Integer.parseInt(productQuantityField.getText());

            Product newProduct = new Product(id, name, category, price, quantity);
            EStore.productStorage.addProduct(newProduct);

            JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price or quantity", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
