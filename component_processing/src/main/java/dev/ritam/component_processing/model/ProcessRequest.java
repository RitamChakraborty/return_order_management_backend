package dev.ritam.component_processing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessRequest {
    private String name;
    private String contactNumber;
    private String componentType;
    private String componentName;
    private int quantity;
}
