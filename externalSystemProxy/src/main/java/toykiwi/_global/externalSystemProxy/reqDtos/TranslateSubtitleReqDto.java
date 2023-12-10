package toykiwi._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TranslateSubtitleReqDto implements ExternalSystemProxyReqDto {
    private final String subtitle;

    public TranslateSubtitleReqDto(String subtitle) {
        this.subtitle = subtitle;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("subtitle", this.subtitle);
        return hashMap;
    }
}