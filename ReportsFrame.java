


import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ReportsFrame extends JFrame{
        public ReportsFrame() {
        setTitle("Reports");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea reportArea = new JTextArea();
        reportArea.setText("Sales Report:\n\nTotal Sales: $10,000\nTotal Orders: 150\nTop Selling Product: Laptop");
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea));

        setVisible(true);
    }
}
