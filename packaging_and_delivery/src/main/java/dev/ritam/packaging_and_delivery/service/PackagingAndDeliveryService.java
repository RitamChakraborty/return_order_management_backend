package dev.ritam.packaging_and_delivery.service;

import dev.ritam.packaging_and_delivery.exception.ComponentTypeNotFoundException;
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
            String componentType,
            int count
    ) throws ComponentTypeNotFoundException {
        Map<String, Integer> packagingItems = packagingAndDeliveryCosts.getPackagingItems();
        Map<String, Integer> deliveryItems = packagingAndDeliveryCosts.getDeliveryItems();

        if (packagingItems.containsKey(componentType) &&
                deliveryItems.containsKey(componentType)) {
            int packagingCharge = (packagingItems.get(componentType) +
                    packagingItems.get(PROTECTIVE_SHEATH)) * count;
            int deliveryCharge = deliveryItems.get(componentType) * count;

            log.info("PackagingAndDeliveryService getPackagingAndDeliveryCharge (String componentType, int count) : " +
                            "For component Type {} and count {}, packaging charge {} and delivery charge {}",
                    componentType, count, packagingCharge, deliveryCharge);

            return PackagingAndDeliveryResponse.builder()
                    .packagingCharge(packagingCharge)
                    .deliveryCharge(deliveryCharge)
                    .build();
        } else {
            var errMsg = String.format("PackagingAndDeliveryService getPackagingAndDeliveryCharge (String componentType, int count) : Component Type %s not found", componentType);
            log.error(errMsg);
            throw new ComponentTypeNotFoundException(errMsg);
        }
    }
}
