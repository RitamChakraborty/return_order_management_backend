package dev.ritam.component_processing.service;


import dev.ritam.component_processing.exception.PackagingAndDeliveryServiceException;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComponentProcessorServiceTest {
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
        ProcessResponse processResponse = componentProcessingService.processDetail(
                processRequest, "ritam@gmail.com");
        Assertions.assertEquals(300, processResponse.getProcessingCharge());
    }

    @Test
    void processDetailWithException() {
        ProcessRequest processRequest = ProcessRequest.builder()
                .componentName("Shifters")
                .componentType("unknown")
                .name("Ritam")
                .quantity(2)
                .contactNumber("9876543210")
                .build();
        Assertions.assertThrows(
                PackagingAndDeliveryServiceException.class,
                () -> componentProcessingService.processDetail(processRequest, "ritam@gmail.com"),
                "Packaging and Delivery microservice failed with status : 500"
        );
    }
}