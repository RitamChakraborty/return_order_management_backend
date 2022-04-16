package dev.ritam.packaging_and_delivery.util;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@RequiredArgsConstructor
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
    }
}