package hello.service;

import hello.model.Task;
import hello.model.User;

import javax.validation.Valid;

public interface TaskService {

    Iterable<Task> getAllTasks();
    Task getTaskById(int id);
    boolean addTask(@Valid Task task);
    boolean updateTask(@Valid Task task);
    void deleteTaskById(Integer id);

}
