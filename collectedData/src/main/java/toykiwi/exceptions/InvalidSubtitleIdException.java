package toykiwi.exceptions;

import org.springframework.http.HttpStatus;

import toykiwi.customExceptionControl.CustomException;

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