import javax.swing.*;
import java.awt.*;  
import java.util.List;

public class MainGUI{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

// Login Frame
class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Login form components here
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);   

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> authenticate());
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    // Simple authentication method
    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded credentials for demonstration
        if(username.equals("admin") && password.equals("password")) {
            dispose();
            new AdminDashboard();
        } else if (username.equals("customer") && password.equals("password")) {
            EStore.currentCart = new Cart(1, "customer");
            new CustomerDashboard();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addProduct = new JButton("Add Product");
        JButton updateProduct = new JButton("Update/Delete Product");
        JButton viewProducts = new JButton("View All Products");
        JButton viewOrders = new JButton("View Orders");
        JButton reports = new JButton("Reports");
        JButton logout = new JButton("Logout");

        addProduct.addActionListener(e -> new AddProductFrame());
        updateProduct.addActionListener(e -> new UpdateDeleteProductFrame());
        viewProducts.addActionListener(e -> new ViewProductsFrame());
        viewOrders.addActionListener(e -> new ViewOrdersFrame());
        reports.addActionListener(e -> new ReportsFrame());
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        panel.add(addProduct);
        panel.add(updateProduct);
        panel.add(viewProducts);
        panel.add(viewOrders);
        panel.add(reports);
        panel.add(logout);
       
        add(panel);
        setVisible(true);   

    }
}

class AddProductFrame extends JFrame {
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

class UpdateDeleteProductFrame extends JFrame {
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
        JButton deleteButton = new JButton("Delete");
        panel.add(updateButton);
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

class ViewProductsFrame extends JFrame {
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

class ViewOrdersFrame extends JFrame {
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

class ReportsFrame extends JFrame {
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


class CustomerDashboard extends JFrame {
    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton browseProducts = new JButton("Browse Products");
        JButton searchProducts = new JButton("Search Products");
        JButton viewCart = new JButton("View Cart");
        JButton orderHistory = new JButton("Order History");
        JButton trackOrders = new JButton("Track Orders");  
        JButton logout = new JButton("Logout");

        browseProducts.addActionListener(e -> new BrowseProductsFrame());
        searchProducts.addActionListener(e -> new searchProductsFrame());
        viewCart.addActionListener(e -> new ViewCartFrame());
        orderHistory.addActionListener(e -> new OrderHistoryFrame());
        trackOrders.addActionListener(e -> new TrackOrdersFrame());
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        panel.add(browseProducts);
        panel.add(searchProducts);
        panel.add(viewCart);
        panel.add(orderHistory);
        panel.add(trackOrders);
        panel.add(logout);
       
        add(panel);
        setVisible(true);   

    }
}

class BrowseProductsFrame extends JFrame {
    public BrowseProductsFrame() {
        setTitle("Browse Products");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Product ID", "Name", "Price", "Category", "Quantity"};
        List<Product> products = EStore.productStorage.getAllProducts();
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

class searchProductsFrame extends JFrame {
    public searchProductsFrame() {
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

class ViewCartFrame extends JFrame {
    public ViewCartFrame() {
        setTitle("View Cart");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Cart cart = EStore.currentCart;
        List<CartItem> items = cart.getItems();
        Object[][] data = new Object[items.size()][5];
        String[] columnNames = {"Product ID", "Name", "Quantity", "Price", "Total Price"};

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
        add(scrollPane);

        setVisible(true);
    }
}

class OrderHistoryFrame extends JFrame {
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

class TrackOrdersFrame extends JFrame {
    public TrackOrdersFrame() {
        setTitle("Track Orders");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Enter Order ID:"));
        JTextField orderIdField = new JTextField();
        panel.add(orderIdField);

        JButton trackButton = new JButton("Track");
        panel.add(trackButton);

        trackButton.addActionListener(e -> {
            String orderId = orderIdField.getText();

            try {
                int id = Integer.parseInt(orderId);
                Order ord = EStore.orderInventory.getOrderById(id);

                if (ord != null) {
                    String message = "Order ID: " + ord.getOrderId() +
                                     "\nCustomer Name: " + ord.getCustomerName() +
                                     "\nTotal Amount: $" + ord.getTotalAmount() +
                                     "\nStatus: " + ord.getStatus() +
                                     "\nOrder Date: " + ord.getTimestamp();
                    JOptionPane.showMessageDialog(this, message, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Order not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }
}   

