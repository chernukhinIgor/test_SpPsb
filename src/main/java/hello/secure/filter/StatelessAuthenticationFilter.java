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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private JSONObject jsonErrorObject(String errorMessage, int errorCode){
        JSONObject jsonError = new JSONObject();
        jsonError.put("success", false);
        jsonError.put("error", JsonWrapper.wrapError(errorMessage, errorCode));
        return jsonError;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.addHeader("Content-Type", "application/json;charset=UTF-8");

//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");

        Authentication authentication;

        try {
            authentication = authenticationService.getAuthentication(httpRequest);
        } catch (ExpiredJwtException e) {
            JSONObject jsonError = jsonErrorObject("Token expired",ReplyCodes.TOKEN_EXPIRED_ERROR);
            httpResponse.getWriter().print(jsonError);
            return;
        } catch (UsernameNotFoundException e){
            JSONObject jsonError = jsonErrorObject("User not exist. Token deleted",ReplyCodes.USER_NOT_EXIST_ERROR);
            httpResponse.getWriter().print(jsonError);
            return;
        } catch (Exception e) {
            JSONObject jsonError = jsonErrorObject("Invalid token",ReplyCodes.TOKEN_INVALID_ERROR);
            httpResponse.getWriter().print(jsonError);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
