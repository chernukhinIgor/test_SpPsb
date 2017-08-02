package hello.secure.service;

import hello.redis.SessionService;
import hello.secure.TokenHandler;
import hello.secure.model.UserAuthentication;
import hello.secure.model.UserSecured;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private SessionService sessionService;

    private void deleteRedisSession(String token){
        // Need to delete session in REDIS
        if(sessionService.getByToken(token)!=null)
            sessionService.delete(sessionService.getByToken(token).getId());
    }

    public Authentication getAuthentication(HttpServletRequest request) throws ExpiredJwtException, UsernameNotFoundException {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            try {
                UserSecured user = (UserSecured) tokenHandler.parseUserFromToken(token);
                if(sessionService.getByToken(token)==null){
                    throw new ExpiredJwtException("Expired");
                }
                if (user != null) {
                    return new UserAuthentication(user);
                }
            }catch (ExpiredJwtException ex){
                deleteRedisSession(token);
                throw new ExpiredJwtException("Token expired");
            }catch (UsernameNotFoundException ex){
                deleteRedisSession(token);
                throw new UsernameNotFoundException("Not founed in db");
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
