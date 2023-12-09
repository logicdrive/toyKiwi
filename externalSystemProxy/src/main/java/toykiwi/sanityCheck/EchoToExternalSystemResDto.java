package toykiwi.sanityCheck;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EchoToExternalSystemResDto {
    private final String message;

    public EchoToExternalSystemResDto(String message) {
        this.message = message;
    }
}
