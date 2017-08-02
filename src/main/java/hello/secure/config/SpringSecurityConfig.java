package hello.secure.config;

import hello.redis.SessionService;
import hello.secure.filter.StatelessAuthenticationFilter;
import hello.secure.service.TokenAuthenticationService;
import hello.service.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Tom on 27.07.2017.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionService sessionService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
//            .and()
//                .anonymous()
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

                .antMatchers("/login").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/confirmEmail").permitAll()
                .antMatchers("/forgotPassword").permitAll()
                .antMatchers("/updatePassword").permitAll()

                .antMatchers("/simpleChannel/**").permitAll()//
                .antMatchers("/channel/**").permitAll()//
                .antMatchers("/ws/**").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()

                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
