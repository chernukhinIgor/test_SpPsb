package hello.secure.filter;

import hello.redis.SessionService;
import hello.secure.service.TokenAuthenticationService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tom on 27.07.2017.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService authenticationService;

    public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");



        Authentication authentication = null;
        try {
            authentication = authenticationService.getAuthentication(httpRequest);
        } catch (ExpiredJwtException e) {
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("error", JsonWrapper.wrapError("Token expired", ReplyCodes.TOKEN_EXPIRED_ERROR));
            httpResponse.addHeader("Content-Type","application/json;charset=UTF-8");
            httpResponse.getWriter().print(json);
            return;
        }catch (Exception e){
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("error", JsonWrapper.wrapError("Invalid token", ReplyCodes.TOKEN_INVALID_ERROR));
            httpResponse.addHeader("Content-Type","application/json;charset=UTF-8");
            httpResponse.getWriter().print(json);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
