package dev.ritam.component_processing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderDetailNotFoundException extends RuntimeException {
    public OrderDetailNotFoundException(String msg) {
        super(msg);
    }
}
