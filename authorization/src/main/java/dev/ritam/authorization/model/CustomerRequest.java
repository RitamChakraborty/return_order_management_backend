package dev.ritam.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @Email(message = "Invalid email provided")
    private String email;
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,16}$",
            message = "Password must be 8 characters long, " +
                    "should contain one uppercase letter, " +
                    "should contain one digit, " +
                    "and must not be more than 16 characters long"
    )
    private String password;
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @Pattern(
            regexp = "^.\\d{10}$",
            message = "Contact number should be 10 character long " +
                    "and should only consist of digits"
    )
    private String contactNumber;
}
