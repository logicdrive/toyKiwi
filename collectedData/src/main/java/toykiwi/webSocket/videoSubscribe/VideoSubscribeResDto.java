package toykiwi.webSocket.videoSubscribe;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import lombok.ToString;

@ToString
public class VideoSubscribeResDto {
    Long videoId;
    String videoStatus;


    public VideoSubscribeResDto(Long videoId, String videoStatus) {
        this.videoId = videoId;
        this.videoStatus = videoStatus;
    }

    public TextMessage jsonTextMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("videoId", this.videoId);
        jsonObject.put("videoStatus", this.videoStatus);
        return new TextMessage(jsonObject.toString());
    }
}
