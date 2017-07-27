package hello.configuration;

import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 27.07.2017.
 */

public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        hello.model.User userByMail = userService.getUserByMail(username);
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        User usr = new User(userByMail.getEmail(),userByMail.getPassword(),true,true,true,true,
                roles);
        if (userByMail == null) {
            throw new UsernameNotFoundException("user not found");
        }
        detailsChecker.check(usr);
        return usr;
    }
}