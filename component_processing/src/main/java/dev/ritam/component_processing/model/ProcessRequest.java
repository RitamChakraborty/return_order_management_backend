package dev.ritam.component_processing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessRequest {
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Contact number can not be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be 10 characters long and should consist of digits")
    private String contactNumber;
    @NotBlank(message = "Component type can not be blank")
    private String componentType;
    @NotBlank(message = "Component name can not be blank")
    private String componentName;
    @Min(value = 1, message = "Quantity must be more than 0")
    private int quantity;
}
