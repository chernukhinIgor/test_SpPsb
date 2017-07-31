package hello.websocket.config;

import hello.secure.TokenHandler;
import hello.secure.service.TokenAuthenticationService;
import hello.websocket.filter.WebSocketChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

/**
 * Created by Tom on 28.07.2017.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private DefaultSimpUserRegistry userRegistry = new DefaultSimpUserRegistry();
    private DefaultUserDestinationResolver resolver = new DefaultUserDestinationResolver(userRegistry);

    @Bean
    @Primary
    public SimpUserRegistry userRegistry() {
        return userRegistry;
    }

    @Bean
    @Primary
    public UserDestinationResolver userDestinationResolver() {
        return resolver;
    }

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private TokenAuthenticationService authenticationService;

    //
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        registration.setInterceptors(new WebSocketChannelInterceptor(tokenHandler,authenticationService,userRegistry));
        //registration.setInterceptors(new WebSocketChannelFilter(tokenHandler,authenticationService));
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //stompEndpointRegistry.addEndpoint("/ws");
        stompEndpointRegistry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/notifications");
        config.setApplicationDestinationPrefixes("/wsApp");
    }
}