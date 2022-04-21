package dev.ritam.authorization.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = customerService.loadUserByUsername("ritam@gmail.com");
        Assertions.assertEquals("ritam@gmail.com", userDetails.getUsername());
    }
}