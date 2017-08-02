package hello.dao;

import hello.model.Task;
import hello.model.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.StringJoiner;

import hello.utils.ReplyCodes;

import static hello.service.TaskService.*;

@Transactional
@Repository
public class TaskDAOImpl implements TaskDAO {
    @Autowired
    UserDAO userDAO;

    @PersistenceContext
    private EntityManager entityManager;
    @SuppressWarnings("unchecked")

    @Override
    public List<Task> getAllTasks() {
        String hql = "FROM Task as tsk ORDER BY tsk.taskId";
        return (List<Task>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Task getTaskById(int taskId) {
        return entityManager.find(Task.class, taskId);
    }

    @Override
    public int addTask(Task task) {
        if(!userDAO.userExistsById(task.getCreatorUserId())){
            return ReplyCodes.CREATOR_USER_ID_NOT_EXIST_ERROR;
        }else if(!userDAO.userExistsById(task.getResponsibleUserId())){
            return ReplyCodes.RESPONSIBLE_USER_ID_NOT_EXIST_ERROR;
        }else {
            entityManager.persist(task);
            entityManager.flush();
            return task.getTaskId();
        }
    }


    @Override
    public void updateTask(Task task) {
        Task tsk = getTaskById(task.getTaskId());
        tsk.setCreatorUserId(task.getCreatorUserId());
        tsk.setName(task.getName());
        tsk.setResponsibleUserId(task.getResponsibleUserId());
        tsk.setDescription(task.getDescription());
        entityManager.flush();
    }

    @Override
    public int deleteTask(int taskId) {
        if(taskExists(taskId)){
            entityManager.remove(getTaskById(taskId));
            if(taskExists(taskId)){
                return ReplyCodes.TASK_NOT_DELETED_ERROR;
            }
            return ReplyCodes.TASK_DELETED_SUCCESSFULLY;
        }else{
            return ReplyCodes.TASK_NOT_EXIST_ERROR;
        }

    }

    @Override
    public boolean taskExists(int taskId) {
        String hql = "FROM Task as tsk WHERE tsk.taskId = ?";
        int count = entityManager.createQuery(hql).setParameter(1, taskId)
                .getResultList().size();
        return count > 0;
    }

    @Override
    public int getTaskCount() {
        String hql = "FROM Task as tsk";
        return entityManager.createQuery(hql).getResultList().size();

    }

    @Override
    public List<Task> getPaginationTasks(String orderBy, String sortBy, int page, int pageLimit) {
        String hql = "FROM Task as tsk ORDER BY tsk."+orderBy+" "+sortBy;
        return (List<Task>) entityManager.createQuery(hql)
                .setFirstResult(page*pageLimit-pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }

    @Override
    public List<Object[]> getParametricTasks(List<String> requestStringParams) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (String param:requestStringParams) {
            stringJoiner.add(param);
        }
        Query query = entityManager.createQuery("select "+stringJoiner.toString()+" FROM Task as tsk ORDER BY tsk.taskId");
        List<Object[]> rows = query.getResultList();
        return rows;
    }
}
