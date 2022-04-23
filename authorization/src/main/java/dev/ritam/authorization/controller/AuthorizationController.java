package dev.ritam.authorization.controller;

import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final CustomerService customerService;

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        return ResponseEntity.ok(customerService.getCustomer());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody CustomerRequest customerRequest
    ) {
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.CREATED);
    }
}
