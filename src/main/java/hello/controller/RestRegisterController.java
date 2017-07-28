package hello.controller;

import hello.mail.EmailServiceImpl;
import hello.model.User;
import hello.secure.service.TokenAuthenticationService;
import hello.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

import static hello.service.UserService.USER_ALREADY_EXIST_ERROR;

/**
 * Created by Tom on 27.07.2017.
 */

@RestController
public class RestRegisterController {
    @Autowired
    private TokenAuthenticationService authenticationService;


    @Autowired
    private UserService userService;

    @Autowired
    public EmailServiceImpl emailService;

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
        try{
            sendTokenAtEmail( user.getEmail());
        }catch (Exception ex){
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("message", "Error adding user. Can't send email.");
            userService.deleteUserById(returnedValue);
            return jsonError;
        }

        JSONObject data = new JSONObject();
        data.put("userId", returnedValue);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("data", data);
        return jsonResponse;
    }

    private void sendTokenAtEmail(String userEmail) throws Exception{
         emailService.sendSimpleMessage(userEmail,"Confirm email",generateToken(userEmail));
    }

    private String generateToken(String userEmail){
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        org.springframework.security.core.userdetails.User uD =
                new org.springframework.security.core.userdetails.User(
                        userEmail,
                        "",
                        true,
                        true,
                        true,
                        true,
                        roles);
        String tokenForUser = authenticationService.tokenHandler.createTokenForEmail(uD);
        return "http://localhost:8080/confirmEmail?token="+tokenForUser;
    }
}
