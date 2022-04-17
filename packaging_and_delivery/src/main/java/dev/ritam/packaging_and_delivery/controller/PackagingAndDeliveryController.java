package dev.ritam.packaging_and_delivery.controller;

import dev.ritam.packaging_and_delivery.service.PackagingAndDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packaging-and-delivery/api")
@RequiredArgsConstructor
public class PackagingAndDeliveryController {
    private final PackagingAndDeliveryService packagingAndDeliveryService;

    @GetMapping("/packaging-and-delivery-charge")
    public @ResponseBody
    ResponseEntity<?> getPackagingAndDeliveryCharge(
            @RequestParam String componentType,
            @RequestParam Integer count
    ) {
        return ResponseEntity.ok(packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(componentType, count));
    }
}
