package toykiwi.sanityCheck.resDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EchoWithJsonResDto {
    @JsonProperty
    private String message;
}
