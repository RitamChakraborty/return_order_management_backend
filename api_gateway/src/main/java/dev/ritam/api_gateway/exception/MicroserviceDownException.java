package dev.ritam.api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MicroserviceDownException extends RequestTimeoutException {
    public MicroserviceDownException(String msg) {
        super(msg);
    }
}
