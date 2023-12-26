package toykiwi.webSocket.sanityCheck;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

// Sanity Check를 위한 단순한 Echo 서비스를 구현하기 위해서
@Component
public class SanityCheckSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();
        CLIENTS.entrySet().forEach( arg->{
            if(arg.getKey().equals(id)) {
                try {
                    
                    SanityCheckReqDto sanityCheckReqDto = new SanityCheckReqDto(message);
                    CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{sanityCheckReqDto: %s}", sanityCheckReqDto.toString()));

                    SanityCheckResDto sanityCheckResDto = new SanityCheckResDto(sanityCheckReqDto.getMessage());
                    CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{sanityCheckResDto: %s}", sanityCheckResDto.toString()));
                    
                    arg.getValue().sendMessage(sanityCheckResDto.jsonTextMessage());

                } catch (IOException e) {
                    CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
                }
            }
        });
    }
}
