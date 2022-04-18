package dev.ritam.component_processing.service;

import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Accessory extends ComponentProcessor {
    private int processingCharge;
    private int duration;

    public Accessory(PackagingAndDeliveryResponse packagingAndDeliveryResponse) {
        super(packagingAndDeliveryResponse);
    }
}

