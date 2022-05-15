package dev.ritam.packaging_and_delivery.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class PackagingAndDeliveryCostsTest {
    @Autowired
    private PackagingAndDeliveryCosts packagingAndDeliveryCosts;

    @Test
    void getPackagingItemsForIntegralItems() {
        // Given
        String integralItem = "integral-item";

        // When
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();

        // Then
        Assertions.assertNotNull(packagingItems);
        Assertions.assertTrue(packagingItems.containsKey(integralItem));
        Assertions.assertEquals(100, packagingItems.get(integralItem));
    }

    @Test
    void getPackagingItemsForAccessory() {
        // Given
        String accessory = "accessory";

        // When
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();

        // Then
        Assertions.assertNotNull(packagingItems);
        Assertions.assertTrue(packagingItems.containsKey(accessory));
        Assertions.assertEquals(50, packagingItems.get(accessory));
    }

    @Test
    void getPackagingItemsForProtectiveSheath() {
        // Given
        String protectiveSheath = "protective-sheath";

        // When
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();

        // Then
        Assertions.assertNotNull(packagingItems);
        Assertions.assertTrue(packagingItems.containsKey(protectiveSheath));
        Assertions.assertEquals(50, packagingItems.get(protectiveSheath));
    }

    @Test
    void getDeliveryItemsForIntegralItems() {
        // Given
        String integralItem = "integral-item";

        // When
        Map<String, Integer> deliveryItems = packagingAndDeliveryCosts.getDeliveryItems();

        // Then
        Assertions.assertNotNull(deliveryItems);
        Assertions.assertTrue(deliveryItems.containsKey(integralItem));
        Assertions.assertEquals(200, deliveryItems.get(integralItem));
    }

    @Test
    void getDeliveryItemsForAccessory() {
        // Given
        String accessory = "accessory";

        // When
        Map<String, Integer> deliveryItems = packagingAndDeliveryCosts.getDeliveryItems();

        // Then
        Assertions.assertNotNull(deliveryItems);
        Assertions.assertTrue(deliveryItems.containsKey(accessory));
        Assertions.assertEquals(100, deliveryItems.get(accessory));
    }
}