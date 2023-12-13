package toykiwi._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetQnAForSentenceReqDto implements ExternalSystemProxyReqDto {
    private final String sentence;

    public GetQnAForSentenceReqDto(String sentence) {
        this.sentence = sentence;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("sentence", this.sentence);
        return hashMap;
    }
}