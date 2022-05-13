package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/component-processing/api")
public class PaymentController {
    @GetMapping("/complete-processing")
    public ResponseEntity<?> completeProcessing(
            @RequestParam String requestId,
            @RequestParam Long creditCardNumber,
            @RequestParam Integer creditLimit,
            @RequestParam Integer processingCharge
    ) {
        PaymentResponse paymentResponse = new PaymentResponse("success");
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
