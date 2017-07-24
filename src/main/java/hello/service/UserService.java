package hello.service;

import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    boolean addUser(@Valid User user);
    boolean updateUser(User user);
    void deleteUserById(int id);
}