package hello.secure.config;

import hello.redis.SessionService;
import hello.secure.filter.StatelessAuthenticationFilter;
import hello.secure.service.TokenAuthenticationService;
import hello.secure.service.UserDetailsServiceImpl;
import hello.service.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * Created by Tom on 27.07.2017.
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private final UserDetailsServiceImpl userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

//    public SpringSecurityConfig() {
//        super(true);
//        this.userService = new UserDetailsServiceImpl();
//        //tokenAuthenticationService = new TokenAuthenticationService("tooManySecrets", userService);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
            .and()
                .anonymous()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .servletApi()
            .and()
                .logout().logoutSuccessHandler(new LogoutSuccessHandler(sessionService))
            .and()
                .headers().cacheControl()
            .and()
                .authorizeRequests()

                // Allow anonymous resource requests
//                .antMatchers("/").permitAll()
//                .antMatchers("/favicon.ico").permitAll()
//                .antMatchers("**/*.html").permitAll()
//                .antMatchers("**/*.css").permitAll()
//                .antMatchers("**/*.js").permitAll()

                // Allow anonymous login



                .antMatchers("/login").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/confirmEmail").permitAll()
                .antMatchers("/forgotPassword").permitAll()
                .antMatchers("/updatePassword").permitAll()

                //.antMatchers("/users").permitAll()//
                //.antMatchers("/user/**").permitAll()//
                .antMatchers("/simpleChannel/**").permitAll()//
                .antMatchers("/channel/**").permitAll()//
                .antMatchers("/ws/**").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()

                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }


//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    @Override
//    public UserDetailsServiceImpl userDetailsService() {
//        return userService;
//    }
//
//    @Bean
//    public TokenAuthenticationService tokenAuthenticationService() {
//        return tokenAuthenticationService;
//    }
//
//    @Bean
//    public String secret() {
//        return "tooManySecrets";
//    }
}
