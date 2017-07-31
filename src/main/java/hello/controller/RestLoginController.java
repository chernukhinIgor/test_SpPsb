package hello.controller;

import hello.model.loginUser;
import hello.secure.service.TokenAuthenticationService;
import hello.secure.service.UserDetailsServiceImpl;
import hello.model.User;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import hello.utils.TokenType;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

//    public RestLoginController() {
//        userDetailsService = new UserDetailsServiceImpl();
//        authenticationService = new TokenAuthenticationService("tooManySecrets", userDetailsService);
//    }

    private boolean checkPassword(loginUser inputUser, User dbUser){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String salt=dbUser.getSalt();
        if(passwordEncoder.matches(salt+inputUser.getPassword(),dbUser.getPassword())) {
            return true;
        }
        return false;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody loginUser user,/*@RequestBody String email, @RequestBody String password,*/ UriComponentsBuilder builder) {
        User userByMail = userService.getUserByMail(user.getEmail());
        if(userByMail==null){
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            json.put("error", JsonWrapper.wrapError("Wrong email or password", ReplyCodes.EMAIL_OR_PASSWORD_ERROR));
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);

        }else if(!userByMail.isConfirmedEmail()){
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            json.put("error", JsonWrapper.wrapError("Email does not confirmed", ReplyCodes.EMAIL_NOT_CONFIRMED_ERROR));
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);

        }else if (checkPassword(user,userByMail)) {
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            org.springframework.security.core.userdetails.User uD = new org.springframework.security.core.userdetails.User(userByMail.getEmail(), userByMail.getPassword(), true, true, true, true, roles);
            String tokenForUser;
            if(user.isRemeberMe()){
                tokenForUser= this.authenticationService.tokenHandler.createTokenForUser(uD, TokenType.REMEMBER_ME);
            }else{
                tokenForUser= this.authenticationService.tokenHandler.createTokenForUser(uD, TokenType.REGULAR);
            }

            Map<String, Object> json = new HashMap<>();
            json.put("success", true);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email",userByMail.getEmail());
            jsonObj.put("name",userByMail.getName());
            jsonObj.put("userId", userByMail.getUserId());
            
            json.put("data", jsonObj);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            headers.add("data", jsonObj.toString());
            headers.add("X-AUTH-TOKEN", tokenForUser);
            return new ResponseEntity<>(json, headers, HttpStatus.OK);

        } else {
            Map<String, Object> json = new HashMap<>();
            json.put("success", false);
            json.put("error", JsonWrapper.wrapError("Wrong email or password", ReplyCodes.EMAIL_OR_PASSWORD_ERROR));
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }
    }
}
