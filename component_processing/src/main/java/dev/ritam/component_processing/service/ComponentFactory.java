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
        ComponentProcessor componentProcessor = null;

        switch (componentType) {
            case ACCESSORY:
                componentProcessor = new Accessory(packagingAndDeliveryResponse);
                break;
            case INTEGRAL_ITEM:
                componentProcessor = new IntegralItem(packagingAndDeliveryResponse);
                break;
        }

        return componentProcessor;
    }
}
