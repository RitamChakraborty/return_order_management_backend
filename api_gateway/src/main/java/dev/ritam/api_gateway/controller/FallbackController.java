package dev.ritam.api_gateway.controller;

import dev.ritam.api_gateway.exception.RequestTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/component-processing-fallback")
    public ResponseEntity<?> componentProcessingFallback() {
        throw new RequestTimeoutException("Component Processing Service is down. Try after some time");
    }
}
