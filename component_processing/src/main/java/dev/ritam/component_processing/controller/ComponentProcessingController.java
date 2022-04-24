package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.service.ComponentProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/component-processing/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ComponentProcessingController {
    private final ComponentProcessingService componentProcessingService;
    private static final String CUSTOMER_EMAIL_HEADER = "x-auth-customer-email";

    @GetMapping("/process-detail")
    public ResponseEntity<?> processDetail(
            @RequestBody
            @Valid
                    ProcessRequest processRequest,
            @RequestHeader(CUSTOMER_EMAIL_HEADER) String customerEmail
    ) {
        return ResponseEntity.ok(componentProcessingService.processDetail(processRequest, customerEmail));
    }
}
