package hello.controller;

import hello.model.User;
import hello.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static hello.service.UserService.USER_ALREADY_EXIST_ERROR;

/**
 * Created by Tom on 27.07.2017.
 */

@RestController
public class RestRegisterController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("register")
    public JSONObject registerUser(@RequestBody User user, UriComponentsBuilder builder) {
        int returnedValue = userService.registerUser(user);
        if (returnedValue == USER_ALREADY_EXIST_ERROR) {
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "Error adding user. Mail already exist");
            return jsonError;
        }
        JSONObject data = new JSONObject();
        data.put("userId", returnedValue);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("data", data);
        return jsonResponse;
    }
}
