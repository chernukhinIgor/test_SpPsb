package hello.controller;

import hello.model.Task;
import hello.model.User;
import hello.service.TaskService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static hello.service.TaskService.*;
import static hello.utils.JsonWrapper.getJsonArrayFromObjects;

@RestController
public class RestTaskController {

    @Autowired
    private TaskService taskService;

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("taskPagination")
    public JSONObject getPaginationTasks(
            // default value or error
            @RequestParam(required = true) String orderBy,
            @RequestParam(required = true) String sortBy,
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int pageLimit
    ){
        try{
            List<Task> paginationTasks = taskService.getPaginationTasks(orderBy, sortBy,page,pageLimit);
            List<Object> objectList = new ArrayList<>(paginationTasks);
            return JsonWrapper.wrapList(objectList);
        }catch (Exception ex){
            JSONObject jsonError=new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "ERROR. Not valid params");
            return jsonError;
        }
    }

    @CrossOrigin
    @GetMapping("tasks")
    public JSONObject getAllTasks(@RequestParam(required = false, value="params") List<String> columns) {
        if (columns != null) {
            List<Object[]> allTasksAsObjects = taskService.getParametricTasks(columns);
            JSONArray array = getJsonArrayFromObjects(columns, allTasksAsObjects);
            return JsonWrapper.wrapList(array);
        } else {
            List<Task> allTasks = taskService.getAllTasks();
            List<Object> objectList = new ArrayList<>(allTasks);
            return JsonWrapper.wrapList(objectList);
        }
    }

    @CrossOrigin
    @GetMapping("taskCount")
    public JSONObject getTaskCount() {
        int taskCount=taskService.getTaskCount();
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data",taskCount);
        return response;
    }



    @CrossOrigin
    @PostMapping("task")
    public JSONObject addTask(@RequestBody Task task, UriComponentsBuilder builder) {
        int i = taskService.addTask(task);

        JSONObject response=new JSONObject();
        switch (i){
            case TASK_ALREADY_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Creating task failed. Id already exists");
                break;
            case CREATOR_USER_ID_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Creator user id not exists");
                break;
            case RESPONSIBLE_USER_ID_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Responsible user id not exists");
                break;
            default:
                response.put("success", true);
                JSONObject data=new JSONObject();
                data.put("taskId", i);
                response.put("data", data);
                break;
        }
        return response;
    }

    @CrossOrigin
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

    @CrossOrigin
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
