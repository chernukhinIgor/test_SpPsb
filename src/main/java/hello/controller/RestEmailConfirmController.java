package hello.controller;

import hello.secure.service.TokenAuthenticationService;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.utils.ReplyCodes;

@RestController
public class RestEmailConfirmController {

    @Autowired
    private TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("confirmEmail")
    public JSONObject getPaginationUsers(@RequestParam(required = true) String token){
        JSONObject response=new JSONObject();
        User usr;
        try{
            usr=authenticationService.tokenHandler.parseUserFromToken(token);
        }catch (ExpiredJwtException ex){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Token expired",ReplyCodes.TOKEN_EXPIRED_ERROR));
            return response;
        }catch (Exception ex){
            response.put("success", false);
            response.put("error", JsonWrapper.wrapError("Token not valid",ReplyCodes.TOKEN_UNKNOWN_ERROR));
            return response;
        }

        int result=userService.confirmEmail(usr.getUsername());

        switch (result){
            case ReplyCodes.EMAIL_CONFIRMED_SUCCESSFULLY:
                response.put("success", true);
                break;

            case ReplyCodes.USER_EMAIL_NOT_EXIST_ERROR:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Confirming email failed. User email not exists",ReplyCodes.USER_EMAIL_NOT_EXIST_ERROR));
                break;

            default:
                response.put("success", false);
                response.put("error", JsonWrapper.wrapError("Unknown error",ReplyCodes.TOKEN_UNKNOWN_ERROR));
                break;
        }
        return response;
    }
}
