package dev.ritam.component_processing.test_suit;

import dev.ritam.component_processing.service.ComponentProcessorServiceTest;
import dev.ritam.component_processing.service.OrderDetailServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ComponentProcessorServiceTest.class,
        OrderDetailServiceTest.class
})
public class ServiceTestSuit {
}
