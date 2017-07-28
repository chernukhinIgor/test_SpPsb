package hello.websocket.filter;

import hello.secure.TokenHandler;
import hello.secure.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * Created by Tom on 28.07.2017.
 */
public class WebSocketChannelFilter implements ExecutorChannelInterceptor {

    private TokenHandler tokenHandler;

    private TokenAuthenticationService authenticationService;

    public WebSocketChannelFilter(TokenHandler tokenHandler, TokenAuthenticationService authenticationService) {
        this.tokenHandler = tokenHandler;
        this.authenticationService = authenticationService;
    }

    @Override
    public Message<?> beforeHandle(Message<?> message, MessageChannel messageChannel, MessageHandler messageHandler) {
        return message;
    }

    @Override
    public void afterMessageHandled(Message<?> message, MessageChannel messageChannel, MessageHandler messageHandler, Exception e) {

    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
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


    @Override
    public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

    }

    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        return false;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

    }
}
