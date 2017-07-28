package hello.controller;

import hello.model.Task;
import hello.secure.model.UserAuthentication;
import hello.service.TaskService;
import hello.websocket.model.Greeting;
import hello.websocket.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tom on 28.07.2017.
 */
@RestController
public class WebSocketController {

    @Autowired
    private TaskService taskService;
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

    @MessageMapping("/userPushedTask")
    @SendTo("/newTask")
    public Task taskCreated(Integer taskId) throws Exception {
        Task taskById = taskService.getTaskById(taskId);
        return taskById;
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
