package hello.websocket.config;

import hello.websocket.handler.SimpleWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by Tom on 01.08.2017.
 */
@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketSimpleConfig implements WebSocketConfigurer {

    @Autowired
    SimpleWebSocketHandler simpleWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(simpleWebSocketHandler, "/simpleChannel/*")
                .setAllowedOrigins("*");
                //.addInterceptors(new HttpSessionHandshakeInterceptor())
    }

}
