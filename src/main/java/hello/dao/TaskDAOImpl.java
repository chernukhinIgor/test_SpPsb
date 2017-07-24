package hello.dao;

import hello.model.Task;
import hello.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class TaskDAOImpl implements TaskDAO {
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
    public void addTask(Task task) {
        entityManager.persist(task);
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
    public void deleteTask(int taskId) {
        entityManager.remove(getTaskById(taskId));
    }

    @Override
    public boolean taskExists(int taskId) {
        String hql = "FROM Task as tsk WHERE tsk.taskId = ?";
        int count = entityManager.createQuery(hql).setParameter(1, taskId)
                .getResultList().size();
        return count > 0 ? true : false;
    }
}
