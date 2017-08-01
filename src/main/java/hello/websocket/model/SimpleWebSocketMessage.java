package hello.websocket.model;

/**
 * Created by Tom on 28.07.2017.
 */

public class SimpleWebSocketMessage {

    public SimpleWebSocketMessage() {
    }

    private String content;

    public SimpleWebSocketMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
