package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component-processing/api")
@Slf4j
public class PaymentController {
    @GetMapping("/complete-processing")
    public ResponseEntity<PaymentResponse> completeProcessing(
            @RequestParam String requestId,
            @RequestParam Long creditCardNumber,
            @RequestParam Integer creditLimit,
            @RequestParam Integer processingCharge
    ) {
        log.info("PaymentController completeProcessing (String requestId, Long creditCardNumber, Integer creditLimit, Integer processingCharge) : " +
                        "Payment request with requestId {}, creditCardNumber {}, creditLimit {}, processingCharge {} produced {}",
                requestId, creditCardNumber, creditLimit, processingCharge, "success"
        );
        return new ResponseEntity<>(new PaymentResponse("success"), HttpStatus.OK);
    }
}
