package dev.ritam.component_processing.client;

import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "${client.value}",
        url = "${client.url}"
)
public interface PackagingAndDeliveryClient {
    @GetMapping("/packaging-and-delivery/api/packaging-and-delivery-charge")
    @Headers(value = {"Content-Type: application/json", "Accept: application/json"})
    ResponseEntity<PackagingAndDeliveryResponse> getPackagingAndDeliveryCharge(
            @RequestParam String componentType,
            @RequestParam Integer count
    );
}
