package hello.configuration;

import hello.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

//        //hello.model.User userByMail = userService.getUserByMail(username);
//        Set<GrantedAuthority> roles = new HashSet();
//        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//        User usr = new User();
//        usr.setEmail("2");
//        usr.setPassword("3");
//        org.springframework.security.core.userdetails.User uD = new org.springframework.security.core.userdetails.User(
//                usr.getEmail(),
//                usr.getPassword(),
//                true,
//                true,
//                true,
//                true,
//                roles);
//        String tokenForUser = this.authenticationService.tokenHandler.createTokenForUser(uD);
        //createTokenForUser()
//        UserAuthentication userAuthentication = new UserAuthentication(uD);
//        authenticationService.addAuthentication(httpResponse,userAuthentication);

        Authentication authentication = authenticationService.getAuthentication(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
