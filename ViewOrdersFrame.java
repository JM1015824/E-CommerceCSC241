
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ViewOrdersFrame extends JFrame{
        public ViewOrdersFrame(OrderInventory orderInventory) {
        setTitle("View Orders");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Order ID", "Customer Name", "Product", "Quantity", "Total Price", "Status"};

        List<Order> orders = EStore.orderInventory.getOrders();


        Object[][] data = new Object[orders.size()][6];
        for(int i = 0; i < orders.size(); i++){
                Order o = orders.get(i);
                data[i][0] = o.getOrderId();
                data[i][1] = o.getCustomerName();
          
                StringBuilder products = new StringBuilder();
                StringBuilder quantities = new StringBuilder();

                for(CartItem item : o.getItems()){
                        products.append(item.getProduct().getName()).append(", ");
                        quantities.append(item.getQuantity()).append(", ");
                }

                //Remove trailing comma and spaces
                if (products.length() > 0) products.setLength(products.length() - 2);
                if (quantities.length() > 0) quantities.setLength(quantities.length() - 2);

                data[i][2] = products.toString();
                data[i][3] = quantities.toString();
                data[i][4] = o.getTotalAmount();
                data[i][5] = o.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);


        setVisible(true);
    }
}
