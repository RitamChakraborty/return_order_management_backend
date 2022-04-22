package dev.ritam.api_gateway.controller;

import dev.ritam.api_gateway.exception.MicroserviceDownException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FallbackController {
    @GetMapping("/component-processing-fallback")
    public void componentProcessingFallback() {
        throw new MicroserviceDownException("Component Processing Service is down. Try after some time");
    }

    @GetMapping("/authorization-fallback")
    public void authorizationFallback() {
        throw new MicroserviceDownException("Authorization Service is down. Try after some time");
    }
}
