package toykiwi.customExceptionControl;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 커스텀 예외들을 적절하게 처리하기 위해서 상속되어야 함
@AllArgsConstructor
@Getter
public abstract class CustomException extends RuntimeException {
    protected HttpStatus httpStatus;
    protected String code;
    protected String message;
}
