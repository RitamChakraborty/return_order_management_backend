package dev.ritam.component_processing.client;

import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PackagingAndDeliveryClientTest {
    @Mock
    private PackagingAndDeliveryClient packagingAndDeliveryClient;

    @Test
    void getPackagingAndDeliveryCharge() {
        // Given
        String componentName = "Shifter";
        String componentType = "accessory";
        String name = "Ritam";
        int quantity = 2;
        String contactNumber = "9876543210";
        var packagingAndDeliveryResponse = PackagingAndDeliveryResponse.builder()
                .packagingCharge(200)
                .deliveryCharge(100)
                .build();

        // When
        Mockito.when(packagingAndDeliveryClient.getPackagingAndDeliveryCharge(componentType, quantity))
                .thenReturn(new ResponseEntity<>(packagingAndDeliveryResponse, HttpStatus.OK));
        var packagingAndDeliveryResponseResponseEntity =
                packagingAndDeliveryClient.getPackagingAndDeliveryCharge(componentType, quantity);

        // Then
        Assertions.assertTrue(packagingAndDeliveryResponseResponseEntity.hasBody());
        Assertions.assertEquals(HttpStatus.OK, packagingAndDeliveryResponseResponseEntity.getStatusCode());
        Assertions.assertEquals(200, Objects.requireNonNull(packagingAndDeliveryResponseResponseEntity.getBody()).getPackagingCharge());
        Assertions.assertEquals(100, packagingAndDeliveryResponseResponseEntity.getBody().getDeliveryCharge());
    }
}