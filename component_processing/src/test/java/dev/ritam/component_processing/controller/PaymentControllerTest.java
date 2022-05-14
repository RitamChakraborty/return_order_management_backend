package dev.ritam.component_processing.controller;

import dev.ritam.component_processing.model.PaymentResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {
    @Spy
    private PaymentController paymentController;

    @Test
    void completeProcessing() {
        // Given
        String requestId = "abcd";
        Long creditCardNumber = 9969141606579432L;
        int creditLimit = 40000;
        int processingCharge = 500;

        // When
        var paymentResponseResponseEntity =
                paymentController.completeProcessing(
                        requestId,
                        creditCardNumber,
                        creditLimit,
                        processingCharge
                );

        // Then
        PaymentResponse paymentResponse = paymentResponseResponseEntity.getBody();
        MatcherAssert.assertThat(paymentResponse, Matchers.hasProperty("response"));
        MatcherAssert.assertThat("success", Matchers.is(paymentResponse.getResponse()));
    }
}