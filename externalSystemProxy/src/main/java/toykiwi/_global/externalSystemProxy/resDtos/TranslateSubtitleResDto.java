package toykiwi._global.externalSystemProxy.resDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TranslateSubtitleResDto implements ExternalSystemProxyResDto {
    @JsonProperty
    private String translatedSubtitle;
}