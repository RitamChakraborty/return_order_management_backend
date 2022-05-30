package dev.ritam.authorization.controller;

import dev.ritam.authorization.model.CustomerRequest;
import dev.ritam.authorization.model.CustomerResponse;
import dev.ritam.authorization.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization/api")
public class AuthorizationController {
    private final CustomerService customerService;

    @PostMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @GetMapping("/authenticate")
    public ResponseEntity<CustomerResponse> authenticate() {
        return ResponseEntity.ok(customerService.getCustomer());
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(
            @RequestBody CustomerRequest customerRequest
    ) {
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.CREATED);
    }
}
