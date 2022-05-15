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
    void getPackagingAndDeliveryChargeForIntegralItem() {
        // Given
        String componentType = "integral-item";
        int count = 2;

        // When
        PackagingAndDeliveryResponse packagingAndDeliveryResponse = packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(componentType, count);

        // Then
        Assertions.assertEquals(300, packagingAndDeliveryResponse.getPackagingCharge());
        Assertions.assertEquals(400, packagingAndDeliveryResponse.getDeliveryCharge());
    }

    @Test
    void getPackagingAndDeliveryChargeForAccessory() {
        // Given
        String componentType = "accessory";
        int count = 2;

        // When
        PackagingAndDeliveryResponse packagingAndDeliveryResponse = packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(componentType, count);

        // Then
        Assertions.assertEquals(200, packagingAndDeliveryResponse.getPackagingCharge());
        Assertions.assertEquals(200, packagingAndDeliveryResponse.getDeliveryCharge());
    }

    @Test
    void getPackagingAndDeliveryChargeUnknownComponentTest() {
        // Given
        String componentType = "unknown";
        int count = 2;

        // When and Then
        Assertions.assertThrows(
                ComponentTypeNotFoundException.class,
                () -> packagingAndDeliveryService.getPackagingAndDeliveryCharge(componentType, count),
                "PackagingAndDeliveryService getPackagingAndDeliveryCharge (String componentType, int count) : Component Type unknown not found"
        );
    }

    @Test
    void getPackagingAndDeliveryChargeUnknownComponentTest2() {
        // Given
        String componentType = "protective-sheath";
        int count = 2;

        // When and Then
        Assertions.assertThrows(
                ComponentTypeNotFoundException.class,
                () -> packagingAndDeliveryService.getPackagingAndDeliveryCharge(componentType, count),
                "PackagingAndDeliveryService getPackagingAndDeliveryCharge (String componentType, int count) : Component Type unknown not found"
        );
    }
}