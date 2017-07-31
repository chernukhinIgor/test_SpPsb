package hello.controller;

import hello.model.Task;
import hello.secure.model.UserAuthentication;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.model.User;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hello.utils.ReplyCodes;
import static hello.utils.JsonWrapper.getJsonArrayFromObjects;

@RestController
public class RestUserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("user/{id}")
    public JSONObject getUserById(@PathVariable("id") Integer id) {
        List<String> columns = Arrays.asList("userId", "name", "surname", "gender", "email", "birth", "telephone", "confirmedEmail");
        List<Object[]> user = userService.getUserListById(id);
        if(user.isEmpty()){
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
            return jsonError;
        }else{
            JSONArray array = getJsonArrayFromObjects(columns, user);
            return JsonWrapper.wrapList(array);
        }


//        if (user != null)
//            return JsonWrapper.wrapObject(user);
//        else {
//            JSONObject jsonError = new JSONObject();
//            jsonError.put("success", false);
//            jsonError.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
//            return jsonError;
//        }
    }

    @CrossOrigin
    @GetMapping("userPagination")
    public JSONObject getPaginationUsers(
            @RequestParam(required = true) String orderBy,
            @RequestParam(required = true) String sortBy,
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int pageLimit
    ) {
        try {
            List<String> columns = Arrays.asList("userId", "name", "surname", "gender", "email", "birth", "telephone", "confirmedEmail");
            List<Object[]> allUsers = userService.getPaginationUsers(orderBy, sortBy, page, pageLimit);
            JSONArray array = getJsonArrayFromObjects(columns, allUsers);
            return JsonWrapper.wrapList(array);
        } catch (Exception ex) {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("error", JsonWrapper.wrapError("Not valid pagination params", ReplyCodes.NOT_VALID_PAGINATION_PARAMS_ERROR));
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
            columns = Arrays.asList("userId", "name", "surname", "gender", "email", "birth", "telephone", "confirmedEmail");

            List<Object[]> allUsers = userService.getAllUsers();
            JSONArray array = getJsonArrayFromObjects(columns, allUsers);
            return JsonWrapper.wrapList(array);
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
            jsonError.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
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
            jsonError.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
            return jsonError;
        }
    }

    @CrossOrigin
    @PostMapping("user")
    public JSONObject addUser(@RequestBody User user, UriComponentsBuilder builder) {
        int returnedValue = userService.registerUser(user);
        if (returnedValue == ReplyCodes.USER_ALREADY_EXIST_ERROR) {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("error", JsonWrapper.wrapError("User with entered email already exist", ReplyCodes.ALREADY_EXIST_ERROR));
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
            error.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
            return error;
        }
    }

    @CrossOrigin
    @DeleteMapping("user/{id}")
    public JSONObject deleteUser(@PathVariable("id") Integer id) {
        int deleteResult = userService.deleteUserById(id);
        JSONObject jsonResponse = new JSONObject();
        switch (deleteResult) {
            case ReplyCodes.USER_NOT_EXIST_ERROR:
                jsonResponse.put("success", false);
                jsonResponse.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
                break;
            case ReplyCodes.USER_NOT_DELETED_ERROR:
                jsonResponse.put("success", false);
                jsonResponse.put("error", JsonWrapper.wrapError("User not deleted", ReplyCodes.NOT_DELETED_ERROR));
                break;
            case ReplyCodes.USER_DELETED_SUCCESSFULLY:
                jsonResponse.put("success", true);
                break;
            default:
                break;
        }
        return jsonResponse;
    }

}
