package hello.controller;

import hello.model.Task;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.model.User;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static hello.service.UserService.USER_DELETED_SUCCESSFULLY;
import static hello.service.UserService.USER_NOT_DELETED_ERROR;
import static hello.service.UserService.USER_NOT_EXIST_ERROR;
import static hello.service.UserService.USER_ALREADY_EXIST_ERROR;
import static hello.utils.JsonWrapper.getJsonArrayFromObjects;

@RestController
public class RestUserController {

    @Autowired
    private UserService userService;

//    @Controller
//    @RequestMapping("/*")
//    public class LoginController {
//
//        @RequestMapping(method = RequestMethod.POST)
//        public String login() {
//            return "login";
//        }
//    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("userPagination")
    public JSONObject getPaginationUsers(
            // default value or error
            @RequestParam(required = true) String orderBy,
            @RequestParam(required = true) String sortBy,
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int pageLimit
    ) {
        try {
            List<User> paginationTasks = userService.getPaginationUsers(orderBy, sortBy, page, pageLimit);
            List<Object> objectList = new ArrayList<>(paginationTasks);
            return JsonWrapper.wrapList(objectList);
        } catch (Exception ex) {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "ERROR. Not valid params");
            return jsonError;
        }
    }

    @CrossOrigin
    @GetMapping("userCount")
    public JSONObject getUserCount() {
        int userCount = userService.getUserCount();
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", userCount);
        return response;
    }

    @CrossOrigin
    @GetMapping("users")
    public JSONObject getAllUsers(@RequestParam(required = false, value="params") List<String> columns){
        if (columns != null) {
            List<Object[]> allUsersAsObjects = userService.getParametricUsers(columns);
            JSONArray array = getJsonArrayFromObjects(columns, allUsersAsObjects);
            return JsonWrapper.wrapList(array);
        } else {
            List<User> allUsers = userService.getAllUsers();
            List<Object> objectList = new ArrayList<>(allUsers);
            return JsonWrapper.wrapList(objectList);
        }
    }

    @CrossOrigin
    @GetMapping("userCreatedTasks/{id}")
    public JSONObject getAllCreatedTasks(@PathVariable("id") Integer id) {
        if (userService.userExists(id)) {
            List<Task> allCreatedTasks = userService.getCreatedTasks(id);
            List<Object> objectList = new ArrayList<>(allCreatedTasks);
            return JsonWrapper.wrapList(objectList);
        } else {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "user not found");
            return jsonError;
        }
    }

    @CrossOrigin
    @GetMapping("userResponsibleTasks/{id}")
    public JSONObject getAllResponsibleTasks(@PathVariable("id") Integer id) {
        if (userService.userExists(id)) {
            List<Task> allResponsibleTasks = userService.getResponsibleTasks(id);
            List<Object> objectList = new ArrayList<>(allResponsibleTasks);
            return JsonWrapper.wrapList(objectList);
        } else {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "user not found");
            return jsonError;
        }
    }

    @CrossOrigin
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

    @CrossOrigin
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

    @CrossOrigin
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
