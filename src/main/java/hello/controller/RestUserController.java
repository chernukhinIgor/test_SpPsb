package hello.controller;

import hello.model.Task;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.*;

import hello.model.User;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import static hello.service.UserService.USER_DELETED_SUCCESSFULLY;
import static hello.service.UserService.USER_NOT_DELETED_ERROR;
import static hello.service.UserService.USER_NOT_EXIST_ERROR;
import static hello.service.UserService.USER_ALREADY_EXIST_ERROR;

@RestController
public class RestUserController {
    @Autowired
    private UserService userService;

    @GetMapping("user/{id}")
    public JSONObject getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        if (user != null)
            return JsonWrapper.wrapObject(user);
        else {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "user not found");
            return jsonError;
        }
    }

    @GetMapping("users")
    public JSONObject getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<Object> objectList = new ArrayList<>(allUsers);
        return JsonWrapper.wrapList(objectList);
    }

    @GetMapping("userCreatedTasks/{id}")
    public JSONObject getAllCreatedTasks(@PathVariable("id") Integer id) {
        if(userService.userExists(id)){
            List<Task> allCreatedTasks = userService.getCreatedTasks(id);
            List<Object> objectList = new ArrayList<>(allCreatedTasks);
            return JsonWrapper.wrapList(objectList);
        }else{
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "user not found");
            return jsonError;
        }
    }

    @GetMapping("userResponsibleTasks/{id}")
    public JSONObject getAllResponsibleTasks(@PathVariable("id") Integer id) {
        if(userService.userExists(id)){
            List<Task> allResponsibleTasks = userService.getResponsibleTasks(id);
            List<Object> objectList = new ArrayList<>(allResponsibleTasks);
            return JsonWrapper.wrapList(objectList);
        }else{
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "user not found");
            return jsonError;
        }
    }

    @PostMapping("user")
    public JSONObject addUser(@RequestBody User user, UriComponentsBuilder builder) {
        int returnedValue = userService.addUser(user);
        if (returnedValue == USER_ALREADY_EXIST_ERROR) {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "ERROR. Adding user failed. ID already exist");
            return jsonError;
        }
        JSONObject data = new JSONObject();
        data.put("userId", returnedValue);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("data", data);
        return jsonResponse;
    }

    @PutMapping("user")
    public JSONObject updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            JSONObject response = new JSONObject();
            response.put("success", true);
            return response;
        } else {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "ERROR. Adding user failed. ID already exist");
            return error;
        }
    }

    @DeleteMapping("user/{id}")
    public JSONObject deleteUser(@PathVariable("id") Integer id) {
        int deleteResult = userService.deleteUserById(id);
        JSONObject jsonResponse = new JSONObject();
        switch (deleteResult) {
            case USER_NOT_EXIST_ERROR:
                jsonResponse.put("success", false);
                jsonResponse.put("message", "ERROR. User not exist");
                break;
            case USER_NOT_DELETED_ERROR:
                jsonResponse.put("success", false);
                jsonResponse.put("message", "ERROR. ???");
                break;
            case USER_DELETED_SUCCESSFULLY:
                jsonResponse.put("success", true);
                break;
            default:
                break;
        }
        return jsonResponse;
    }

}
