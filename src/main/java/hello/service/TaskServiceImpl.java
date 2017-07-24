package hello.service;

import hello.model.Task;
import org.springframework.stereotype.Service;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Override
    public Iterable<Task> getAllTasks() {
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void deleteTaskById(long id) {

    }
}
