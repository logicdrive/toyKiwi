package toykiwi.webSocket.sanityCheck;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.ToString;

@ToString
public class SanityCheckResDto {
    String message;

    public SanityCheckResDto(String message) {
        this.message = message;
    }

    public TextMessage jsonTextMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", this.message);
        return new TextMessage(jsonObject.toString());
    }
}
