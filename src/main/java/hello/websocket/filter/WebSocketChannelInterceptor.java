package hello.websocket.filter;

import hello.Application;
import hello.secure.TokenHandler;
import hello.secure.service.TokenAuthenticationService;
import org.springframework.context.ApplicationEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.messaging.*;

import java.security.Principal;

/**
 * Created by Tom on 28.07.2017.
 */
public class WebSocketChannelInterceptor extends ChannelInterceptorAdapter {

    private DefaultSimpUserRegistry userRegistry;

    private TokenHandler tokenHandler;

    private TokenAuthenticationService authenticationService;

    public WebSocketChannelInterceptor(TokenHandler tokenHandler, TokenAuthenticationService authenticationService, DefaultSimpUserRegistry userRegistry) {
        this.tokenHandler = tokenHandler;
        this.authenticationService = authenticationService;
        this.userRegistry = userRegistry;
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return super.preReceive(channel);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompHeaderAccessor accessorStomp = StompHeaderAccessor.wrap(message);
        //if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
        String authToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");
        Authentication authentication = authenticationService.getAuthentication(accessor);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SimpMessageType messageType = accessor.getMessageType();
        System.out.println(messageType);
        /*if (authToken == null)
            return null;*/
        User user = tokenHandler.parseUserFromToken(authToken);
        Principal principal = () -> user.getUsername();
        if (accessorStomp.getMessageType() == SimpMessageType.CONNECT) {
            userRegistry.onApplicationEvent(new SessionConnectedEvent(this, (Message<byte[]>) message, principal));
        } else if (accessorStomp.getMessageType() == SimpMessageType.SUBSCRIBE) {
            userRegistry.onApplicationEvent(new SessionSubscribeEvent(this, (Message<byte[]>) message, principal));
        } else if (accessorStomp.getMessageType() == SimpMessageType.UNSUBSCRIBE) {
            userRegistry.onApplicationEvent(new SessionUnsubscribeEvent(this, (Message<byte[]>) message, principal));
        } else if (accessorStomp.getMessageType() == SimpMessageType.DISCONNECT) {
            userRegistry.onApplicationEvent(new SessionDisconnectEvent(this, (Message<byte[]>) message, accessor.getSessionId(), CloseStatus.NORMAL));
        }
        accessorStomp.setUser(principal);
        accessorStomp.setLeaveMutable(true);
        //}

        //return message;
        return MessageBuilder.createMessage(message.getPayload(), accessorStomp.getMessageHeaders());
    }
}
