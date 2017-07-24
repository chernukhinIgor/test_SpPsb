package hello.service;

import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    Iterable<User> getAllUsers();
    User getUserById(int id);
    boolean addUser(@Valid User user);
    void deleteUserById(int id);

}