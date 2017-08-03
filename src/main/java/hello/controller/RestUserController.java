package hello.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hello.utils.ReplyCodes;
import hello.utils.JsonWrapper;
import hello.model.Task;
import hello.model.User;
import hello.service.UserService;

import static hello.utils.JsonWrapper.getJsonArrayFromObjects;

@RestController
public class RestUserController {

    @Autowired
    private UserService userService;

    private List<String> COLUMNS = Arrays.asList("userId", "name", "surname", "gender", "email", "birth", "telephone", "confirmedEmail");

    private List<String> SORT_TYPE = Arrays.asList("desc", "asc");


    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @CrossOrigin
    @GetMapping("user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {
        List<Object[]> user = userService.getUserListById(id);
        if (user.isEmpty()) {
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User does not exist",ReplyCodes.NOT_EXIST_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.NOT_FOUND);
        } else {
            JSONArray userData = getJsonArrayFromObjects(COLUMNS, user);
            JSONObject response=JsonWrapper.jsonSuccessObject(userData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("userPagination")
    public ResponseEntity<?> getPaginationUsers(
            @RequestParam(required = true) String orderBy, @RequestParam(required = true) String sortBy,
            @RequestParam(required = true) int page, @RequestParam(required = true) int pageLimit) {
        if(!COLUMNS.contains(orderBy)||!SORT_TYPE.contains(sortBy)||page<=0){
            JSONObject jsonError=JsonWrapper.jsonErrorObject("Not valid pagination params",ReplyCodes.NOT_VALID_PAGINATION_PARAMS_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.BAD_REQUEST);
        }else {
            List<Object[]> allUsers = userService.getPaginationUsers(orderBy, sortBy, page, pageLimit);
            JSONArray usersData = getJsonArrayFromObjects(COLUMNS, allUsers);
            JSONObject response=JsonWrapper.jsonSuccessObject(usersData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("userCount")
    public ResponseEntity<?> getUserCount() {
        int userCount = userService.getUserCount();
        JSONObject response = JsonWrapper.jsonSuccessObject(userCount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("users")
    public ResponseEntity getAllUsers(@RequestParam(required = false, value = "params") List<String> columns) {
        List<Object[]> allUsers;
        JSONArray usersData;
        JSONObject response;
        if(columns != null) {
            if(COLUMNS.containsAll(columns)){
                allUsers = userService.getParametricUsers(columns);
            }else {
                response=JsonWrapper.jsonErrorObject("Not valid params",ReplyCodes.NOT_VALID_PARAMS_ERROR);
                return new ResponseEntity(response, HttpStatus.OK);
            }
            usersData = getJsonArrayFromObjects(columns, allUsers);
            response=JsonWrapper.jsonSuccessObject(usersData);
        } else {
            allUsers = userService.getAllUsers();
            usersData = getJsonArrayFromObjects(COLUMNS, allUsers);
            response=JsonWrapper.jsonSuccessObject(usersData);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("userCreatedTasks/{id}")
    public ResponseEntity<?> getAllCreatedTasks(@PathVariable("id") Integer id) {
        if (userService.userExists(id)) {
            List<Task> allCreatedTasks = userService.getCreatedTasks(id);
            List<Object> objectList = new ArrayList<>(allCreatedTasks);
            JSONObject response = JsonWrapper.jsonSuccessObject(objectList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User does not exist",ReplyCodes.NOT_EXIST_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("userResponsibleTasks/{id}")
    public ResponseEntity<?> getAllResponsibleTasks(@PathVariable("id") Integer id) {
        if (userService.userExists(id)) {
            List<Task> allResponsibleTasks = userService.getResponsibleTasks(id);
            List<Object> objectList = new ArrayList<>(allResponsibleTasks);
            JSONObject response = JsonWrapper.jsonSuccessObject(objectList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User does not exist",ReplyCodes.NOT_EXIST_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @PostMapping("user")
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder builder) {
        int MIN_PASSWORD_LENGTH = 6;
        if(user.getEmail()==null||user.getPassword()==null){
            JSONObject jsonError=JsonWrapper.jsonErrorObject("Email or password not entered",ReplyCodes.EMAIL_OR_PASSWORD_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }else if(user.getPassword().length()< MIN_PASSWORD_LENGTH ||!isValidEmailAddress(user.getEmail())){
            JSONObject jsonError=JsonWrapper.jsonErrorObject("Email or password not valid. Min password length is "+
                    MIN_PASSWORD_LENGTH +" symbols",ReplyCodes.EMAIL_OR_PASSWORD_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }else if(user.getUserId()!=0){
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User id not needed",ReplyCodes.NOT_VALID_PARAMS_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }

        int returnedValue = userService.registerUser(user);
        if (returnedValue == ReplyCodes.USER_ALREADY_EXIST_ERROR) {
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User with entered email already exist",ReplyCodes.ALREADY_EXIST_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }
        JSONObject data = new JSONObject();
        data.put("userId", returnedValue);
        JSONObject response = JsonWrapper.jsonSuccessObject(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method can not update password, salt, emailConfirm
     * @param user - new user data, must contain exist userId
     * @return responseEntity with result
     */
    @CrossOrigin
    @PutMapping("user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            JSONObject response = new JSONObject();
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            JSONObject jsonError=JsonWrapper.jsonErrorObject("User does not exist",ReplyCodes.NOT_EXIST_ERROR);
            return new ResponseEntity<>(jsonError, HttpStatus.OK);
        }
    }

    // TODO: Role access, allow only admin do this.
    @CrossOrigin
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        int deleteResult = userService.deleteUserById(id);
        JSONObject jsonResponse = new JSONObject();
        switch (deleteResult) {
            case ReplyCodes.USER_NOT_EXIST_ERROR:
                jsonResponse=JsonWrapper.jsonErrorObject("User does not exist",ReplyCodes.NOT_EXIST_ERROR);
                break;
            case ReplyCodes.USER_NOT_DELETED_ERROR:
                jsonResponse=JsonWrapper.jsonErrorObject("User not deleted",ReplyCodes.NOT_DELETED_ERROR);
                break;
            case ReplyCodes.USER_DELETED_SUCCESSFULLY:
                jsonResponse.put("success", true);
                break;
            default:
                break;
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}