package dev.ritam.packaging_and_delivery.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "packaging-and-delivery-cost")
public class PackagingAndDeliveryCosts {
    private Map<String, Integer> packagingItems;
    private Map<String, Integer> deliveryItems;
}
