package dev.ritam.component_processing.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class ComponentProcessorDefaultValuesTest {
    @Autowired
    private ComponentProcessingDefaultValues componentProcessingDefaultValues;

    @Test
    void getIntegralItem() {
        Map<String, Integer> integralItem = componentProcessingDefaultValues.getIntegralItem();
        Assertions.assertTrue(integralItem.containsKey("processing-duration"));
        Assertions.assertEquals(5, integralItem.get("processing-duration"));
        Assertions.assertTrue(integralItem.containsKey("processing-charge"));
        Assertions.assertEquals(500, integralItem.get("processing-charge"));
    }

    @Test
    void getAccessory() {
        Map<String, Integer> accessory = componentProcessingDefaultValues.getAccessory();
        Assertions.assertTrue(accessory.containsKey("processing-duration"));
        Assertions.assertEquals(2, accessory.get("processing-duration"));
        Assertions.assertTrue(accessory.containsKey("processing-charge"));
        Assertions.assertEquals(300, accessory.get("processing-charge"));
    }
}