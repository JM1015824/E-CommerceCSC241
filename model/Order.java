package model;
import java.util.*;

public class Order {
    public enum Status {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    private final int orderId;
    private final String customerName;
    private final List<CartItem> items;
    private final double totalAmount;
    private final double taxAmount;
    private Status status;
    private final Date timestamp;   


    public Order(int orderId, String customerName, List<CartItem> items, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.timestamp = new Date();
        this.taxAmount = totalAmount * 0.1; // Assuming a fixed tax rate of 10%
        this.status = Status.PENDING;

    }

    public double getTaxAmount() {
        return taxAmount;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status shipped) {
        this.status = shipped;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}