package dev.ritam.authorization.controller;

import dev.ritam.authorization.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final CustomerService customerService;

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        return ResponseEntity.ok(customerService.getCustomer());
    }
}
