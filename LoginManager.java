public class LoginManager {
    private UserStorage userStorage;

    public LoginManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User loginUserManual(String name, String password) {
        for (User user : userStorage.getAllUsers()) {
            if (user.getName().equalsIgnoreCase(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
