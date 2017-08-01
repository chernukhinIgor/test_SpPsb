package hello.controller;

import hello.model.Task;
import hello.secure.model.UserAuthentication;
import hello.service.TaskService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import hello.websocket.handler.SimpleWebSocketHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static hello.utils.JsonWrapper.getJsonArrayFromObjects;

@RestController
public class RestTaskController {

    @Autowired
    SimpleWebSocketHandler simpleWebSocketHandler;

    @Autowired
    private SimpMessagingTemplate template;

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
            jsonError.put("error", JsonWrapper.wrapError("Task does not exist", ReplyCodes.NOT_EXIST_ERROR));
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
            jsonError.put("error", JsonWrapper.wrapError("Not valid pagination params", ReplyCodes.NOT_VALID_PARAMS_ERROR));
            return jsonError;
        }
    }

    @CrossOrigin
    @GetMapping("tasks")
    public JSONObject getAllTasks(@RequestParam(required = false, value="params") List<String> columns) {
        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("Principal: " + principal);
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
//            case ReplyCodes.TASK_ALREADY_EXIST_ERROR:
//                response.put("success", false);
//                response.put("error", JsonWrapper.wrapError("Creating task failed. Id already exists", ReplyCodes.TASK_ALREADY_EXIST_ERROR));
//                break;
            case ReplyCodes.CREATOR_USER_ID_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Creator user id not exists", ReplyCodes.NOT_EXIST_ERROR));
                break;
            case ReplyCodes.RESPONSIBLE_USER_ID_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Responsible user id not exists", ReplyCodes.NOT_EXIST_ERROR));
                break;
            default:
                response.put("success", true);
                JSONObject data=new JSONObject();
                data.put("taskId", i);
                response.put("data", data);

                Task taskById = taskService.getTaskById(i);
                UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    int responsibleUserId = taskById.getResponsibleUserId();
                    template.convertAndSend("/channel/" + responsibleUserId, taskById);
                    simpleWebSocketHandler.taskCreated("/simpleChannel/" + responsibleUserId, taskById);
                }
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
            jsonSuccess.put("message", "Task updated successfully");
            return jsonSuccess;
        }else {
            JSONObject jsonError=new JSONObject();
            jsonError.put("success", false);
            jsonError.put("error", JsonWrapper.wrapError("Task does not exist", ReplyCodes.NOT_EXIST_ERROR));
            return jsonError;
        }
    }

    @CrossOrigin
    @DeleteMapping("task/{id}")
    public JSONObject deleteTask(@PathVariable("id") Integer id) {
        int i = taskService.deleteTaskById(id);
        JSONObject response=new JSONObject();
        switch (i){
            case ReplyCodes.TASK_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Task does not exist", ReplyCodes.NOT_EXIST_ERROR));
                break;
            case ReplyCodes.TASK_NOT_DELETED_ERROR:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Task not deleted", ReplyCodes.NOT_DELETED_ERROR));
                break;
            case ReplyCodes.TASK_DELETED_SUCCESSFULLY:
                response.put("success", true);
                response.put("message", "Task deleted successfully");
                break;
            default:
                break;
        }

        return response;
    }
}
