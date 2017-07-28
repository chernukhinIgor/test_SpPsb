package hello.controller;

import hello.secure.model.UserAuthentication;
import hello.websocket.model.Greeting;
import hello.websocket.model.HelloMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom on 28.07.2017.
 */
@RestController
public class WebSocketController {

//    private Map<Integer, String> users = new HashMap<>();
//    static Integer id = 1;

    @MessageMapping("/notificationService")
    @SendTo("/notifications")
    public Greeting greeting(HelloMessage message) throws Exception {
        Object principal = null;
        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            principal = authentication.getPrincipal();
            if (principal != null)
                System.out.println("Principal: " + principal);
        } else
            System.out.println("null =(((");
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        //Thread.sleep(1000); // simulated delay
        //return users;
        return new Greeting(principal + " : " + message.getName() + "  (" + time + ")");
    }


//    @CrossOrigin
//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public JSONObject send(Message message) throws Exception {
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message",message);
//        jsonObject.put("time",time);
//        return jsonObject;
//        //return new OutputMessage(message.getFrom(), message.getText(), time);
//    }
}
