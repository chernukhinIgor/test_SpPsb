package hello.websocket.handler;

import hello.model.Task;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 01.08.2017.
 */
@Component
public class SimpleWebSocketHandler extends TextWebSocketHandler {

    private Map<String,WebSocketSession> sessionContainer = new HashMap<>();

    public void taskCreated(String uri, Task task) {
        System.out.println("TaskCreatedCallback...");
        WebSocketSession webSocketSession = sessionContainer.get(uri);
        if (webSocketSession != null && webSocketSession.isOpen()){
            try {
                System.out.println("Now sending task:" + task);
                webSocketSession.sendMessage(new TextMessage("{\"value\": \"" + task + "\"}"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Don't have open session to send:");
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        URI uri = session.getUri();
        System.out.println("Connection established");
        sessionContainer.put(uri.toString(),session);
        session.sendMessage(new TextMessage("{\"session uri\": \"" + session.getUri() + "\"}"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession remove = sessionContainer.remove(session.getUri().toString());
        if (remove == session){
            System.out.println("Connection closed: "+ remove);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
            session.close();
        } else {
            System.out.println("Received:" + message.getPayload());
        }
    }

}