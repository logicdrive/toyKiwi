package toykiwi.collectedData.resDtos;

import toykiwi.domain.Subtitle;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VideoSubtitlesResDto {
    private final List<SubtitleResDto> subtitles;

    public VideoSubtitlesResDto(List<Subtitle> subtitles) {
        this.subtitles = subtitles.stream()
            .map(subtitle -> new SubtitleResDto(subtitle))
            .collect(Collectors.toList());
    }
}