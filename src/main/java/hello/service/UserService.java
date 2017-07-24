package hello.service;

import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    public Iterable<User> getAllUsers();
    public User getUserById(long id);
    public void addUser(@Valid User user);
    public void deleteUserById(long id);

}