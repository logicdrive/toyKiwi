package toykiwi._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EchoWithJsonReqDto implements ExternalSystemProxyReqDto {
    private final String message;

    public EchoWithJsonReqDto(String message) {
        this.message = message;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", this.message);
        return hashMap;
    }
}
