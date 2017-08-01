package hello.controller;

import hello.model.User;
import hello.model.loginUser;
import hello.redis.SessionService;
import hello.service.UserService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class RestLogOutController {
    @Autowired
    private SessionService sessionService;

    @CrossOrigin
    @PostMapping("/userLogout")
    public JSONObject logOut(@RequestBody loginUser user, UriComponentsBuilder builder) {
        JSONObject jsonResponse = new JSONObject();
        if(sessionService.getByEmail(user.getEmail())!=null){
            sessionService.delete(sessionService.getByEmail(user.getEmail()).getId());
            jsonResponse.put("success", true);
        }else{
            jsonResponse.put("success", false);
        }
        return jsonResponse;
    }
}
