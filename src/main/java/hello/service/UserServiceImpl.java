package hello.service;

import hello.dao.UserDAO;
import hello.model.Task;
import hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import hello.utils.ReplyCodes;

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
        if (userDAO.userExistsById(user.getUserId())) {
            return ReplyCodes.USER_ALREADY_EXIST_ERROR;
        } else {
            return userDAO.addUser(user);
        }
    }

    @Override
    public boolean updateUser(User user) {
        if (userDAO.userExistsById(user.getUserId())) {
            userDAO.updateUser(user);
            return true;
        } else
            return false;
    }

    @Override
    public boolean userExists(int id) {
        return userDAO.userExistsById(id);
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
    public List<Object[]> getParametricUsers(List<String> requestStringParams) {
        return userDAO.getParametricUsers(requestStringParams);
    }

    @Override
    public User getUserByMail(String mail) {
        return userDAO.getUserByMail(mail);
    }

    @Override
    public int registerUser(User user) {
        if (userDAO.userExistsByMail(user.getEmail())) {
            return ReplyCodes.USER_ALREADY_EXIST_ERROR;
        } else {
            return userDAO.addUser(user);
        }
    }

    @Override
    public int confirmEmail( String email) {
        return userDAO.confirmEmail(email);
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
