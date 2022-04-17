package dev.ritam.packaging_and_delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ComponentTypeNotFoundException extends RuntimeException {
    public ComponentTypeNotFoundException(String msg) {
        super(msg);
    }
}
