package hello.websocket.filter;

import hello.secure.model.UserAuthentication;
import hello.secure.service.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * Created by Tom on 28.07.2017.
 */
public class WebSocketChannelInterceptor extends ChannelInterceptorAdapter {
                                    /*or implements ExecutorChannelInterceptor */

    private TokenAuthenticationService authenticationService;

    public WebSocketChannelInterceptor(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        //this.userRegistry = userRegistry;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        System.out.print(accessor.getCommand().toString());
        String authToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");
        if (authToken != null && !authToken.equals("")) {
            System.out.println(", token presented");
            try {
                UserAuthentication authentication = (UserAuthentication) authenticationService.getAuthentication(accessor);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                Principal principal = () -> authentication.getUser().getUsername();
                accessor.setUser(principal);
            } catch (ExpiredJwtException e) {
                throw new IllegalArgumentException("Jwt expired.");
            }
            catch (MalformedJwtException e) {
                throw new IllegalArgumentException("Jwt malformed.");
            }
        } else {
            System.out.println(", token is not presented =(");
        }
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    //    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        System.out.println(accessor.toString());
//       /* StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        StompHeaderAccessor accessorStomp = StompHeaderAccessor.wrap(message);
//        //if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
//        String authToken = accessor.getFirstNativeHeader("X-AUTH-TOKEN");
//        Authentication authentication = authenticationService.getAuthentication(accessor);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        SimpMessageType messageType = accessor.getMessageType();
//        System.out.println(messageType);
////        if (authToken == null)
////            return null;
//        User user = tokenHandler.parseUserFromToken(authToken);
//        Principal principal = () -> user.getUsername();
//        if (accessorStomp.getMessageType() == SimpMessageType.CONNECT) {
//            userRegistry.onApplicationEvent(new SessionConnectedEvent(this, (Message<byte[]>) message, principal));
//        } else if (accessorStomp.getMessageType() == SimpMessageType.SUBSCRIBE) {
//            userRegistry.onApplicationEvent(new SessionSubscribeEvent(this, (Message<byte[]>) message, principal));
//        } else if (accessorStomp.getMessageType() == SimpMessageType.UNSUBSCRIBE) {
//            userRegistry.onApplicationEvent(new SessionUnsubscribeEvent(this, (Message<byte[]>) message, principal));
//        } else if (accessorStomp.getMessageType() == SimpMessageType.DISCONNECT) {
//            userRegistry.onApplicationEvent(new SessionDisconnectEvent(this, (Message<byte[]>) message, accessor.getSessionId(), CloseStatus.NORMAL));
//        }
//        accessorStomp.setUser(principal);
//        accessorStomp.setLeaveMutable(true);
//        //}
//
//        //return message;
//        return MessageBuilder.createMessage(message.getPayload(), accessorStomp.getMessageHeaders());
//        */
//        return message;
//    }
}
