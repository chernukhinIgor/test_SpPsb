package hello.dao;

/**
 * Created by Tom on 21.07.2017.
 */

import hello.model.Task;
import hello.model.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int userId);
    int addUser(User user);
    void updateUser(User user);
    int deleteUser(int userId);
    boolean userExistsById(int userId);
    boolean userExistsByMail(String mail);
    List<Task> getCreatedTasks(int id);
    List<Task> getResponsibleTasks(int id);
    List<Object[]> getParametricUsers(List<String> requestStringParams);
    int getUserCount();
    List<User>getPaginationUsers(String orderBy, String sortBy, int page, int pageLimit);
    User getUserByMail(String mail);
    int confirmEmail( String email);
}