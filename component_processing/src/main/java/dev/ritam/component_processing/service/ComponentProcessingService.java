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
        try {
            ResponseEntity<PackagingAndDeliveryResponse> responseEntity = packagingAndDeliveryClient
                    .getPackagingAndDeliveryCharge(component, count);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
                var packagingAndDeliveryResponse = responseEntity.getBody();

                    ComponentType componentType = component.equals("accessory")
                            ? ComponentType.ACCESSORY
                            : ComponentType.INTEGRAL_ITEM;
                    var componentProcessor = componentFactory.make(
                            componentType, packagingAndDeliveryResponse
                    );

                    componentProcessor.setProcessingCharge(
                            componentProcessingDefaultValues.getProcessingCharge(componentType));
                    componentProcessor.setDuration(
                            componentProcessingDefaultValues.getProcessingDuration(componentType));
                    var processResponse = componentProcessor.processComponent();
                    log.info(String.format("ComponentProcessingService processDetail(ProcessRequest processRequest) : " +
                            "process request %s produced response %s", processRequest, processResponse.toString()));

                    return processResponse;
            } else {
                var errMsg = String.format(
                        "ComponentProcessingService processDetail(ProcessRequest processRequest) : Packaging and Delivery microservice failed with status : %s",
                        responseEntity.getStatusCode()
                );
                log.error(errMsg);
                throw new PackagingAndDeliveryServiceException(errMsg);
            }
        } catch (Exception e) {
            var errMsg = String.format(
                    "ComponentProcessingService processDetail(ProcessRequest processRequest) : Packaging and Delivery microservice failed with message : %s",
                    e.getMessage()
            );
            log.error(errMsg);
            throw new PackagingAndDeliveryServiceException(errMsg);
        }
    }
}
