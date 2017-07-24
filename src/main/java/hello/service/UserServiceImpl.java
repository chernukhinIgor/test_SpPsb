package hello.service;

import hello.dao.UserDAO;
import hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public synchronized int addUser(User user) {
        if (userDAO.userExists(user.getUserId())) {
            return -1;
        } else {
            return userDAO.addUser(user);
        }
    }

    @Override
    public boolean updateUser(User user) {
        if (userDAO.userExists(user.getUserId())) {
            userDAO.updateUser(user);
            return true;
        } else
            return false;
    }

    @Override
    public int deleteUserById(int id) {
        return userDAO.deleteUser(id);
    }
}
