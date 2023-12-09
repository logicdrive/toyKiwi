package toykiwi._global.customExceptionControl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 커스텀 예외 객체들을 범용적으로 적절하게 처리하여 결과를 유저에게 전송시키기 위해서
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CustomExceptionResponseEntity> handleException(CustomException e) {
        return CustomExceptionResponseEntity.toResponseEntity(e);
    }
}
