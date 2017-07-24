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
public class UserDAOImpl implements UserDAO  {
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
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        User usr = getUserById(user.getUserId());
        usr.setName(user.getName());
        usr.setSurname(user.getSurname());
        usr.setBirth(user.getBirth());
        usr.setEmail(user.getEmail());
        usr.setSex(user.getSex());
        usr.setTelephone(user.getTelephone());
        entityManager.flush();
    }

    @Override
    public void deleteUser(int userId) {
        entityManager.remove(getUserById(userId));
    }

    @Override
    public boolean userExists(int userId) {
        String hql = "FROM User as usr WHERE usr.userId = ?";
        //String hql = "FROM User as usr WHERE usr.name = ? and usr.surname = ?";
        int count = entityManager.createQuery(hql).setParameter(1, userId).getResultList().size();
        return count > 0 ? true : false;
    }
}