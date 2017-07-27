package hello.secure.filter;

import hello.secure.service.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
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
        HttpServletResponse httpResponse= (HttpServletResponse) response;
        Authentication authentication = null;
        try {
            authentication = authenticationService.getAuthentication(httpRequest);
        } catch (ExpiredJwtException e) {
            JSONObject json = new JSONObject();
            json.put("success",false);
            JSONObject error = new JSONObject();
            error.put("errorMessage","Token expired");
            error.put("code",99989);
            json.put("error", error);
            httpResponse.addHeader("Content-Type","application/json;charset=UTF-8");
            httpResponse.getWriter().print(json);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
