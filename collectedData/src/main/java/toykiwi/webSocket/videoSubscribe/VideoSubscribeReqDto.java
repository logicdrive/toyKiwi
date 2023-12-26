package toykiwi.webSocket.videoSubscribe;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class VideoSubscribeReqDto {
    Long videoId;

    public VideoSubscribeReqDto(TextMessage textMessage) {
        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        this.videoId = jsonObject.getLong("videoId");
    }
}
