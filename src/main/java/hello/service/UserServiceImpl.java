package hello.service;

import hello.dao.UserDAO;
import hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public Iterable<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public synchronized boolean addUser(User user) {
        if (userDAO.userExists(user.getName(), user.getSurname())) {
            return false;
        } else {
            userDAO.addUser(user);
            return true;
        }
    }

    @Override
    public void deleteUserById(int id) {
        userDAO.deleteUser(id);
    }
}
