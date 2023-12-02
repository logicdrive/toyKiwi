package toykiwi.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toykiwi.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/videos")
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
        @RequestBody NotifyUploadedVideoCommand notifyUploadedVideoCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /video/notifyUploadedVideo  called #####");
        Optional<Video> optionalVideo = videoRepository.findById(id);

        optionalVideo.orElseThrow(() -> new Exception("No Entity Found"));
        Video video = optionalVideo.get();
        video.notifyUploadedVideo(notifyUploadedVideoCommand);

        videoRepository.save(video);
        return video;
    }
}
//>>> Clean Arch / Inbound Adaptor
