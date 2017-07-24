package hello.service;

import hello.model.Task;

import javax.validation.Valid;

public interface TaskService {

    public Iterable<Task> getAllTasks();
    public Task getTaskById(int id);
    public void addTask(@Valid Task task);
    public void deleteTaskById(long id);

}
