package toykiwi._global.externalSystemProxy.resDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RemoveFileResDto implements ExternalSystemProxyResDto {
    @JsonProperty
    private String fileUrl;
}
