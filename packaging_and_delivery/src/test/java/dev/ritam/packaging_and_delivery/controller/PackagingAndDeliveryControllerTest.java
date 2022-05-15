package dev.ritam.packaging_and_delivery.controller;

import dev.ritam.packaging_and_delivery.service.PackagingAndDeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PackagingAndDeliveryControllerTest {
    @Mock
    private PackagingAndDeliveryService packagingAndDeliveryService;

    @InjectMocks
    private PackagingAndDeliveryController packagingAndDeliveryController;

    @Test
    void getPackagingAndDeliveryCharge() {
        // Given
        String componentType = "integral-item";
        int count = 1;

        // When
        packagingAndDeliveryController.getPackagingAndDeliveryCharge(componentType, count);

        // Then
        Mockito.verify(packagingAndDeliveryService)
                .getPackagingAndDeliveryCharge(componentType, count);
    }
}