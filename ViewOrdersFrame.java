
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ViewOrdersFrame extends JFrame{
        public ViewOrdersFrame() {
        setTitle("View Orders");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Order ID", "Customer Name", "Product", "Quantity", "Total Price"};
        Object[][] data = {
                {"201", "Alice", "Laptop", 1, 799.99},
                {"202", "Bob", "Smartphone", 2, 999.98},
                {"203", "Charlie", "Headphones", 1, 99.99}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }
}
