package dev.ritam.authorization.service;

import dev.ritam.authorization.entity.Customer;
import dev.ritam.authorization.exception.CustomerExistsException;
import dev.ritam.authorization.exception.CustomerNotFoundException;
import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.model.CustomerResponse;
import dev.ritam.authorization.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String customerEmail = customerRequest.getEmail();

        if (customerRepository.existsById(customerEmail)) {
            var errMsg = String.format("CustomerService.addCustomer (CustomerRequest customerRequest) : Customer with email %s already exists", customerEmail);
            log.error(errMsg);
            throw new CustomerExistsException(errMsg);
        }

        var customer = customerRepository.save(
                Customer.builder()
                        .email(customerEmail)
                        .password(passwordEncoder.encode(customerRequest.getPassword()))
                        .firstName(customerRequest.getFirstName())
                        .lastName(customerRequest.getLastName())
                        .contactNumber(customerRequest.getContactNumber())
                        .build()
        );

        log.info("CustomerService.addCustomer (CustomerRequest customerRequest) : Customer : {}", customer);

        return CustomerResponse.builder()
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .contactNumber(customer.getContactNumber())
                .build();
    }

    public CustomerResponse getCustomer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        var customer = customerRepository.findById(username)
                .orElseThrow(() -> {
                    var errorMsg = String.format("CustomerService.getCustomer () : " +
                            "Customer not found with username %s", username);
                    log.error(errorMsg);
                    return new CustomerNotFoundException(errorMsg);
                });

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
