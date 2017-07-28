package hello.service;

import hello.model.Task;
import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(int id);
    int addTask(@Valid Task task);
    boolean updateTask(@Valid Task task);
    int deleteTaskById(int id);
    int getTaskCount();
    List<Task>getPaginationTasks(String orderBy, String sortBy, int page, int pageLimit);
    List<Object[]> getParametricTasks(List<String>  requestStringParams);
}
