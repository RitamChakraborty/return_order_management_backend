package dev.ritam.authorization.controller;

import dev.ritam.authorization.exception.BadRequestException;
import dev.ritam.authorization.exception.CustomerExistsException;
import dev.ritam.authorization.exception.InvalidJWTException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidJWTException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleInvalidJWTException(InvalidJWTException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomerExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handCustomerExistsException(CustomerExistsException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handException(Exception e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
