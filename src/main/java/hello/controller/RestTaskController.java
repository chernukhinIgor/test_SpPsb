package hello.controller;

import hello.model.Task;
import hello.model.User;
import hello.service.TaskService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static hello.service.TaskService.*;

@RestController
public class RestTaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("task/{id}")
    public JSONObject getTaskByID(@PathVariable("id") Integer id) {
        Task task = taskService.getTaskById(id);
        if(task!=null){
            return JsonWrapper.wrapObject(task);
        }else{
            JSONObject jsonError=new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "Task not found");
            return jsonError;
        }
    }

    @GetMapping("tasks")
    public JSONObject getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        List<Object> objectList = new ArrayList<Object>(allTasks);
        return JsonWrapper.wrapList(objectList);
    }

    @PostMapping("task")
    public JSONObject addTask(@RequestBody Task task, UriComponentsBuilder builder) {
        int id = taskService.addTask(task);
        if (id == TASK_ALREADY_EXIST_ERROR) {
            JSONObject jsonError=new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "ERROR. Creating task failed. Id already exists");

            return jsonError;
        }else{
            JSONObject jsonSuccess=new JSONObject();
            jsonSuccess.put("success", true);

            JSONObject data=new JSONObject();
            data.put("taskId", id);
            jsonSuccess.put("data", data);

            return jsonSuccess;
        }
    }

    @PutMapping("task")
    public JSONObject updateTask (@RequestBody Task task) {
        if (taskService.updateTask(task)) {
            JSONObject jsonSuccess=new JSONObject();
            jsonSuccess.put("success", true);
            jsonSuccess.put("message", "Task updated succesfully");

            return jsonSuccess;
        }else {
            JSONObject jsonError=new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "ERROR. Task not found");

            return jsonError;
        }
    }

    @DeleteMapping("task/{id}")
    public JSONObject deleteTask(@PathVariable("id") Integer id) {
        int i = taskService.deleteTaskById(id);
        JSONObject response=new JSONObject();
        switch (i){
            case TASK_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Task does not exist");
                break;
            case TASK_NOT_DELETED_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Task not deleted");
                break;
            case TASK_DELETED_SUCCESSFULLY:
                response.put("success", true);
                response.put("message", "Task deleted succesfully");
                break;
            default:
                break;
        }

        return response;
    }
}
