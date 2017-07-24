package hello.service;

import hello.dao.TaskDAO;
import hello.dao.UserDAO;
import hello.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDAO taskDAO;

    @Override
    public Iterable<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public Task getTaskById(int id) {
        return taskDAO.getTaskById(id);
    }

    @Override
    public synchronized boolean addTask(Task task) {
        if (taskDAO.taskExists(task.getTaskId())) {
            return false;
        } else {
            taskDAO.addTask(task);
            return true;
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
    public void deleteTaskById(int id) {
        taskDAO.deleteTask(id);
    }
}
