package dev.ritam.authorization.service;

import dev.ritam.authorization.entity.Customer;
import dev.ritam.authorization.exception.CustomerExistsException;
import dev.ritam.authorization.exception.CustomerNotFoundException;
import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.model.CustomerResponse;
import dev.ritam.authorization.repository.CustomerRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, passwordEncoder);
    }

    @Test
    void loadUserByUsername() {
        // Given
        String username = "ritam@gmail.com";

        // When
        Mockito.when(customerRepository.getById(username))
                .thenReturn(
                        Customer.builder()
                                .firstName("Ritam")
                                .lastName("Chakraborty")
                                .contactNumber("9876543210")
                                .email(username)
                                .password("password")
                                .build()
                );
        UserDetails userDetails = customerService.loadUserByUsername(username);

        // Then
        MatcherAssert.assertThat(userDetails, Matchers.notNullValue());
        MatcherAssert.assertThat(userDetails.getUsername(), Matchers.is(username));
        MatcherAssert.assertThat(userDetails.getPassword(), Matchers.is("password"));
    }

    @Test
    void addCustomerTest() {
        // Given
        String customerEmail = "ritam@gmai.com";
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName("Ritam")
                .lastName("Chakraborty")
                .email(customerEmail)
                .password("password")
                .contactNumber("9876543210")
                .build();

        // When
        Mockito.when(customerRepository.existsById(customerEmail))
                .thenReturn(false);
        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(
                        Customer.builder()
                                .email(customerEmail)
                                .build()
                );
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);

        // Then
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(passwordEncoder).encode(passwordCaptor.capture());
        MatcherAssert.assertThat(customerResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(customerResponse.getEmail(), Matchers.is(customerEmail));
    }

    @Test
    void addCustomerTestWithCustomerExistsException() {
        // Given
        String customerEmail = "ritam@gmail.com";
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName("Ritam")
                .lastName("Chakraborty")
                .email(customerEmail)
                .password("password")
                .contactNumber("9876543210")
                .build();

        // When
        Mockito.when(customerRepository.existsById(customerEmail))
                .thenReturn(true);

        // Then
        String expected = String.format("CustomerService.addCustomer (CustomerRequest customerRequest) : Customer with email %s already exists", customerEmail);
        Assertions.assertThrows(
                CustomerExistsException.class,
                () -> customerService.addCustomer(customerRequest),
                expected
        );
    }

    @Test
    void getCustomerTest() {
        // Given
        String customerEmail = "ritam@gmail.com";
        String password = "password";
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                customerEmail,
                                password
                        )
                );

        // When
        Mockito.when(customerRepository.findById(customerEmail))
                .thenReturn(Optional.of(
                        Customer.builder()
                                .firstName("Ritam")
                                .lastName("Chakraborty")
                                .contactNumber("9876543210")
                                .email(customerEmail)
                                .password("password")
                                .build()
                ));
        CustomerResponse customerResponse = customerService.getCustomer();

        // Then
        MatcherAssert.assertThat(customerResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(customerResponse.getEmail(), Matchers.is(customerEmail));
    }

    @Test
    void getCustomerTest2() {
        // Given
        String customerEmail = "ritam@gmail.com";
        String password = "password";
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                customerEmail,
                                password
                        )
                );

        // When
        Mockito.when(customerRepository.findById(customerEmail))
                .thenReturn(Optional.of(
                        Customer.builder()
                                .firstName("Ritam")
                                .lastName("Chakraborty")
                                .contactNumber("9876543210")
                                .email(customerEmail)
                                .password("password")
                                .build()
                ));
        CustomerResponse customerResponse = customerService.getCustomer();

        // Then
        MatcherAssert.assertThat(customerResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(customerResponse.getEmail(), Matchers.is(customerEmail));
    }

    @Test
    void getCustomerTestWithCustomerNotFoundException() {
        // Given
        String customerEmail = "ritam@gmail.com";
        String password = "password";
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                customerEmail,
                                password
                        )
                );

        // When
        Mockito.when(customerRepository.findById(customerEmail))
                .thenReturn(Optional.empty());

        // Then
        String expected = String.format("CustomerService.getCustomer () : Customer not found with username %s", customerEmail);
        Assertions.assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomer(),
                expected
        );
    }
}