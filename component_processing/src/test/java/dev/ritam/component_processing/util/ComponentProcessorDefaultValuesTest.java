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
        Assertions.assertEquals(integralItem.get("processing-duration"), 5);
        Assertions.assertTrue(integralItem.containsKey("processing-charge"));
        Assertions.assertEquals(integralItem.get("processing-charge"), 500);
    }

    @Test
    void getAccessory() {
        Map<String, Integer> accessory = componentProcessingDefaultValues.getAccessory();
        Assertions.assertTrue(accessory.containsKey("processing-duration"));
        Assertions.assertEquals(accessory.get("processing-duration"), 2);
        Assertions.assertTrue(accessory.containsKey("processing-charge"));
        Assertions.assertEquals(accessory.get("processing-charge"), 300);
    }
}