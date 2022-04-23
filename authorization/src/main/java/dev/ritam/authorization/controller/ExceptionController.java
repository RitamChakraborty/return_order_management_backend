package dev.ritam.authorization.controller;

import dev.ritam.authorization.exception.InvalidJWTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidJWTException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleInvalidJWTException(InvalidJWTException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
