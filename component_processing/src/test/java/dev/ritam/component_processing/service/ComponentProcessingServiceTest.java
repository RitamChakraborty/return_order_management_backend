package dev.ritam.component_processing.service;


import dev.ritam.component_processing.model.ProcessRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComponentProcessingServiceTest {
    @Autowired
    private ComponentProcessingService componentProcessingService;

    @Test
    void processDetail() {
        ProcessRequest processRequest = ProcessRequest.builder()
                .componentName("Shifters")
                .componentType("accessory")
                .name("Ritam")
                .quantity(2)
                .contactNumber("9876543210")
                .build();
        componentProcessingService.processDetail(processRequest);
    }
}