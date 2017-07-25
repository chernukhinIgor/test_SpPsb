package hello.service;

import hello.dao.UserDAO;
import hello.model.Task;
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
            return USER_ALREADY_EXIST_ERROR;
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
    public boolean userExists(int id) {
        return userDAO.userExists(id);
    }

    @Override
    public int deleteUserById(int id) {
        return userDAO.deleteUser(id);
    }

    @Override
    public List<Task> getCreatedTasks(int id) {
        return userDAO.getCreatedTasks(id);
    }

    @Override
    public List<Task> getResponsibleTasks(int id) {
        return userDAO.getResponsibleTasks(id);
    }

    @Override
    public List<Object[]>  getParametricUsers(String  requestStringParams) {
        return userDAO.getParametricUsers(requestStringParams);
    }

    @Override
    public int getUserCount() {
        return userDAO.getUserCount();
    }

    @Override
    public List<User> getPaginationUsers(String orderBy, String sortBy, int page, int pageLimit) {
        return userDAO.getPaginationUsers(orderBy,sortBy,page,pageLimit);
    }
}
