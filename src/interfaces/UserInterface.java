package interfaces;

import entities.User;

public interface UserInterface {

    User findByEmail(String email);
    void addUser(User user);
    boolean validateUser(String email, String password);
}
