package hello.controller;

import hello.secure.service.TokenAuthenticationService;
import hello.secure.service.UserDetailsServiceImpl;
import hello.model.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tom on 27.07.2017.
 */
@RestController
public class RestLoginController {
    private UserDetailsServiceImpl userDetailsService;
    private final TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    public RestLoginController() {
        userDetailsService = new UserDetailsServiceImpl();
        authenticationService = new TokenAuthenticationService("tooManySecrets", userDetailsService);
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user, /*@RequestBody String email, @RequestBody String password,*/ UriComponentsBuilder builder) {
        User userByMail = userService.getUserByMail(user.getEmail());
        if (userByMail.getPassword().equals(user.getPassword())) {
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            org.springframework.security.core.userdetails.User uD = new org.springframework.security.core.userdetails.User(userByMail.getEmail(), userByMail.getPassword(), true, true, true, true, roles);
            String tokenForUser = this.authenticationService.tokenHandler.createTokenForUser(uD);
            Map<String, Object> json = new HashMap<>();
            json.put("success", true);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            headers.add("X-AUTH-TOKEN", tokenForUser);
            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } else {
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
/*
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
        return jsonResponse;*/
    }
}
