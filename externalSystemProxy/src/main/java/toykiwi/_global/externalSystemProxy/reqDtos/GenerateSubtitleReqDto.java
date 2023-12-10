package toykiwi._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GenerateSubtitleReqDto implements ExternalSystemProxyReqDto {
    private final String uploadedUrl;

    public GenerateSubtitleReqDto(String uploadedUrl) {
        this.uploadedUrl = uploadedUrl;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("uploadedUrl", this.uploadedUrl);
        return hashMap;
    }
}