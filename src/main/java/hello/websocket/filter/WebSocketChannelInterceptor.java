package hello.websocket.filter;

import hello.secure.TokenHandler;
import hello.secure.service.TokenAuthenticationService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

/**
 * Created by Tom on 28.07.2017.
 */
public class WebSocketChannelInterceptor extends ChannelInterceptorAdapter {

    private TokenHandler tokenHandler;

    private TokenAuthenticationService authenticationService;

    public WebSocketChannelInterceptor(TokenHandler tokenHandler, TokenAuthenticationService authenticationService) {
        this.tokenHandler = tokenHandler;
        this.authenticationService = authenticationService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");
            Authentication authentication = authenticationService.getAuthentication(accessor);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authToken == null)
                return null;
            User user = tokenHandler.parseUserFromToken(authToken);
            Principal principal = () -> user.getUsername();
            accessor.setUser(principal);
        }

        return message;
    }
}
