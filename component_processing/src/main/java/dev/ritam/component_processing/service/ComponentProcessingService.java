package dev.ritam.component_processing.service;

import dev.ritam.component_processing.client.PackagingAndDeliveryClient;
import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentProcessingService {
    private final PackagingAndDeliveryClient packagingAndDeliveryClient;

    public ProcessResponse processDetail(ProcessRequest processRequest) {
        String componentType = processRequest.getComponentType();
        int count = processRequest.getQuantity();
        ResponseEntity<PackagingAndDeliveryResponse> responseEntity = packagingAndDeliveryClient
                .getPackagingAndDeliveryCharge(componentType, count);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            PackagingAndDeliveryResponse packagingAndDeliveryResponse = responseEntity.getBody();

            if (packagingAndDeliveryResponse != null) {
                log.info(packagingAndDeliveryResponse.toString());
            }
        } else {
            log.error("feign client failed");
        }
        return null;
    }
}
