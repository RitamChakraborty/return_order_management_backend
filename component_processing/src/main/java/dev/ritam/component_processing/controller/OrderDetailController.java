package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.entity.OrderDetail;
import dev.ritam.component_processing.model.OrderRequest;
import dev.ritam.component_processing.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/component-processing/api")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private static final String CUSTOMER_EMAIL_HEADER = "x-auth-customer-email";

    @PostMapping("/place-order")
    public ResponseEntity<OrderDetail> placeOrder(
            @RequestBody OrderRequest orderRequest,
            @RequestHeader(CUSTOMER_EMAIL_HEADER) String customerEmail
    ) {
        return new ResponseEntity<>(
                orderDetailService.addOrderDetail(orderRequest, customerEmail),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/order-details")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByCustomerEmail(
            @RequestHeader(CUSTOMER_EMAIL_HEADER) String customerEmail
    ) {
        return new ResponseEntity<>(
                orderDetailService.getOrderDetailsByCustomerEmail(customerEmail),
                HttpStatus.OK
        );
    }

    @GetMapping("/order-detail/{:orderId}")
    public ResponseEntity<OrderDetail> getOrderDetailsByCustomerEmail(
            @RequestHeader(CUSTOMER_EMAIL_HEADER) String customerEmail,
            @PathVariable Long orderId
    ) {
        return new ResponseEntity<>(
                orderDetailService.getOrderDetailByCustomerEmailAndOrderId(customerEmail, orderId),
                HttpStatus.OK
        );
    }
}
