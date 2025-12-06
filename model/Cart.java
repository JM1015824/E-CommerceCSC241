import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final int cartId;
    private final String customerName;
    private final List<CartItem> items;

    public Cart(int cartId, String customerName) {
        this.cartId = cartId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
    }

    public int getCartId() {
        return cartId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<CartItem> getItems() {
        return items;
    }

    // Add item with stock validation
    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient product quantity available for " + product.getName());
        }

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("Updated quantity for product: " + product.getName() + " to " + item.getQuantity());
                return;
            }
        }

        items.add(new CartItem(product, quantity));
        product.setQuantity(product.getQuantity() - quantity);
        System.out.println("Added product: " + product.getName() + " with quantity " + quantity + " to cart.");
    }

    // Remove item by product ID
    public void removeProduct(String productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                iterator.remove();
                item.getProduct().setQuantity(item.getProduct().getQuantity() + item.getQuantity());
                System.out.println("Removed product: " + item.getProduct().getName() + " from cart.");
                return;
            }
        }
    }

    // Update item quantity
    public void updateItemQuantity(String productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                if (newQuantity <= 0) {
                    removeProduct(productId);
                } else if (newQuantity > item.getProduct().getQuantity()) {
                    System.out.println("Not enough stock for " + item.getProduct().getName());
                } else {
                    item.setQuantity(newQuantity);
                    System.out.println("Updated quantity for product ID: " + productId + " to " + newQuantity);
                }
                return;
            }
        }
        System.out.println("Product with ID: " + productId + " not found in cart.");
    }

    // View cart contents
    public void viewCart() {
        System.out.println("\n🛒 Cart ID: " + cartId + ", Customer: " + customerName);
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (CartItem item : items) {
            System.out.println(" - " + item);
        }
        System.out.println("Total amount: $" + getTotalAmount());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Clear all items
    public void clearCart() {
        items.clear();
        System.out.println("Cleared all items from cart.");
    }

    // Calculate total
    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Applys coupon
    public double applyCoupon(Coupon coupon) {
        Date now = new Date();
        if (coupon != null && coupon.getExpirationDate().after(now)) {
            double discount = getTotalAmount() * (coupon.getPercentOff() / 100);
            return getTotalAmount() - discount;
        }
        return getTotalAmount();
    }
     public Order checkout() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot checkout.");
        }

        // Get a order ID (timestamp-based)
        int orderId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

        double totalAmount = getTotalAmount();

        // Reduce stock in product storage
        for (CartItem item : items) {
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
        }

        // Create order
        Order order = new Order(orderId, customerName, new ArrayList<>(items), totalAmount);

        // Add to global order inventory
        EStore.orderInventory.addOrder(order);

        // Clear cart after checkout
        clearCart();

        return order;
    }
    
    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                '}';
    }
}


