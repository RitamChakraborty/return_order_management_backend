package dev.ritam.component_processing.util;

import dev.ritam.component_processing.model.ComponentType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "component-processing")
public class ComponentProcessingDefaultValues {
    private static final String PROCESSING_CHARGE = "processing-charge";
    private static final String PROCESSING_DURATION = "processing-duration";
    private Map<String, Integer> integralItem;
    private Map<String, Integer> accessory;

    public int getProcessingCharge(ComponentType componentType) {
        switch (componentType) {
            case ACCESSORY:
                return accessory.get(PROCESSING_CHARGE);
            case INTEGRAL_ITEM:
                return integralItem.get(PROCESSING_CHARGE);
            default:
                return 0;
        }
    }

    public int getProcessingDuration(ComponentType componentType) {
        switch (componentType) {
            case ACCESSORY:
                return accessory.get(PROCESSING_DURATION);
            case INTEGRAL_ITEM:
                return integralItem.get(PROCESSING_DURATION);
            default:
                return 0;
        }
    }
}
