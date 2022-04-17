package dev.ritam.packaging_and_delivery.controller;

import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryRequest;
import dev.ritam.packaging_and_delivery.service.PackagingAndDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packaging-and-delivery/api")
@RequiredArgsConstructor
public class PackagingAndDeliveryController {
    private final PackagingAndDeliveryService packagingAndDeliveryService;

    @GetMapping("/packaging-and-delivery-charge")
    public ResponseEntity<?> getPackagingAndDeliveryCharge(
            @RequestBody PackagingAndDeliveryRequest packagingAndDeliveryRequest
    ) {
        return ResponseEntity.ok(
                packagingAndDeliveryService.getPackagingAndDeliveryCharge(
                        packagingAndDeliveryRequest
                )
        );
    }
}
