package hello.dao;

import hello.model.Task;

public interface TaskDAO {
    Iterable<Task> getAllTasks();
    Task getTaskById(int taskId);
    void addTask(Task task);
    void updateTask(Task task);
    void deleteTask(int taskId);
    boolean taskExists(int taskId);
}
