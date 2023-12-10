package toykiwi._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UploadYoutubeVideoReqDto implements ExternalSystemProxyReqDto {
    private final String youtubeUrl;
    private final Integer cuttedStartSecond;
    private final Integer cuttedEndSecond;

    public UploadYoutubeVideoReqDto(String youtubeUrl, Integer cuttedStartSecond, Integer cuttedEndSecond) {
        this.youtubeUrl = youtubeUrl;
        this.cuttedStartSecond = cuttedStartSecond;
        this.cuttedEndSecond = cuttedEndSecond;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("youtubeUrl", this.youtubeUrl);
        hashMap.put("cuttedStartSecond", this.cuttedStartSecond);
        hashMap.put("cuttedEndSecond", this.cuttedEndSecond);
        return hashMap;
    }
}