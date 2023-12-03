package toykiwi.infra;

import toykiwi.domain.Video;
import toykiwi.domain.VideoRepository;
import toykiwi.dto.NotifyUploadedVideoReqDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.Optional;

@RestController
@Transactional
public class VideoController {
    private final Logger logger = LoggerFactory.getLogger("toykiwi.video.custom");

    @Autowired
    VideoRepository videoRepository;

    // 비디오가 외부 저장소에 업로드되었음을 알리고, 관련 URL을 전달해주기 위해서
    @RequestMapping(
        value = "videos/{id}/notifyuploadedvideo",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Video notifyUploadedVideo(
        @PathVariable(value = "id") Long id,
        @RequestBody NotifyUploadedVideoReqDto notifyUploadedVideoReqDto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        try {

            logger.debug(String.format("[ENTER] notifyUploadedVideo: {id: %d, notifyUploadedVideoReqDto: %s}", id, notifyUploadedVideoReqDto.toString()));

            Optional<Video> optionalVideo = videoRepository.findById(id);
            optionalVideo.orElseThrow(() -> new Exception("No Entity Found"));

            Video video = optionalVideo.get();
            video.notifyUploadedVideo(notifyUploadedVideoReqDto);
            videoRepository.save(video);
            
            logger.debug(String.format("[EXIT] notifyUploadedVideo: {video: %s}", video.toString()));
            return video;

        } catch(Exception e) {
            logger.error(String.format("[%s] notifyUploadedVideo: {id: %d, notifyUploadedVideoReqDto: %s, stackTrace: %s}", 
                e.getClass().getName(), id, notifyUploadedVideoReqDto.toString(), e.getStackTrace().toString()));
            throw e;
        }
    }

    
}
