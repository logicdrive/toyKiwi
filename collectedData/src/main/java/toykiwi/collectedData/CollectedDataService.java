package toykiwi.collectedData;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toykiwi.domain.Subtitle;
import toykiwi.domain.SubtitleRepository;
import toykiwi.domain.Video;
import toykiwi.domain.VideoRepository;
import toykiwi.exceptions.InvalidVideoIdException;
import toykiwi.logger.CustomLogger;
import toykiwi.logger.CustomLoggerType;

@Service
@RequiredArgsConstructor
public class CollectedDataService {
    private final VideoRepository videoRepository;
    private final SubtitleRepository subtitleRepository;

    // 주어진 비디오에 속하는 자막들을 반환시키기 위해서
    public List<Subtitle> videoSubtitles(Long videoId) throws InvalidVideoIdException {
            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search video", String.format("{videoId: %d}", videoId));
            List<Video> videos = this.videoRepository.findAllByVideoId(videoId);
            if(videos.size() != 1)
                throw new InvalidVideoIdException();
            Video subtitleVideo = videos.get(0);

            CustomLogger.debug(CustomLoggerType.EFFECT, "Try to search subtitles by using video object", String.format("{subtitleVideo: %s}", subtitleVideo.toString()));
            List<Subtitle> subtitles = this.subtitleRepository.findAllByVideo(subtitleVideo);

            CustomLogger.debug(CustomLoggerType.EFFECT, "Subtitles are searched", String.format("{subtitlesSize: %d}", subtitles.size()));
            return subtitles;
        }
}
