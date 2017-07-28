package hello.service;

import hello.model.Task;
import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    int USER_NOT_EXIST_ERROR = -1;
    int USER_NOT_DELETED_ERROR = -2;
    int USER_ALREADY_EXIST_ERROR = -3;
    int USER_EMAIL_NOT_EXIST_ERROR = -4;

    int USER_DELETED_SUCCESSFULLY = 1;
    int EMAIL_CONFIRMED_SUCCESSFULLY=2;

    List<User> getAllUsers();
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
}