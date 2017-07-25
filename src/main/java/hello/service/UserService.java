package hello.service;

import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    int USER_ALREADY_EXIST_ERROR = -3;
    int USER_DELETED_SUCCESSFULLY = 1;
    int USER_NOT_EXIST_ERROR = -1;
    int USER_NOT_DELETED_ERROR = -2;
    List<User> getAllUsers();
    User getUserById(int id);
    int addUser(@Valid User user);
    boolean updateUser(User user);
    int deleteUserById(int id);
}