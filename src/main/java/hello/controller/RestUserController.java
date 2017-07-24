package hello.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import hello.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hello.model.User;
import org.springframework.web.util.UriComponentsBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestUserController {
	@Autowired
	private UserService userService;

    @GetMapping("user/{id}")
    public JSONObject getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);

        JSONObject dataSet = new JSONObject();
        dataSet.put("success", "True") ;
        dataSet.element("data", user);


        return dataSet;
        //return value;
    }

    @GetMapping("users")
    public JSONObject getAllUsers() {
        Iterable<User> allUsers = userService.getAllUsers();


        JSONObject dataSet = new JSONObject();
        dataSet.put("success", true) ;
        dataSet.element("data", allUsers);

        return dataSet;

        //return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
    @PostMapping("user")
    public String addUser(@RequestBody User user, UriComponentsBuilder builder) {
        boolean flag = userService.addUser(user);
        if (!flag) {
            return "Error in POST";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
        return "Posted";
    }
    @PutMapping("user")
    public String updateUser (@RequestBody User user) {
        if (userService.updateUser(user))
            return "User updated";
        else
            return "User not found";
    }
    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return "User deleted";
    }

}
