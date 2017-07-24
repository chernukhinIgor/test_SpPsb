package hello.controller;

import hello.repository.UserRepository;
import hello.service.UserService;
import hello.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import hello.model.User;

import java.io.IOException;

@RestController
public class MainController {
	@Autowired
	private UserService userService;


    // Data as payload
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updatePerson(@RequestBody User user) throws IOException {
        try{
            userService.addUser(user);
        }catch (Exception ex){
            return "Error adding user";
        }
        return "Saved";
    }

	@RequestMapping(path="/delete", method = RequestMethod.POST)
    public String deleteUserById(@RequestParam(value="id", defaultValue = "-1") long id){
	    try{
            userService.deleteUserById(id);
        }catch (Exception ex){
	        return "Error deleting user";
        }
        return "User succesfully deleted!";
    }

	@RequestMapping(path="/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
