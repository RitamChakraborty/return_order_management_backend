package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.service.ComponentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component-processing/api")
@RequiredArgsConstructor
public class ComponentProcessingController {
    private final ComponentProcessingService componentProcessingService;

    @GetMapping("/process-detail")
    public ResponseEntity<?> processDetail(@RequestBody ProcessRequest processRequest) {
        return ResponseEntity.ok(componentProcessingService.processDetail(processRequest));
    }
}
