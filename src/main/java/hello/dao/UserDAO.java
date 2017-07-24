package hello.dao;

/**
 * Created by Tom on 21.07.2017.
 */

import hello.model.User;

public interface UserDAO {
    Iterable<User> getAllUsers();
    User getUserById(int userId);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int userId);
    boolean userExists(int userId);
}