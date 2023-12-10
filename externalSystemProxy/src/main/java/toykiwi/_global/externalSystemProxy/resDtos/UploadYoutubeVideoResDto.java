package toykiwi._global.externalSystemProxy.resDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UploadYoutubeVideoResDto implements ExternalSystemProxyResDto {
    @JsonProperty
    private String videoTitle;

    @JsonProperty
    private String uploadedUrl;

    @JsonProperty
    private String thumbnailUrl;
}