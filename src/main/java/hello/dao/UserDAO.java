package hello.dao;

import hello.model.Task;
import hello.model.User;
import java.util.List;

public interface UserDAO {
    List<Object[]> getAllUsers();
    User getUserById(int userId);
    List<Object[]> getUserListById(int userId);
    int addUser(User user);
    void updateUser(User user);
    int deleteUser(int userId);
    boolean userExistsById(int userId);
    boolean userExistsByMail(String mail);
    List<Task> getCreatedTasks(int id);
    List<Task> getResponsibleTasks(int id);
    List<Object[]> getParametricUsers(List<String> requestStringParams);
    int getUserCount();
    List<Object[]> getPaginationUsers(String orderBy, String sortBy, int page, int pageLimit);
    User getUserByMail(String mail);
    int confirmEmail( String email);
    void updatePassword(User user);
}