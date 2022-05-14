package dev.ritam.component_processing.service;


import dev.ritam.component_processing.client.PackagingAndDeliveryClient;
import dev.ritam.component_processing.exception.PackagingAndDeliveryServiceException;
import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ComponentProcessorServiceTest {
    @Mock
    private PackagingAndDeliveryClient packagingAndDeliveryClient;
    @InjectMocks
    private ComponentProcessingService componentProcessingService;

    @Test
    void processDetail() {
        // Given
        String componentName = "Shifter";
        String componentType = "accessory";
        String name = "Ritam";
        int quantity = 2;
        String contactNumber = "9876543210";
        ProcessRequest processRequest = ProcessRequest.builder()
                .componentName(componentName)
                .componentType(componentType)
                .name(name)
                .quantity(quantity)
                .contactNumber(contactNumber)
                .build();
        var packagingAndDeliveryResponse = PackagingAndDeliveryResponse.builder()
                .packagingCharge(200)
                .deliveryCharge(100)
                .build();

        // When
        Mockito.when(packagingAndDeliveryClient.getPackagingAndDeliveryCharge(componentType, quantity))
                .thenReturn(new ResponseEntity<>(packagingAndDeliveryResponse, HttpStatus.OK));
        ProcessResponse processResponse = componentProcessingService.processDetail(
                processRequest, "ritam@gmail.com");

        // Then
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