package dev.ritam.packaging_and_delivery.controller;

import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packaging-and-delivery/api")
public class PackagingAndDeliveryController {
    @GetMapping("/packaging-and-delivery-charge")
    public ResponseEntity<?> getPackagingAndDeliveryCharge(
            @RequestBody PackagingAndDeliveryRequest packagingAndDeliveryRequest
    ) {
        return null;
    }
}
