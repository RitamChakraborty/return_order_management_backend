package dev.ritam.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException(String msg) {
        super(msg);
    }
}
