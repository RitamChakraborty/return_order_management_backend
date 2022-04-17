package dev.ritam.packaging_and_delivery.service;

import dev.ritam.packaging_and_delivery.exception.ComponentTypeNotFoundException;
import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryRequest;
import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PackagingAndDeliveryServiceTest {
    @Autowired
    private PackagingAndDeliveryService packagingAndDeliveryService;

    @Test
    void getPackagingAndDeliveryCharge() {
        PackagingAndDeliveryRequest packagingAndDeliveryRequest = PackagingAndDeliveryRequest.builder()
                .componentType("integral-item")
                .count(2)
                .build();
        PackagingAndDeliveryResponse packagingAndDeliveryResponse = packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(packagingAndDeliveryRequest);
        Assertions.assertEquals(packagingAndDeliveryResponse.getPackagingCharge(), 300);
        Assertions.assertEquals(packagingAndDeliveryResponse.getDeliveryCharge(), 400);
    }

    @Test
    void getPackagingAndDeliveryChargeUnknownComponentTest() {
        String componentType = "unknown";
        PackagingAndDeliveryRequest packagingAndDeliveryRequest = PackagingAndDeliveryRequest.builder()
                .componentType(componentType)
                .count(2)
                .build();
        Assertions.assertThrows(
                ComponentTypeNotFoundException.class,
                () -> packagingAndDeliveryService.getPackagingAndDeliveryCharge(packagingAndDeliveryRequest),
                "Component Type unknown not found"
        );
    }
}