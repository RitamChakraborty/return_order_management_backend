package dev.ritam.component_processing.service;

import dev.ritam.component_processing.entity.OrderDetail;
import dev.ritam.component_processing.exception.OrderDetailNotFoundException;
import dev.ritam.component_processing.model.OrderRequest;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
import dev.ritam.component_processing.repository.OrderDetailRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @InjectMocks
    private OrderDetailService orderDetailService;

    @Test
    void addOrderDetail() {
        // Given
        String customerEmail = "ritam@gmail.com";
        OrderRequest orderRequest = OrderRequest.builder()
                .processRequest(
                        ProcessRequest.builder()
                                .name("Ritam")
                                .contactNumber("9876543210")
                                .componentType("integral-item")
                                .componentName("mock")
                                .quantity(1)
                                .build()
                )
                .processResponse(
                        ProcessResponse.builder()
                                .requestId(UUID.randomUUID().toString())
                                .processingCharge(500)
                                .packagingAndDeliveryCharge(300)
                                .dateOfDelivery(LocalDate.now().plusDays(3))
                                .build()
                )
                .build();

        // When
        orderDetailService.addOrderDetail(orderRequest, customerEmail);

        // Then
        Mockito.verify(orderDetailRepository).saveAndFlush(Mockito.any(OrderDetail.class));
    }

    @Test
    void getOrderDetailsByCustomerEmail() {
        // Given
        String customerEmail = "ritam@gmail.com";

        // When
        orderDetailService.getOrderDetailsByCustomerEmail(customerEmail);

        // Then
        Mockito.verify(orderDetailRepository).findOrderDetailByCustomerEmail(customerEmail);
    }

    @Test
    void getOrderDetailByCustomerEmailAndOrderId() {
        // Given
        String customerEmail = "ritam@gmail.com";
        var orderDetail = OrderDetail.builder()
                .orderId(1L)
                .customerEmail(customerEmail)
                .processRequest(
                        ProcessRequest.builder()
                                .name("Ritam")
                                .contactNumber("9876543210")
                                .componentType("integral-item")
                                .componentName("mock")
                                .quantity(1)
                                .build()
                )
                .processResponse(
                        ProcessResponse.builder()
                                .requestId(UUID.randomUUID().toString())
                                .processingCharge(500)
                                .packagingAndDeliveryCharge(300)
                                .dateOfDelivery(LocalDate.now().plusDays(3))
                                .build()
                )
                .build();

        // When
        Mockito.when(orderDetailRepository.findOrderDetailByCustomerEmailAndOrderId(customerEmail, 1L))
                .thenReturn(Optional.of(orderDetail));
        orderDetail = orderDetailService.getOrderDetailByCustomerEmailAndOrderId(customerEmail, 1L);

        // Then
        MatcherAssert.assertThat(orderDetail.getOrderId(), Matchers.is(1L));
    }

    @Test
    void getOrderDetailByCustomerEmailAndOrderIdWithUnknownOrderId() {
        // Given
        String customerEmail = "ritam@gmail.com";
        var orderDetail = OrderDetail.builder()
                .orderId(1L)
                .customerEmail(customerEmail)
                .processRequest(
                        ProcessRequest.builder()
                                .name("Ritam")
                                .contactNumber("9876543210")
                                .componentType("integral-item")
                                .componentName("mock")
                                .quantity(1)
                                .build()
                )
                .processResponse(
                        ProcessResponse.builder()
                                .requestId(UUID.randomUUID().toString())
                                .processingCharge(500)
                                .packagingAndDeliveryCharge(300)
                                .dateOfDelivery(LocalDate.now().plusDays(3))
                                .build()
                )
                .build();
        String errorMsg = String.format(
                "Order detail with customer email : %s and order id : %s not found",
                customerEmail, 1L
        );

        // When
        Mockito.when(orderDetailRepository.findOrderDetailByCustomerEmailAndOrderId(customerEmail, 1L))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(
                OrderDetailNotFoundException.class,
                () -> orderDetailService.getOrderDetailByCustomerEmailAndOrderId(customerEmail, 1L),
                errorMsg
        );
    }
}