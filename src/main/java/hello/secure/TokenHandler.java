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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 27.07.2017.
 */
@Service
public final class TokenHandler {
    private long EXPIRATION_TIME;

    //100 Min
   // static final long EXPIRATION_TIME = 100 * 60 * 1000;

    // 24 hours to confirm email
    //static final long CONFIRM_EMAIL_TIME = 24 * 60 * 60 * 1000;

    private final String secret="tooManySecrets";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

//    public TokenHandler(String secret, UserDetailsServiceImpl userDetailsService) {
//        this.secret = secret;
//        //this.userDetailsService = userDetailsService;
//    }

    public User parseUserFromToken(String token) throws ExpiredJwtException {
        //Jwts.parser().parseClaimsJws(token);
        try {
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            hello.model.User userByMail = userService.getUserByMail(username);
            Set<GrantedAuthority> roles=new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//            User usr=new User(userDetails.getUsername(),userDetails.getPassword(),true,true,
//                    true,true,roles);
            UserSecured uc = new UserSecured(userDetails.getUsername(),userDetails.getPassword(),true,true,
                    true,true,roles, userByMail.getUserId());
            return uc;

            //return userDetailsService.loadUserByUsername(username);
        }
        catch (Exception e){
            throw e;
        }
    }

    public String createTokenForUser(User user, TokenType tokenType) {
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
