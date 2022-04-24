package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component-processing/api")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/order-details")
    ResponseEntity<?> getOrderDetailsByCustomerEmail(@RequestParam String customerEmail) {
        return new ResponseEntity<>(
                orderDetailService.getOrderDetailsByCustomerEmail(customerEmail),
                HttpStatus.OK
        );
    }
}
