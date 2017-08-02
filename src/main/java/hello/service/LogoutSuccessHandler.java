package hello.service;

import hello.redis.SessionService;
import hello.secure.model.UserAuthentication;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private SessionService sessionService;

    public LogoutSuccessHandler(SessionService sessionService){
        this.sessionService=sessionService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        JSONObject json = new JSONObject();
        if(token!=null&&sessionService.getByToken(token)!=null){
            sessionService.delete(sessionService.getByToken(token).getId());
            json.put("success", true);
        }else{
            json.put("success", false);
            json.put("error", JsonWrapper.wrapError("Invalid token", ReplyCodes.TOKEN_INVALID_ERROR));
        }
        httpServletResponse.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().print(json);
    }
}
