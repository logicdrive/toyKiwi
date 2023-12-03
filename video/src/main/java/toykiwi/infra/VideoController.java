package toykiwi.infra;

import toykiwi.domain.Video;
import toykiwi.domain.VideoRepository;
import toykiwi.dto.NotifyUploadedVideoReqDto;

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

    @Autowired
    VideoRepository videoRepository;

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
        System.out.println("##### /video/notifyUploadedVideo  called #####");

        Optional<Video> optionalVideo = videoRepository.findById(id);
        optionalVideo.orElseThrow(() -> new Exception("No Entity Found"));

        Video video = optionalVideo.get();
        video.notifyUploadedVideo(notifyUploadedVideoReqDto);
        videoRepository.save(video);
        
        return video;
    }

    
}
