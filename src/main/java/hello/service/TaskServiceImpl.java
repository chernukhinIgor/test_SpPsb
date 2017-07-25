package hello.service;

import hello.dao.TaskDAO;
import hello.dao.UserDAO;
import hello.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDAO taskDAO;

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public Task getTaskById(int id) {
        return taskDAO.getTaskById(id);
    }

    @Override
    public int addTask(Task task) {
        if (taskDAO.taskExists(task.getTaskId())) {
            return TASK_ALREADY_EXIST_ERROR;
        } else {
            return taskDAO.addTask(task);
        }
    }

    @Override
    public boolean updateTask(Task task) {
        if (taskDAO.taskExists(task.getTaskId())) {
            taskDAO.updateTask(task);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deleteTaskById(int id) {
        return taskDAO.deleteTask(id);
    }

    @Override
    public int getTaskCount() {
        return taskDAO.getTaskCount();
    }

    @Override
    public List<Task> getPaginationTasks(String orderBy, String sortBy, int page, int pageLimit) {
        return taskDAO.getPaginationTasks(orderBy,sortBy,page,pageLimit);
    }
}
