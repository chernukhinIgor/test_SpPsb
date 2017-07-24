package hello.dao;

import hello.model.Task;

import java.util.List;

public interface TaskDAO {
    List<Task> getAllTasks();
    Task getTaskById(int taskId);
    void addTask(Task task);
    void updateTask(Task task);
    void deleteTask(int taskId);
    boolean taskExists(int taskId);
}
