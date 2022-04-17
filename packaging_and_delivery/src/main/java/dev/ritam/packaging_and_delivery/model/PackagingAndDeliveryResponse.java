package dev.ritam.packaging_and_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackagingAndDeliveryResponse {
    private int packagingCharge;
    private int deliveryCharge;
}
