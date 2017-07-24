package hello.dao;

/**
 * Created by Tom on 21.07.2017.
 */

import hello.model.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int userId);
    int addUser(User user);
    void updateUser(User user);
    int deleteUser(int userId);
    boolean userExists(int userId);
}