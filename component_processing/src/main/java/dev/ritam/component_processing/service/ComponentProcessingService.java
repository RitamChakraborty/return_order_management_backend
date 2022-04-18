package dev.ritam.component_processing.service;

import dev.ritam.component_processing.client.PackagingAndDeliveryClient;
import dev.ritam.component_processing.exception.PackagingAndDeliveryServiceException;
import dev.ritam.component_processing.model.ComponentType;
import dev.ritam.component_processing.model.PackagingAndDeliveryResponse;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
import dev.ritam.component_processing.util.ComponentProcessingDefaultValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentProcessingService {
    private final PackagingAndDeliveryClient packagingAndDeliveryClient;
    private final ComponentFactory componentFactory;
    private final ComponentProcessingDefaultValues componentProcessingDefaultValues;

    public ProcessResponse processDetail(ProcessRequest processRequest) {
        String component = processRequest.getComponentType();
        int count = processRequest.getQuantity();
        ResponseEntity<PackagingAndDeliveryResponse> responseEntity = packagingAndDeliveryClient
                .getPackagingAndDeliveryCharge(component, count);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            PackagingAndDeliveryResponse packagingAndDeliveryResponse = responseEntity.getBody();

            if (packagingAndDeliveryResponse != null) {
                ComponentType componentType = component.equals("accessory")
                        ? ComponentType.ACCESSORY
                        : ComponentType.INTEGRAL_ITEM;
                ComponentProcessor componentProcessor = componentFactory.make(
                        componentType, packagingAndDeliveryResponse
                );

                componentProcessor.setProcessingCharge(
                        componentProcessingDefaultValues.getProcessingCharge(componentType));
                componentProcessor.setDuration(
                        componentProcessingDefaultValues.getProcessingDuration(componentType));

                return componentProcessor.processComponent();
            } else {
                String errMsg = "Packaging and Delivery microservice returned null object";
                log.error(errMsg);
                throw new PackagingAndDeliveryServiceException(errMsg);
            }
        } else {
            String errMsg = String.format(
                    "Packaging and Delivery microservice failed with status : %s",
                    responseEntity.getStatusCode()
            );
            log.error(errMsg);
            throw new PackagingAndDeliveryServiceException(errMsg);
        }
    }
}
