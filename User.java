import java.util.*;
public class User {
    private String id;
    private String name;
    private String role; // "customer" or "admin"
    private String password;

    public User(String id, String name, String role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }
    public void setRole(String role) { this.role = role; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", role='" + role + '\'' + '}';
    }
}


