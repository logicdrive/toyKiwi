package toykiwi.exceptions;

import org.springframework.http.HttpStatus;

import toykiwi.customExceptionControl.CustomException;

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