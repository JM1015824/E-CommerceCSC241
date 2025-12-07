

import java.awt.*;
import java.util.List;
import javax.swing.*;


public class ViewProductsFrame extends JFrame {
        public ViewProductsFrame() {
        setTitle("View All Products");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Product> products = EStore.productStorage.getAllProducts();

        String[] columnNames = {"Product ID", "Name", "Price", "Category", "Quantity"};
        Object[][] data = new Object[products.size()][5];


        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getName();
            data[i][2] = p.getPrice();
            data[i][3] = p.getCategory();
            data[i][4] = p.getQuantity();
        }


        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }
}
