package hello.websocket.controller;

import hello.model.Task;
import hello.secure.model.UserAuthentication;
import hello.service.TaskService;
import hello.websocket.model.SimpleWebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom on 28.07.2017.
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TaskService taskService;

    @MessageMapping("/chat")
    @SendTo("/chatRoom")
    public SimpleWebSocketMessage chatting(SimpleWebSocketMessage message, SimpMessageHeaderAccessor accessor) throws Exception {
        Principal user1 = accessor.getUser();
        String user = "anonymous";
        if (user1 != null)
            user = user1.getName();
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new SimpleWebSocketMessage(user + " : " + message.getContent() + "  (" + time + ")");
    }

    @MessageMapping("/userPushedTask")
    public void taskPushed(Integer taskId) throws Exception {
        Task taskById = taskService.getTaskById(taskId);
        int responsibleUserId = taskById.getResponsibleUserId();
        template.convertAndSend("/channel/" + responsibleUserId, taskById);
    }

    @SendTo("/channel/{clientId}")
    @MessageMapping("/channel/{clientId}")
    public SimpleWebSocketMessage message(@DestinationVariable("clientId") String clientId, @Payload Message<?> message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        System.out.println(accessor.toString());
        System.out.println(clientId);
        return new SimpleWebSocketMessage("Message, " + clientId + "!");
    }

    @SendTo("/channel/{clientId}")
    @SubscribeMapping("/channel/{clientId}")
    public SimpleWebSocketMessage subscribe(@DestinationVariable("clientId") String clientId, SimpMessageHeaderAccessor accessor) {
        UserAuthentication userAuth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (userAuth != null) {
            System.out.println("User from security context: " + userAuth.getUser().getUsername());
        }
        Principal principal = accessor.getUser();
        if (principal != null) {
            System.out.println("User from accessor: " + principal.getName());
        }
        //template.convertAndSend("/channel/" + clientId, "Successfully subscribed /channel/" + clientId);
        return new SimpleWebSocketMessage("Successfully subscribed /channel/" + clientId);
    }

}
