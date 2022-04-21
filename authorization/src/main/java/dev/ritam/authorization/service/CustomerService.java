package dev.ritam.authorization.service;

import dev.ritam.authorization.entity.Customer;
import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.model.CustomerResponse;
import dev.ritam.authorization.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save(
                Customer.builder()
                        .email(customerRequest.getEmail())
                        .password(passwordEncoder.encode(customerRequest.getPassword()))
                        .firstName(customerRequest.getFirstName())
                        .lastName(customerRequest.getLastName())
                        .contactNumber(customerRequest.getContactNumber())
                        .build()
        );

        log.info(String.format("Add Customer : %s", customer));

        return CustomerResponse.builder()
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .contactNumber(customer.getContactNumber())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.getById(username);
    }
}
