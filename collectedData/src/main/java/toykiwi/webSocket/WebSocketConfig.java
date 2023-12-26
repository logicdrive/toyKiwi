package toykiwi.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;
import toykiwi.webSocket.sanityCheck.SanityCheckSocketHandler;
import toykiwi.webSocket.videoSubscribe.VideoSubscribeSocketHandler;

// WebSocket에 관련 경로들을 등록시키기 위해서
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SanityCheckSocketHandler sanityCheckSocketHandler;
    private final VideoSubscribeSocketHandler videoSubscribeSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sanityCheckSocketHandler, "/socket/sanityCheck").setAllowedOrigins("*");
        registry.addHandler(videoSubscribeSocketHandler, "/socket/videoSubscribe").setAllowedOrigins("*");
    }
}