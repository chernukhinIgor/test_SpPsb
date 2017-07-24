package hello.dao;

/**
 * Created by Tom on 21.07.2017.
 */

import hello.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User as usr ORDER BY usr.userId";
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public int addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user.getUserId();
    }

    @Override
    public void updateUser(User user) {
        User usr = getUserById(user.getUserId());
        usr.setName(user.getName());
        usr.setSurname(user.getSurname());
        usr.setBirth(user.getBirth());
        usr.setEmail(user.getEmail());
        usr.setGender(user.getGender());
        usr.setTelephone(user.getTelephone());
        entityManager.flush();
    }

    @Override
    public int deleteUser(int userId) {
        if (userExists(userId)) {
            entityManager.remove(getUserById(userId));
            if (userExists(userId)) {
                return -2;
            }
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean userExists(int userId) {
        String hql = "FROM User as usr WHERE usr.userId = ?";
        //String hql = "FROM User as usr WHERE usr.name = ? and usr.surname = ?";
        int count = entityManager.createQuery(hql).setParameter(1, userId).getResultList().size();
        return count > 0 ? true : false;
    }
}