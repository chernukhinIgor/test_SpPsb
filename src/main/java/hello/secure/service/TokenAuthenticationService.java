package hello.secure.service;

import hello.secure.TokenHandler;
import hello.secure.model.UserAuthentication;
import hello.secure.model.UserSecured;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tom on 27.07.2017.
 */

@Service("tokenAuthenticationService")
public class TokenAuthenticationService {

        private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @Autowired
    public TokenHandler tokenHandler;

//    public TokenAuthenticationService(String secret, UserDetailsServiceImpl userService) {
//        tokenHandler = new TokenHandler(secret, userService);
//    }

//    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
//        final User user = authentication.getDetails();
//        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
//    }

    public Authentication getAuthentication(HttpServletRequest request) throws ExpiredJwtException {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            //final User user = tokenHandler.parseUserFromToken(token);
            final UserSecured user = (UserSecured) tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
    public Authentication getAuthentication(StompHeaderAccessor accessor) throws ExpiredJwtException {
        final String token = accessor.getFirstNativeHeader(AUTH_HEADER_NAME);
        if (token != null) {
            //final User user = tokenHandler.parseUserFromToken(token);
            final UserSecured user = (UserSecured) tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
