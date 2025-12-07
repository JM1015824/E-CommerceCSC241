


import java.awt.*;
import java.util.List;
import javax.swing.*;


public class OrderHistoryFrame extends JFrame{
        public OrderHistoryFrame() {
        setTitle("Order History");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        List<Order> orders = EStore.orderInventory.getOrdersByCustomer("customer");
        String[] columnNames = {"Order ID", "Total Amount", "Status"};
        Object[][] data = new Object[orders.size()][3];

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            data[i][0] = order.getOrderId();
            data[i][1] = order.getTotalAmount();
            data[i][2] = order.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }
}
