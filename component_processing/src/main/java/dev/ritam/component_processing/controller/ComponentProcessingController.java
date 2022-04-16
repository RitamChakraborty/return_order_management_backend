package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.ProcessRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component-processing/api")
public class ComponentProcessingController {
    @GetMapping("/process-detail")
    public ResponseEntity<?> processDetail(@RequestBody ProcessRequest processRequest) {
        return null;
    }
}
