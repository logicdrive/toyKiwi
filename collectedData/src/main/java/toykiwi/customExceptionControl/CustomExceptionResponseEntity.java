package toykiwi.customExceptionControl;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;

// 커스텀 예외들에 status를 부여해서 적절한 HTTP 응답 객체를 생성시키기 위해서
@Data
@Builder
public class CustomExceptionResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<CustomExceptionResponseEntity> toResponseEntity(CustomException e) {
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(CustomExceptionResponseEntity.builder()
                    .status(e.getHttpStatus().value())
                    .code(e.getCode())
                    .message(e.getMessage())
                    .build()
            );
    }
}
