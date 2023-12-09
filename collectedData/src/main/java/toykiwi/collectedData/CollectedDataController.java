package toykiwi.collectedData;

import toykiwi._global.customExceptionControl.CustomException;
import toykiwi._global.logger.CustomLogger;
import toykiwi._global.logger.CustomLoggerType;
import toykiwi.collectedData.resDtos.VideoSubtitlesResDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CollectedDataController {
    private final CollectedDataService collectedDataService;

    // 주어진 비디오에 속하는 자막들을 반환시키기 위해서
    @GetMapping("/videos/{id}/subtitles")
    public ResponseEntity<VideoSubtitlesResDto> videoSubtitles(@PathVariable Long id) {
        try {

            CustomLogger.debug(CustomLoggerType.ENTER, "", String.format("{id: %d}", id));

            VideoSubtitlesResDto videoSubtitlesResDto = this.collectedDataService.videoSubtitles(id);

            CustomLogger.debug(CustomLoggerType.EXIT, "", String.format("{subtitlesSize: %d}", videoSubtitlesResDto.getSubtitles().size()));
            return ResponseEntity.ok(videoSubtitlesResDto);

        } catch(CustomException e) {
            CustomLogger.error(e, "", String.format("{id: %d}", id));
            throw e;
        } catch(Exception e) {
            CustomLogger.error(e, "", String.format("{id: %d}", id));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
