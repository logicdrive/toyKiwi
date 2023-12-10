package toykiwi._global.externalSystemProxy.resDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SubtitleResDto {
    @JsonProperty
    private String subtitle;

    @JsonProperty
    private Integer startSecond;

    @JsonProperty
    private Integer endSecond;
}