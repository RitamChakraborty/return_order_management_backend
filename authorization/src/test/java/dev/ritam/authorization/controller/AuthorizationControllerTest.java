package dev.ritam.authorization.controller;

import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private AuthorizationController authorizationController;

    @Test
    void authenticate() {
        // Given
        String email = "ritam@gmail.com";
        String firstName = "Ritam";
        String lastName = "Chakraborty";
        String contactNumber = "9876543210";

        // When
        authorizationController.authenticate();

        // Then
        Mockito.verify(customerService).getCustomer();
    }

    @Test
    void signup() {
        // Given
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName("Ritam")
                .lastName("Chakraborty")
                .contactNumber("9876543210")
                .email("ritam@gmail.com")
                .password("password")
                .build();

        // When
        authorizationController.signup(customerRequest);

        // Then
        Mockito.verify(customerService).addCustomer(customerRequest);
    }
}