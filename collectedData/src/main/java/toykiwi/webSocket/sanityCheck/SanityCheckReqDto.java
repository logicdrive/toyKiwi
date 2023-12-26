package toykiwi.webSocket.sanityCheck;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SanityCheckReqDto {
    String message;

    public SanityCheckReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.message = jsonObject.getString("message");
    }
}
