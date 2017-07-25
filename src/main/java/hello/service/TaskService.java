package hello.service;

import hello.model.Task;
import hello.model.User;

import javax.validation.Valid;
import java.util.List;

public interface TaskService {
    int TASK_ALREADY_EXIST_ERROR=0;

    int TASK_NOT_EXIST_ERROR=-1;
    int TASK_NOT_DELETED_ERROR=-2;

    int TASK_DELETED_SUCCESSFULLY=1;

    List<Task> getAllTasks();
    Task getTaskById(int id);
    int addTask(@Valid Task task);
    boolean updateTask(@Valid Task task);
    int deleteTaskById(int id);

}
