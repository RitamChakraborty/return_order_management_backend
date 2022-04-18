package dev.ritam.component_processing.service;

import dev.ritam.component_processing.model.ComponentType;
import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import org.springframework.stereotype.Component;

@Component
public class ComponentFactory {
    public ComponentProcessor make(
            ComponentType componentType,
            PackagingAndDeliveryResponse packagingAndDeliveryResponse
    ) {
        switch (componentType) {
            case ACCESSORY:
                return new Accessory(packagingAndDeliveryResponse);
            case INTEGRAL_ITEM:
                return new IntegralItem(packagingAndDeliveryResponse);
            default:
                return null;
        }
    }
}
