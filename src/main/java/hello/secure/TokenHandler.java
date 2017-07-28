package hello.secure;

import hello.secure.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

/**
 * Created by Tom on 27.07.2017.
 */
public final class TokenHandler {

    //1 Min
    static final long EXPIRATION_TIME = 60 * 1000;
    private final String secret;
    private final UserDetailsServiceImpl userService;

    public TokenHandler(String secret, UserDetailsServiceImpl userService) {
        this.secret = secret;
        this.userService = userService;
    }

    public User parseUserFromToken(String token) throws ExpiredJwtException {
        try {
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return userService.loadUserByUsername(username);
        }
        catch (Exception e){
            throw e;
        }
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
