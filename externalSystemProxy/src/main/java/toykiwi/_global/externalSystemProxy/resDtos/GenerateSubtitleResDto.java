package toykiwi._global.externalSystemProxy.resDtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GenerateSubtitleResDto implements ExternalSystemProxyResDto {
    @JsonProperty
    private List<SubtitleResDto> subtitles;
}