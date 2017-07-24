package hello.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import hello.service.UserService;
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
    public JsonObject getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);

        JsonObject value = Json.createObjectBuilder()
//                .add("firstName", "John")
//                .add("lastName", "Smith")
//                .add("age", 25)
                .add("data", Json.createObjectBuilder()
                        .add("userName", user.getName())
                        .add("city", "New York")
                        .add("state", "NY")
                        .add("postalCode", "10021"))
//                .add("phoneNumber", Json.createArrayBuilder()
//                        .add(Json.createObjectBuilder()
//                                .add("type", "home")
//                                .add("number", "212 555-1234"))
//                        .add(Json.createObjectBuilder()
//                                .add("type", "fax")
//                                .add("number", "646 555-4567")))
                .build();

        // new ResponseEntity<>(user, HttpStatus.OK);
        return value;
    }

    @GetMapping("users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
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
