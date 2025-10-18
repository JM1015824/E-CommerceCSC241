import java.util.*;

public class UserStorage {
    private Map<String, User> users = new HashMap<>();
    private int customerCounter = 1;
    private int adminCounter = 1;

    public void addUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            String role = user.getRole().toLowerCase();
            String generatedId = role.equals("admin")
                    ? "A" + String.format("%03d", adminCounter++)
                    : "C" + String.format("%03d", customerCounter++);
            user.setId(generatedId);
        }
        users.put(user.getId(), user);
    }

    public User getUserById(String id) {
        return users.get(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void removeUser(String id) {
        users.remove(id);
    }
}
