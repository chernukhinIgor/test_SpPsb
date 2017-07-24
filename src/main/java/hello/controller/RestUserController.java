package hello.controller;

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

    @PostMapping("user")
    public JSONObject addUser(@RequestBody User user, UriComponentsBuilder builder) {
        int id = userService.addUser(user);
        if (id == -1) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "ERROR. Adding user failed. ID already exist");
            return error;
        }
        JSONObject data = new JSONObject();
        data.put("userId", id);
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", data);
        return response;
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
        int i = userService.deleteUserById(id);
        JSONObject response = new JSONObject();
        switch (i) {
            case -1:
                response.put("success", false);
                response.put("message", "ERROR. User not exist");
                break;
            case -2:
                response.put("success", false);
                response.put("message", "ERROR. ???");
                break;
            case 1:
                response.put("success", true);
                break;
            default:
        }
        return response;
    }

}
