package hello.dao;

import hello.model.Task;

import java.util.List;

public interface TaskDAO {


    List<Task> getAllTasks();
    Task getTaskById(int taskId);
    int addTask(Task task);
    void updateTask(Task task);
    int deleteTask(int taskId);
    boolean taskExists(int taskId);
}
