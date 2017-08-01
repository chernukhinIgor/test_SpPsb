package hello.controller;

import hello.mail.EmailServiceImpl;
import hello.model.LoginUser;
import hello.model.User;
import hello.redis.SessionService;
import hello.secure.service.TokenAuthenticationService;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import hello.utils.TokenType;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

@RestController
public class RestForgotPassword {
    @Autowired
    private TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    public EmailServiceImpl emailService;

    @Autowired
    private SessionService sessionService;

    @CrossOrigin
    @GetMapping("forgotPassword")
    public JSONObject forgotPassword(@RequestParam(required = true) String email, UriComponentsBuilder builder) {

        User userByMail = userService.getUserByMail(email);
        JSONObject response = new JSONObject();
        if(userByMail==null){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Wrong email", ReplyCodes.EMAIL_OR_PASSWORD_ERROR));
            return response;
        }else if(!userByMail.isConfirmedEmail()){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Email does not confirmed", ReplyCodes.EMAIL_NOT_CONFIRMED_ERROR));
            return response;
        }

        try{
            sendTokenAtEmail( userByMail.getEmail());
        }catch (Exception ex){
            JSONObject jsonError = new JSONObject();
            jsonError.put("success", false);
            jsonError.put("error", JsonWrapper.wrapError("Cant send email", ReplyCodes.CANT_SEND_EMAIL_ERROR));
            return jsonError;
        }
        response.put("success", true);
        return response;
    }


    private void sendTokenAtEmail(String userEmail) throws Exception{
        emailService.sendSimpleMessage(userEmail,"Forgot password",generateToken(userEmail));
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
        String tokenForUser = authenticationService.tokenHandler.createTokenForUser(uD, TokenType.EMAIL_CONFIRM);
        return "http://localhost:8080/changePassword?email="+userEmail+"&token="+tokenForUser;
    }

    @CrossOrigin
    @PostMapping("updatePassword")
    public JSONObject changePassword(@RequestBody LoginUser user, UriComponentsBuilder builder) {

        JSONObject response=new JSONObject();
        org.springframework.security.core.userdetails.User usr;
        try{
            usr=authenticationService.tokenHandler.parseUserFromToken(user.getToken());
        }catch (ExpiredJwtException ex){
  //          if(sessionService.getByToken(token)!=null)
    //            sessionService.delete(sessionService.getByToken(token).getId());

            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Token expired",ReplyCodes.TOKEN_EXPIRED_ERROR));
            return response;
        }catch (Exception ex){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Token not valid",ReplyCodes.TOKEN_INVALID_ERROR));
            return response;
        }

        if(!user.getEmail().equals(usr.getUsername())){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Wrong email",ReplyCodes.NOT_VALID_PARAMS_ERROR));
            return response;
        }

        User newUser=userService.getUserByMail(user.getEmail());
        if (newUser == null){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
            return response;
        }
        newUser.setPassword(user.getPassword());

        if (userService.updatePassword(newUser)) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("User does not exist", ReplyCodes.NOT_EXIST_ERROR));
        }
        return response;
    }
}
