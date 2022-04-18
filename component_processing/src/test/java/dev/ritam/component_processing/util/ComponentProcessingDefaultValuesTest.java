package dev.ritam.component_processing.util;

import dev.ritam.component_processing.model.ComponentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComponentProcessingDefaultValuesTest {
    @Autowired
    private ComponentProcessingDefaultValues componentProcessingDefaultValues;

    @Test
    void getProcessingCharge() {
        int expected = 500;
        int actual = componentProcessingDefaultValues.getProcessingCharge(ComponentType.INTEGRAL_ITEM);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getProcessingDuration() {
        int expected = 5;
        int actual = componentProcessingDefaultValues.getProcessingDuration(ComponentType.INTEGRAL_ITEM);
        Assertions.assertEquals(expected, actual);
    }
}