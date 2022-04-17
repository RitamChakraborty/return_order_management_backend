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
    void getPackagingItems() {
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();
        Assertions.assertNotNull(packagingItems);
        Assertions.assertTrue(packagingItems.containsKey("integral-item"));
        Assertions.assertEquals(packagingItems.get("integral-item"), 100);
    }

    @Test
    void getDeliveryItems() {
        Map<String, Integer> deliveryItems = packagingAndDeliveryCosts.getDeliveryItems();
        Assertions.assertNotNull(deliveryItems);
        Assertions.assertTrue(deliveryItems.containsKey("integral-item"));
        Assertions.assertEquals(deliveryItems.get("integral-item"), 200);
    }
}