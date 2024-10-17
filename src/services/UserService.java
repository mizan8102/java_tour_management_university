package services;


import entities.User;
import interfaces.UserInterface;

import java.util.ArrayList;

public class UserService implements UserInterface {
    private ArrayList<User> users;

    public UserService() {
        this.users = new ArrayList<>();
        users.add(new User(1, "Admin", "admin@gmail.com","12345678", "admin", "019234567890"));
//        users.add(new Guide(2, "Harun", "harun@example.com", "password456", "01987654321", 5, new String[]{"English", "Bangla"}));
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
