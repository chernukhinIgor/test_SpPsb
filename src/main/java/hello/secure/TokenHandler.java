package hello.secure;

import hello.secure.model.UserSecured;
import hello.service.UserService;
import hello.utils.TokenType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 27.07.2017.
 */
@Service
public final class TokenHandler {

    private final String secret="tooManySecrets";

    @Autowired
    private UserService userService;

    public User parseUserFromToken(String token) throws ExpiredJwtException, UsernameNotFoundException {
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            hello.model.User userByMail = userService.getUserByMail(username);
            if(userByMail==null){
                throw new UsernameNotFoundException("User not found in db");
            }

            Set<GrantedAuthority> roles=new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UserSecured uc = new UserSecured(userByMail.getEmail(),userByMail.getPassword(),true,true,
                    true,true,roles, userByMail.getUserId());
            return uc;
    }

    public String createTokenForUser(User user, TokenType tokenType) {
        long EXPIRATION_TIME;
        if(tokenType==TokenType.REMEMBER_ME){
            EXPIRATION_TIME = 365 * 24 * 60 * 60 * 1000;// 1 year
        }else if(tokenType==TokenType.EMAIL_CONFIRM){
            EXPIRATION_TIME = 24 * 60 * 60 * 1000;// 24 hours
        }else {
            EXPIRATION_TIME = 100 * 60 * 1000;// 100 min
        }
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
