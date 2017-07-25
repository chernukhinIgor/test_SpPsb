package hello.controller;

import hello.model.Task;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import hello.model.User;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static hello.service.UserService.USER_DELETED_SUCCESSFULLY;
import static hello.service.UserService.USER_NOT_DELETED_ERROR;
import static hello.service.UserService.USER_NOT_EXIST_ERROR;
import static hello.service.UserService.USER_ALREADY_EXIST_ERROR;

@RestController
public class RestUserController {
    @Autowired
    private UserService  userService;

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
    public JSONObject getAllUsers(
            @RequestParam(required = false) boolean userId,
            @RequestParam(required = false) boolean name,
            @RequestParam(required = false) boolean surname,
            @RequestParam(required = false) boolean telephone,
            @RequestParam(required = false) boolean email,
            @RequestParam(required = false) boolean gender,
            @RequestParam(required = false) boolean birth
    ) throws IllegalAccessException {
        String requestStringParams = requestHasParams(userId, name, surname, telephone, email, gender, birth);
        List<String> parameters = new ArrayList<>();
        if (userId)
            parameters.add("userId");
        if (name)
            parameters.add("name");
        if (surname)
            parameters.add("surname");
        if (telephone)
            parameters.add("telephone");
        if (email)
            parameters.add("email");
        if (gender)
            parameters.add("gender");
        if (birth)
            parameters.add("birth");
        if (requestStringParams != null) {
            JSONArray array = new JSONArray();
            List<Object[]> allUsersAsObjects = userService.getParametricUsers(requestStringParams);
            for (Object[] row : allUsersAsObjects) {
                JSONObject data = new JSONObject();
                for (int i=0;i<parameters.size();i++){
                    data.element(parameters.get(i),row[i]);
                }
                array.add(data);
            }
            return JsonWrapper.wrapList(array);
        } else {
            List<User> allUsers = userService.getAllUsers();
            List<Object> objectList = new ArrayList<>(allUsers);
            return JsonWrapper.wrapList(objectList);
        }
    }

    private String requestHasParams(boolean userId, boolean name, boolean surname, boolean telephone, boolean email, boolean gender, boolean birth) {
        if (userId || name || surname || telephone || email || gender || birth) {
            StringJoiner resultString = new StringJoiner(",");
            //Select t.A as B from table as t
            if (userId)
                resultString.add("usr.userId as userIdQ");
            if (name)
                resultString.add("usr.name as nameQ");
            if (surname)
                resultString.add("surname");
            if (telephone)
                resultString.add("telephone");
            if (email)
                resultString.add("email");
            if (gender)
                resultString.add("gender");
            if (birth)
                resultString.add("birth");
            return resultString.toString();
        }
        return null;
    }


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
