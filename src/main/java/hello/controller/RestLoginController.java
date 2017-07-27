package hello.controller;

import hello.secure.service.TokenAuthenticationService;
import hello.secure.service.UserDetailsServiceImpl;
import hello.model.User;
import hello.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private boolean checkPassword(User inputUser, User dbUser){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String salt=dbUser.getSalt();
        if(passwordEncoder.matches(salt+inputUser.getPassword(),dbUser.getPassword())) {
            return true;
        }
        return false;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user, /*@RequestBody String email, @RequestBody String password,*/ UriComponentsBuilder builder) {
        User userByMail = userService.getUserByMail(user.getEmail());
        if(userByMail==null){
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            json.put("message", "Email does not exists");

            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
        else if (checkPassword(user,userByMail)) {
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            org.springframework.security.core.userdetails.User uD = new org.springframework.security.core.userdetails.User(userByMail.getEmail(), userByMail.getPassword(), true, true, true, true, roles);
            String tokenForUser = this.authenticationService.tokenHandler.createTokenForUser(uD);
            Map<String, Object> json = new HashMap<>();
            json.put("success", true);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email",userByMail.getEmail());
            jsonObj.put("name",userByMail.getName());
            jsonObj.put("userId", userByMail.getUserId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            headers.add("data", jsonObj.toString());
            headers.add("X-AUTH-TOKEN", tokenForUser);

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } else {
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            json.put("message", "Wrong password");

            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
    }
}
