package hello.service;

import hello.model.Task;
import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    List<Object[]> getAllUsers();
    User getUserById(int id);
    int addUser(@Valid User user);
    boolean updateUser(User user);
    boolean userExists(int id);
    int deleteUserById(int id);
    List<Task> getCreatedTasks(int id);
    List<Task> getResponsibleTasks(int id);
    int getUserCount();
    List<User>getPaginationUsers(String orderBy, String sortBy, int page, int pageLimit);
    List<Object[]> getParametricUsers(List<String> requestStringParams);
    User getUserByMail(String mail);
    int registerUser(User user);
    int confirmEmail(String email);
    boolean updatePassword(User user);
}