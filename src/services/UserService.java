package services;

import entities.User;
import interfaces.UserInterface;

public class UserService implements UserInterface {
    private static final int MAX_USERS = 100;
    private User[] users;
    private int userCount;

    public UserService() {
        this.users = new User[MAX_USERS];
        this.userCount = 0;
        users[userCount++] = new User(1, "Admin", "admin@gmail.com", "12345678", "admin", "019234567890");
    }

    @Override
    public User findByEmail(String email) {

        for (int i = 0; i < userCount; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                return users[i];
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        if (userCount < MAX_USERS) {
            users[userCount++] = user;
        } else {
            System.out.println("User limit reached. Cannot add more users.");
        }
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
