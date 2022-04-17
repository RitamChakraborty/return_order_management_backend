package dev.ritam.component_processing.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "component-processing")
public class ComponentProcessingDefaultValues {
    private Map<String, Integer> integralItem;
    private Map<String, Integer> accessory;
}
