package dev.ritam.packaging_and_delivery.service;

import dev.ritam.packaging_and_delivery.exception.ComponentTypeNotFoundException;
import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryRequest;
import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryResponse;
import dev.ritam.packaging_and_delivery.util.PackagingAndDeliveryCosts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackagingAndDeliveryService {
    private final PackagingAndDeliveryCosts packagingAndDeliveryCosts;
    private static final String PROTECTIVE_SHEATH = "protective-sheath";

    public PackagingAndDeliveryResponse getPackagingAndDeliveryCharge(
            PackagingAndDeliveryRequest packagingAndDeliveryRequest
    ) throws ComponentTypeNotFoundException {
        String componentType = packagingAndDeliveryRequest.getComponentType();
        int quantity = packagingAndDeliveryRequest.getCount();
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();
        Map<String, Integer> deliveryItems = packagingAndDeliveryCosts.getDeliveryItems();

        if (packagingItems.containsKey(componentType) &&
                deliveryItems.containsKey(componentType)) {
            int packagingCharge = (packagingItems.get(componentType) +
                    packagingItems.get(PROTECTIVE_SHEATH)) * quantity;
            int deliveryCharge = deliveryItems.get(componentType) * quantity;

            log.info(String.format("For component Type : %s and count : %s, packaging charge : %s and delivery charge : %s",
                    componentType, quantity, packagingCharge, deliveryCharge));

            return PackagingAndDeliveryResponse.builder()
                    .packagingCharge(packagingCharge)
                    .deliveryCharge(deliveryCharge)
                    .build();
        } else {
            String errMsg = String.format("Component Type %s not found", componentType);
            log.error(errMsg);
            throw new ComponentTypeNotFoundException(errMsg);
        }
    }
}
