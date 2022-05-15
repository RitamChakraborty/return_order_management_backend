package dev.ritam.packaging_and_delivery.controller;

import dev.ritam.packaging_and_delivery.model.PackagingAndDeliveryResponse;
import dev.ritam.packaging_and_delivery.service.PackagingAndDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/packaging-and-delivery/api")
@RequiredArgsConstructor
@Validated
public class PackagingAndDeliveryController {
    private final PackagingAndDeliveryService packagingAndDeliveryService;

    @GetMapping("/packaging-and-delivery-charge")
    public @ResponseBody
    ResponseEntity<PackagingAndDeliveryResponse> getPackagingAndDeliveryCharge(
            @RequestParam
            @NotBlank(message = "Component Type can not be empty")
                    String componentType,
            @RequestParam
            @Min(value = 1, message = "Count must be more than 0")
                    Integer count
    ) {
        return ResponseEntity.ok(packagingAndDeliveryService
                .getPackagingAndDeliveryCharge(componentType, count));
    }
}
