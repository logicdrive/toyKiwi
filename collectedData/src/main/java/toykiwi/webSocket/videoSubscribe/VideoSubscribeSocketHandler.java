package toykiwi.webSocket.videoSubscribe;

import java.io.IOException;
import java.util.HashMap;

// import toykiwi._global.exceptions.InvalidVideoIdException;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;

import toykiwi.domain.Video;
// import toykiwi.domain.VideoRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

// 특정 비디오의 상태 변화를 감시하고, 관련 변화를 클라이언트에게 전달해주기 위해서
@Component
@RequiredArgsConstructor
public class VideoSubscribeSocketHandler extends TextWebSocketHandler {
    private final HashMap<Long, HashMap<String, WebSocketSession>> subscribes = new HashMap<>();
    // private final VideoRepository videoRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            
            VideoSubscribeReqDto videoSubscribeReqDto = new VideoSubscribeReqDto(message);
            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{videoSubscribeReqDto: %s}", videoSubscribeReqDto.toString()));


            // 원활한 테스트를 위해서 비활성화
            // CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search video", String.format("{videoId: %s}", videoSubscribeReqDto.getVideoId()));
            // List<Video> videos = this.videoRepository.findAllByVideoId(videoSubscribeReqDto.getVideoId());
            // if(videos.size() != 1)
            //     throw new InvalidVideoIdException();
            
            // Video video = videos.get(0);
            // if((video.getStatus() == "GeneratedQnAUploaded") ||
            //     (video.getStatus() == "VideoUploadFailed") ||
            //     (video.getStatus() == "VideoRemoveRequested")) {
            //     VideoSubscribeResDto videoSubscribeResDto = new VideoSubscribeResDto(video.getVideoId(), video.getStatus());
            //     CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed video status", String.format("{videoSubscribeResDto: %s}", videoSubscribeResDto.toString()));
            //     session.sendMessage(videoSubscribeResDto.jsonTextMessage());
            //     return;
            // }


            if(!this.subscribes.containsKey(videoSubscribeReqDto.getVideoId()))
                this.subscribes.put(videoSubscribeReqDto.getVideoId(), new HashMap<>());
            if(!this.subscribes.get(videoSubscribeReqDto.getVideoId()).containsKey(session.getId()))
                this.subscribes.get(videoSubscribeReqDto.getVideoId()).put(session.getId(), session);

        } catch (Exception e) {
            CustomLogger.error(e, "", String.format("{message: %s}", message.toString()));
        }
    }

    public void notifyVideoUpdate(Video video) {
        if(!this.subscribes.containsKey(video.getVideoId())) return;

        try {

            HashMap<String, WebSocketSession> subscribedSessions = this.subscribes.get(video.getVideoId());
            for(String sessionId : subscribedSessions.keySet()) {
                WebSocketSession session = subscribedSessions.get(sessionId);
                if(!session.isOpen())
                {
                    subscribedSessions.remove(sessionId);
                    return;
                }

                VideoSubscribeResDto videoSubscribeResDto = new VideoSubscribeResDto(video.getVideoId(), video.getStatus());
                CustomLogger.debug(CustomLoggerType.EFFECT, "Notify changed video status", String.format("{videoSubscribeResDto: %s}", videoSubscribeResDto.toString()));
                session.sendMessage(videoSubscribeResDto.jsonTextMessage());
            }

            if((video.getStatus() == "GeneratedQnAUploaded") ||
               (video.getStatus() == "VideoUploadFailed") ||
               (video.getStatus() == "VideoRemoveRequested")) {
                this.subscribes.remove(video.getVideoId());
            }

        } catch (IOException e) {
            CustomLogger.error(e, "", String.format("{video: %s}", video.toString()));
        }
    }
}
