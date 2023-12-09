package toykiwi._global.exceptions;

import toykiwi._global.customExceptionControl.CustomException;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class InvalidSubtitleIdException extends CustomException {       
    public InvalidSubtitleIdException() {
        super(
            HttpStatus.BAD_REQUEST,
            "InvalidSubtitleIdException",
            "Given subtitle id is invalid.\n" +
            "Please check if passed subtitle id is valid."
        );
    }
}