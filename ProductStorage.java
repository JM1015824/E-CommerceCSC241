import java.util.*;

public class ProductStorage {
    private Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> getProductsSortedByPrice() {
        List<Product> sorted = new ArrayList<>(products.values());
        sorted.sort(Comparator.comparingDouble(Product::getPrice));
        return sorted;
    }
}
