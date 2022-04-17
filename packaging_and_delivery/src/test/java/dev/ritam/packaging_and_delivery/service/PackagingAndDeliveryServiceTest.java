package dev.ritam.packaging_and_delivery.service;

import dev.ritam.packaging_and_delivery.exception.ComponentTypeNotFoundException;
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
        String componentType = "integral-item";
        int count = 2;
        PackagingAndDeliveryResponse packagingAndDeliveryResponse = packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(componentType, count);
        Assertions.assertEquals(packagingAndDeliveryResponse.getPackagingCharge(), 300);
        Assertions.assertEquals(packagingAndDeliveryResponse.getDeliveryCharge(), 400);
    }

    @Test
    void getPackagingAndDeliveryChargeUnknownComponentTest() {
        String componentType = "unknown";
        int count = 2;
        Assertions.assertThrows(
                ComponentTypeNotFoundException.class,
                () -> packagingAndDeliveryService.getPackagingAndDeliveryCharge(componentType, count),
                "Component Type unknown not found"
        );
    }
}