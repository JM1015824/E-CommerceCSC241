

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class SearchProductsFrame extends JFrame{
        public SearchProductsFrame() {
        setTitle("Search Products");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Enter Product Name:"));
        JTextField searchField = new JTextField();
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        panel.add(searchButton);

        searchButton.addActionListener(e -> {
            List<Product> results = EStore.productStorage.searchByName(searchField.getText());
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No products found", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder("Search Results:\n");
                for (Product p : results) {
                    message.append("ID: ").append(p.getId())
                            .append(", Name: ").append(p.getName())
                            .append(", Price: $").append(p.getPrice())
                            .append(", Category: ").append(p.getCategory())
                            .append(", Quantity: ").append(p.getQuantity())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }
}
