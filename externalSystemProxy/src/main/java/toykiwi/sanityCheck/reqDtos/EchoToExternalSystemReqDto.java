package toykiwi.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EchoToExternalSystemReqDto {
    private String message;
}
