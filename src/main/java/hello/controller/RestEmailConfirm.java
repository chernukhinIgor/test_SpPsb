package hello.controller;

import hello.secure.service.TokenAuthenticationService;
import hello.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static hello.service.UserService.EMAIL_CONFIRMED_SUCCESSFULLY;
import static hello.service.UserService.USER_EMAIL_NOT_EXIST_ERROR;
import static hello.service.UserService.USER_NOT_EXIST_ERROR;

@RestController
public class RestEmailConfirm {

    @Autowired
    private TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("confirmEmail")
    public JSONObject getPaginationUsers(
            @RequestParam(required = true) String token){
        JSONObject response=new JSONObject();
        User usr;
        try{
            usr=authenticationService.tokenHandler.parseUserFromToken(token);
        }catch (ExpiredJwtException ex){
            response.put("success", false);
            response.put("message", "ERROR. Time for confirming email is expired");

            return response;
        }

        int result=userService.confirmEmail(usr.getUsername());

        switch (result){
            case USER_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Confirming email failed. User id not exists");
                break;
            case USER_EMAIL_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("message", "ERROR. Confirming email failed. User email not exists");
                break;
            case EMAIL_CONFIRMED_SUCCESSFULLY:
                response.put("success", true);
                break;
            default:
                break;
        }
        return response;
    }
}
