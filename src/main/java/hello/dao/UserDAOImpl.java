package hello.dao;

/**
 * Created by Tom on 21.07.2017.
 */

import hello.model.Task;
import hello.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.StringJoiner;

import hello.utils.ReplyCodes;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Transactional
@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")

    @Override
    public List<Object[]> getAllUsers() {
        String hql = "SELECT u.userId, u.name, u.surname, u.gender, u.email, u.birth, u.telephone, u.confirmedEmail FROM User u";
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public User getUserById(int userId) {
        //String hql = "SELECT u.userId, u.name, u.surname, u.gender, u.email, u.birth, u.telephone, u.confirmedEmail FROM User u WHERE";
        return entityManager.find(User.class, userId);
    }

    @Override
    public int addUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String salt=BCrypt.gensalt();
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(salt+user.getPassword()));

        entityManager.persist(user);
        entityManager.flush();
        return user.getUserId();
    }

    @Override
    public void updatePassword(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String salt=BCrypt.gensalt();
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(salt+user.getPassword()));

        entityManager.flush();
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
        if (userExistsById(userId)) {
            entityManager.remove(getUserById(userId));
            if (userExistsById(userId)) {
                return ReplyCodes.USER_NOT_DELETED_ERROR;
            }
            return ReplyCodes.USER_DELETED_SUCCESSFULLY;
        } else {
            return ReplyCodes.USER_NOT_EXIST_ERROR;
        }
    }

    @Override
    public boolean userExistsById(int userId) {
        String hql = "FROM User as usr WHERE usr.userId = ?";
        int count = entityManager.createQuery(hql).setParameter(1, userId).getResultList().size();
        return count > 0;
    }

    @Override
    public boolean userExistsByMail(String mail){
        String hql = "FROM User as usr WHERE usr.email = ?";
        int count = entityManager.createQuery(hql).setParameter(1, mail).getResultList().size();
        return count > 0;
    }

    @Override
    public List<Task> getCreatedTasks(int id) {
        String hql = "FROM Task as tsk WHERE tsk.creatorUserId = ? ORDER BY tsk.taskId";
        return entityManager.createQuery(hql).setParameter(1, id).getResultList();
    }

    @Override
    public List<Task> getResponsibleTasks(int id) {
        String hql = "FROM Task as tsk WHERE tsk.responsibleUserId = ? ORDER BY tsk.taskId";
        return entityManager.createQuery(hql).setParameter(1, id).getResultList();
    }

    @Override
    public List<Object[]> getParametricUsers(List<String> requestStringParams) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (String param:requestStringParams) {
            stringJoiner.add(param);
        }
        Query query = entityManager.createQuery("select "+stringJoiner.toString()+" FROM User as usr ORDER BY usr.userId");
        List<Object[]> rows = query.getResultList();
        return rows;
    }

    @Override
    public int getUserCount() {
        String hql = "FROM User as usr";
        return entityManager.createQuery(hql).getResultList().size();
    }

    @Override
    public List<User> getPaginationUsers(String orderBy, String sortBy, int page, int pageLimit) {
        String hql = "FROM User as usr ORDER BY usr."+orderBy+" "+sortBy;
        return (List<User>) entityManager.createQuery(hql)
                .setFirstResult(page*pageLimit-pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }

    @Override
    public User getUserByMail(String mail) {
        //User user = entityManager.find(User.class, username);
        String hql = "FROM User as usr WHERE usr.email = ?";
        List<User> resultList = entityManager.createQuery(hql).setParameter(1, mail).getResultList();
        if (resultList.size() == 1) {
            User us = resultList.get(0);
            return us;
        }
        return null;
    }

    @Override
    public int confirmEmail( String email) {
        if(!userExistsByMail(email)){
            return ReplyCodes.NOT_EXIST_ERROR;
        }else{
            User usr = getUserByMail(email);
            usr.setConfirmedEmail(true);
            entityManager.flush();
            return ReplyCodes.EMAIL_CONFIRMED_SUCCESSFULLY;
        }
    }

}