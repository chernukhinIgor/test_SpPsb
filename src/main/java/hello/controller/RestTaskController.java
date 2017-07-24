package hello.controller;

import hello.model.Task;
import hello.model.User;
import hello.service.TaskService;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class RestTaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("task/{id}")
    public ResponseEntity<Task> getTaskByID(@PathVariable("id") Integer id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("tasks")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        Iterable<Task> allTasks = taskService.getAllTasks();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    @PostMapping("task")
    public String addTask(@RequestBody Task task, UriComponentsBuilder builder) {
        boolean flag = taskService.addTask(task);
        if (!flag) {
            return "Error in POST";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(task.getTaskId()).toUri());
        return "Posted";
    }

    @PutMapping("task")
    public String updateTask (@RequestBody Task task) {
        if (taskService.updateTask(task))
            return "Task updated";
        else
            return "Task not found";
    }

    @DeleteMapping("task/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTaskById(id);
        return "Task deleted";
    }

}
