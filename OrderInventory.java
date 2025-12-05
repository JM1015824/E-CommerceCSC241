import java.util.*;

public class OrderInventory {
    private final List<Order> orders;
    private final Queue<Order> orderQueue; // Queue for pending orders

    public OrderInventory() {
        this.orders = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
    }

    //  Place a new order and deduct stock
    public Order placeOrder(String customerName, List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place order.");
        }

        double totalAmount = 0;

        for (CartItem item : items) {
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product: " + item.getProduct().getName());
            }

            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProduct().getName());
            }

            // Deduct stock
            int newQuantity = item.getProduct().getQuantity() - item.getQuantity();
            item.getProduct().setQuantity(newQuantity);

            totalAmount += item.getTotalPrice();
        }

        int newOrderId = orders.size() + 1; // Simple incremental order ID
        Order newOrder = new Order(newOrderId, customerName, items, totalAmount);

        addOrder(newOrder);
        orderQueue.add(newOrder);

        System.out.println("Order placed successfully: " + newOrder);
        return newOrder;
    }

    //  Process next order (FIFO)
    public void processNextOrder() {
        Order orderToProcess = orderQueue.poll();
        if (orderToProcess != null) {
            orderToProcess.setStatus(Order.Status.SHIPPED);
            System.out.println(" Processed order ID: " + orderToProcess.getOrderId());
        } else {
            System.out.println("No orders to process.");
        }
    }

    // Get pending orders
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.PENDING) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    // Get shipped orders
    public List<Order> getShippedOrders() {
        List<Order> shippedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.SHIPPED) {
                shippedOrders.add(order);
            }
        }
        return shippedOrders;
    }

    // Get delivered orders
    public List<Order> getDeliveredOrders() {
        List<Order> deliveredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.DELIVERED) {
                deliveredOrders.add(order);
            }
        }
        return deliveredOrders;
    }

    // Add a new order (used internally)
    public void addOrder(Order order) {
        orders.add(order);
    }

    // View all orders
    public List<Order> getOrders() {
        return orders;
    }

    // Get all orders for a specific customer
    public List<Order> getOrdersByCustomer(String customerName) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomerName().equalsIgnoreCase(customerName)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    // Get a specific order
    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    // Update an order’s status manually (Admin function)
    public void updateOrderStatus(int orderId, Order.Status newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            System.out.println("Order ID " + orderId + " status updated to " + newStatus);
        } else {
            System.out.println("Order ID " + orderId + " not found.");
        }
    }

    public Order createOrder(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }

        List<CartItem> items = cart.getItems();
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot create order.");
        }   

        return placeOrder(cart.getCustomerName(), cart.getItems());
    }
}