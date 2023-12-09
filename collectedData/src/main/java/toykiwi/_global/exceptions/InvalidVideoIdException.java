package toykiwi._global.exceptions;

import toykiwi._global.customExceptionControl.CustomException;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class InvalidVideoIdException extends CustomException {       
    public InvalidVideoIdException() {
        super(
            HttpStatus.BAD_REQUEST,
            "InvalidVideoIdException",
            "Given video id is invalid.\n" +
            "Please check if passed video id is valid."
        );
    }
}