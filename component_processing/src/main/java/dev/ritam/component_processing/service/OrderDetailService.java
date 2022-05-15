package dev.ritam.component_processing.service;

import dev.ritam.component_processing.entity.OrderDetail;
import dev.ritam.component_processing.exception.OrderDetailNotFoundException;
import dev.ritam.component_processing.model.OrderRequest;
import dev.ritam.component_processing.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail addOrderDetail(OrderRequest orderRequest, String customerEmail) {
        var orderDetail = OrderDetail.builder()
                .customerEmail(customerEmail)
                .processRequest(orderRequest.getProcessRequest())
                .processResponse(orderRequest.getProcessResponse())
                .build();
        orderDetailRepository.saveAndFlush(orderDetail);
        log.info(String.format("OrderDetailService addOrderDetail(OrderRequest orderRequest, String customerEmail) : " +
                        "For order request %s with customer email %s order Detail added  %s",
                orderRequest, customerEmail, orderDetail));
        return orderDetail;
    }

    public List<OrderDetail> getOrderDetailsByCustomerEmail(String customerEmail) {
        return orderDetailRepository.findOrderDetailByCustomerEmail(customerEmail);
    }

    public OrderDetail getOrderDetailByCustomerEmailAndOrderId(
            String customerEmail,
            Long orderId
    ) {
        var orderDetail = orderDetailRepository.findOrderDetailByCustomerEmailAndOrderId(
                customerEmail,
                orderId
        ).orElseThrow(() -> {
            var errorMsg = String.format(
                    "OrderDetailService getOrderDetailByCustomerEmailAndOrderId " +
                            "(String customerEmail, Long orderId) : " +
                            "Order detail with customer email %s and order id %s not found",
                    customerEmail, orderId
            );
            log.error(errorMsg);
                    return new OrderDetailNotFoundException(errorMsg);
                }
        );

        log.info(String.format(
                "OrderDetailService getOrderDetailByCustomerEmailAndOrderId " +
                        "(String customerEmail, Long orderId) : " +
                        "order detail request with customer email %s and order id %s produced order detail %s",
                customerEmail, orderId, orderDetail
        ));

        return orderDetail;
    }
}
