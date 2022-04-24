package dev.ritam.component_processing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessResponse {
    private String requestId;
    private int processingCharge;
    private int packagingAndDeliveryCharge;
    private LocalDate dateOfDelivery;
}
