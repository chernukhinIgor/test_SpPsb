package hello.service;

import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    int addUser(@Valid User user);
    boolean updateUser(User user);
    int deleteUserById(int id);
}